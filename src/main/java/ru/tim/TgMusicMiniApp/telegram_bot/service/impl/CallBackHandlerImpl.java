package ru.tim.TgMusicMiniApp.telegram_bot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.tim.TgMusicMiniApp.App.dto.settings.BotSettingsDto;
import ru.tim.TgMusicMiniApp.App.entity.enums.TypeName;
import ru.tim.TgMusicMiniApp.App.entity.playset.PlaySet;
import ru.tim.TgMusicMiniApp.App.entity.track.TgUserTrack;
import ru.tim.TgMusicMiniApp.App.entity.track.Track;
import ru.tim.TgMusicMiniApp.App.service.PlaySetService;
import ru.tim.TgMusicMiniApp.App.service.SettingsService;
import ru.tim.TgMusicMiniApp.telegram_bot.TelegramBot;
import ru.tim.TgMusicMiniApp.telegram_bot.entity.BotMessage;
import ru.tim.TgMusicMiniApp.telegram_bot.service.BotMessageService;
import ru.tim.TgMusicMiniApp.telegram_bot.service.CallBackHandler;
import ru.tim.TgMusicMiniApp.telegram_bot.utility.KeyBoardUtils;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CallBackHandlerImpl implements CallBackHandler {

    private final TelegramBot bot;
    private final PlaySetService playSetService;
    private final SettingsService settingsService;
    private final BotMessageService botMessageService;

    @Async
    @Override
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

    @Async
    @Override
    public void sendAudio(Long userId, String fileId){
        SendAudio sendAudio = new SendAudio();
        sendAudio.setChatId(userId);
        sendAudio.setAudio(new InputFile(fileId));

        try {
            Message message = bot.execute(sendAudio);
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

    @Async
    @Override
    public void sendMoveKeyboard(Long userId){
        SendMessage message = new SendMessage();
        message.setChatId(userId.toString());
        message.setText("Нажмите Назад/Далее");
        message.setReplyMarkup(KeyBoardUtils.moveButton());

        try {
            Message msg = bot.execute(message);
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

    @Async
    @Override
    public void cleanupChat(Long userId) {
        botMessageService.getAllChatMessages(userId)
                .forEach(botMessage -> {
                    try {
                        bot.execute(new DeleteMessage(userId.toString(), botMessage.getMessageId()));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    botMessageService.deleteMessage(botMessage);
                });
    }


}
