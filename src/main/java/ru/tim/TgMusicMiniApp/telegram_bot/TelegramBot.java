package ru.tim.TgMusicMiniApp.telegram_bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Audio;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tim.TgMusicMiniApp.App.entity.track.TgUserTrack;
import ru.tim.TgMusicMiniApp.App.service.TrackService;
import ru.tim.TgMusicMiniApp.telegram_bot.configuraction.BotProperty;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    BotProperty botProperty;
    TrackService trackService;

    @Autowired
    public TelegramBot(BotProperty botProperty, TrackService trackService){
        super(new DefaultBotOptions(), botProperty.token());
        this.botProperty = botProperty;
        this.trackService = trackService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasAudio()){
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

    @Override
    public String getBotUsername() {
        return botProperty.name();
    }

    public void sendNewMessage(SendMessage message) {
        try {
            execute(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void sendNewAudio(SendAudio audio) {
        try {
            execute(audio);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}