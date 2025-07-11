package ru.tim.TgMusicMiniApp.telegram_bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.menubutton.SetChatMenuButton;
import org.telegram.telegrambots.meta.api.objects.Audio;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.tim.TgMusicMiniApp.App.entity.track.TgUserTrack;
import ru.tim.TgMusicMiniApp.App.service.PlaySetService;
import ru.tim.TgMusicMiniApp.App.service.TrackService;
import ru.tim.TgMusicMiniApp.App.service.SettingsService;
import ru.tim.TgMusicMiniApp.telegram_bot.configuraction.BotProperty;
import ru.tim.TgMusicMiniApp.telegram_bot.service.CallBackHandler;
import ru.tim.TgMusicMiniApp.telegram_bot.service.UserInfoService;
import ru.tim.TgMusicMiniApp.telegram_bot.utility.KeyBoardUtils;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final BotProperty botProperty;
    private final TrackService trackService;
    private final PlaySetService playSetService;
    private final SettingsService settingsService;
    private final UserInfoService userInfoService;
    private final CallBackHandler callBackHandler;
    private final TextEncryptor textEncryptor;

    @Autowired
    public TelegramBot(BotProperty botProperty,
                       TrackService trackService,
                       PlaySetService playSetService,
                       SettingsService settingsService,
                       UserInfoService userInfoService,
                       TextEncryptor textEncryptor,
                       @Lazy CallBackHandler callBackHandler) {
        super(new DefaultBotOptions(), botProperty.token());
        this.botProperty = botProperty;
        this.trackService = trackService;
        this.playSetService = playSetService;
        this.settingsService = settingsService;
        this.userInfoService = userInfoService;
        this.callBackHandler = callBackHandler;
        this.textEncryptor = textEncryptor;

    }

    @Override
    public String getBotUsername() {
        return botProperty.name();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()){
            if("/start".equals(update.getMessage().getText())){
                Long userId = update.getMessage().getFrom().getId();
                if(!userInfoService.isUserExists(userId)){
                    userInfoService.addNewUser(userId);
                    playSetService.createNewUserPlaySet(userId);
                    settingsService.createTypesSettings(userId);
                }
                sendWebAppButton(userId);
            }
            else if(update.getMessage().hasAudio()){
                Audio track = update.getMessage().getAudio();
                Long userId = update.getMessage().getFrom().getId();
                TgUserTrack trackEntity = TgUserTrack
                        .builder()
                        .audioTgId(track.getFileId())
                        .title(track.getTitle())
                        .artist(track.getPerformer())
                        .duration(track.getDuration())
                        .tgUserId(userId)
                        .listPlace(trackService.getTracksAmount(userId))
                        .build();
                trackService.saveTgUserTrack(trackEntity);
            }
        }
        else if(update.hasCallbackQuery()){
            Long userId = update.getCallbackQuery().getFrom().getId();
            callBackHandler.sendPlaySetTracks(userId, update.getCallbackQuery().getData());
        }
    }


    private void sendWebAppButton(Long userId){
        String encUserId = textEncryptor.encrypt(userId.toString());
        SetChatMenuButton setChatMenuButton = SetChatMenuButton.builder()
                .chatId(userId)
                .menuButton(KeyBoardUtils.webAppButton(encUserId))
                .build();
        try {
            execute(setChatMenuButton);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


}