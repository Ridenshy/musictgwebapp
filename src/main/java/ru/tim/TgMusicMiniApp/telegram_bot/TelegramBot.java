package ru.tim.TgMusicMiniApp.telegram_bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Audio;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.tim.TgMusicMiniApp.App.dto.BotSettingsDto;
import ru.tim.TgMusicMiniApp.App.entity.enums.TypeName;
import ru.tim.TgMusicMiniApp.App.entity.playset.PlaySet;
import ru.tim.TgMusicMiniApp.App.entity.track.TgUserTrack;
import ru.tim.TgMusicMiniApp.App.entity.track.Track;
import ru.tim.TgMusicMiniApp.App.service.PlaySetService;
import ru.tim.TgMusicMiniApp.App.service.TrackService;
import ru.tim.TgMusicMiniApp.App.service.SettingsService;
import ru.tim.TgMusicMiniApp.telegram_bot.configuraction.BotProperty;
import ru.tim.TgMusicMiniApp.telegram_bot.entity.BotMessage;
import ru.tim.TgMusicMiniApp.telegram_bot.service.BotMessageService;
import ru.tim.TgMusicMiniApp.telegram_bot.utility.KeyBoardUtils;

import java.util.List;

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
            sendPlaySetTracks(userId, update.getCallbackQuery().getData());
        }
    }

    @Override
    public String getBotUsername() {
        return botProperty.name();
    }

    public void sendPlaySetTracks(Long userId, String callbackData){
        cleanupChat(userId);
        BotSettingsDto botSettingsDto = settingsService.getBotSettingsDto(userId);
        Integer amountToDrop = botSettingsDto.getAmount();

        TypeName typeName = botSettingsDto.getTypeName();
        PlaySet playSet = playSetService.getPlaySet(userId);
        Integer alreadyPlayed = playSet.getAlreadyPlayed();
        Integer lastDropAmount = playSet.getLastDropAmount();
        List<Track> trackList = playSet.getTracks();
        List<Track> tracksToSend;
        int toSendSize;
        if(!callbackData.equals("back_")){
            if(playSet.getAlreadyPlayed() < trackList.size()){
                tracksToSend = trackList.stream()
                        .skip(alreadyPlayed)
                        .limit(amountToDrop)
                        .toList();
                toSendSize = tracksToSend.size();
                playSetService.updateAlreadyPlayed(userId, alreadyPlayed+toSendSize, toSendSize);
            }else {
                tracksToSend = trackList.stream()
                        .limit(amountToDrop)
                        .toList();
                toSendSize = tracksToSend.size();
                playSetService.updateAlreadyPlayed(userId, toSendSize, toSendSize);
            }
        }else {
            int skipPosition = alreadyPlayed - amountToDrop - lastDropAmount;
            if(skipPosition >= 0){
            tracksToSend = trackList.stream()
                    .skip(skipPosition)
                    .limit(amountToDrop)
                    .toList();
            toSendSize = tracksToSend.size();
            playSetService.updateAlreadyPlayed(userId, skipPosition + toSendSize, toSendSize);
            }
            else {
                int dropsCount = (trackList.size()/amountToDrop);
                skipPosition = dropsCount * amountToDrop;
                tracksToSend = trackList.stream()
                        .skip(skipPosition)
                        .limit(amountToDrop)
                        .toList();
                toSendSize = tracksToSend.size();
                playSetService.updateAlreadyPlayed(userId, skipPosition+toSendSize, toSendSize);
            }
        }

        for (Track track : tracksToSend) {
            if (track instanceof TgUserTrack tgTrack) {
                sendAudio(userId, tgTrack.getAudioTgId());
            }
        }
        if(!typeName.equals(TypeName.PACK)){
            sendMoveKeyboard(userId);
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

        public void sendMoveKeyboard(Long userId){
        SendMessage message = new SendMessage();
        message.setChatId(userId.toString());
        message.setText("Нажмите Назад/Далее");
        message.setReplyMarkup(KeyBoardUtils.moveButton());

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