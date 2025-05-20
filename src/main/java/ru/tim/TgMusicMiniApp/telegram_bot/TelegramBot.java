package ru.tim.TgMusicMiniApp.telegram_bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Audio;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaAudio;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.tim.TgMusicMiniApp.App.dto.BotSettingsDto;
import ru.tim.TgMusicMiniApp.App.entity.enums.TypeName;
import ru.tim.TgMusicMiniApp.App.entity.playset.PlaySet;
import ru.tim.TgMusicMiniApp.App.entity.settings.Settings;
import ru.tim.TgMusicMiniApp.App.entity.track.TgUserTrack;
import ru.tim.TgMusicMiniApp.App.entity.track.Track;
import ru.tim.TgMusicMiniApp.App.service.PlaySetService;
import ru.tim.TgMusicMiniApp.App.service.TrackService;
import ru.tim.TgMusicMiniApp.App.service.SettingsService;
import ru.tim.TgMusicMiniApp.telegram_bot.configuraction.BotProperty;
import ru.tim.TgMusicMiniApp.telegram_bot.entity.BotMessage;
import ru.tim.TgMusicMiniApp.telegram_bot.service.BotMessageService;
import ru.tim.TgMusicMiniApp.telegram_bot.service.impl.BotMessageServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final BotProperty botProperty;
    private final TrackService trackService;
    private final PlaySetService playSetService;
    private final SettingsService settingsService;
    private final BotMessageService botMessageService;

    @Autowired
    public TelegramBot(BotProperty botProperty,
                       TrackService trackService,
                       PlaySetService playSetService,
                       SettingsService settingsService,
                       BotMessageService botMessageService){
        super(new DefaultBotOptions(), botProperty.token());
        this.botProperty = botProperty;
        this.trackService = trackService;
        this.playSetService = playSetService;
        this.settingsService = settingsService;
        this.botMessageService = botMessageService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()){
            if("/start".equals(update.getMessage().getText())){
                Long userId = update.getMessage().getFrom().getId();
                playSetService.createNewUserPlaySet(userId);
                settingsService.createTypesSettings(userId);
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
            sendPlaySetTracks(userId);
        }
    }

    @Override
    public String getBotUsername() {
        return botProperty.name();
    }

    public void sendPlaySetTracks(Long userId){
        cleanupChat(userId);
        BotSettingsDto botSettingsDto = settingsService.getBotSettingsDto(userId);
        Integer amountToDrop = botSettingsDto.getAmount();
        TypeName typeName = botSettingsDto.getTypeName();
        PlaySet playSet = playSetService.getPlaySet(userId);
        List<Track> trackList = playSet.getTracks();
        List<Track> tracksToSend = trackList.stream()
                .skip(playSet.getAlreadyPlayed())
                .limit(amountToDrop)
                .toList();

        if(playSet.getAlreadyPlayed() + amountToDrop >= trackList.size()){
            playSetService.updateAlreadyPlayed(userId, 0);
        }else {
            playSetService.updateAlreadyPlayed(userId, playSet.getAlreadyPlayed() + amountToDrop);
        }
        for (int i = 0; i < tracksToSend.size(); ++i) {
            Track track = tracksToSend.get(i);
            if (track instanceof TgUserTrack tgTrack) {
                sendAudio(userId, tgTrack.getAudioTgId());
            }
        }
        if(!typeName.equals(TypeName.PACK)){
            sendNextButton(userId);
        }
    }

    private void sendAudio(Long userId, String fileId){
        SendAudio sendAudio = new SendAudio();
        sendAudio.setChatId(userId);
        sendAudio.setAudio(new InputFile(fileId));

        try {
           Message message = execute(sendAudio);
           BotMessage botMessage = BotMessage.builder()
                   .messageId(message.getMessageId())
                   .chatId(userId)
                   .hasNextReply(false)
                   .build();
           botMessageService.saveBotMessage(botMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

        public void sendNextButton(Long userId){
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        InlineKeyboardButton nextButton = new InlineKeyboardButton();

        nextButton.setText("Далее ▶️");

        String callbackData = "next_" + userId;
        nextButton.setCallbackData(callbackData);

        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(nextButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow);
        inlineKeyboard.setKeyboard(rowList);

        SendMessage message = new SendMessage();
        message.setChatId(userId.toString());
        message.setText("Нажмите кнопку чтобы продолжить прослушивание:");
        message.setReplyMarkup(inlineKeyboard);

        try {
            Message msg = execute(message);
            BotMessage botMessage = BotMessage.builder()
                    .messageId(msg.getMessageId())
                    .chatId(userId)
                    .hasNextReply(true)
                    .build();
            botMessageService.saveBotMessage(botMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanupChat(Long userId) {
        botMessageService.getAllChatMessages(userId)
                .forEach(botMessage -> {
                    try {
                        execute(new DeleteMessage(userId.toString(), botMessage.getMessageId()));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    botMessageService.deleteMessage(botMessage);
                });
    }

}