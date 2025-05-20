package ru.tim.TgMusicMiniApp.telegram_bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Audio;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaAudio;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.tim.TgMusicMiniApp.App.entity.playset.PlaySet;
import ru.tim.TgMusicMiniApp.App.entity.settings.Settings;
import ru.tim.TgMusicMiniApp.App.entity.settings.TypeSettings;
import ru.tim.TgMusicMiniApp.App.entity.track.TgUserTrack;
import ru.tim.TgMusicMiniApp.App.entity.track.Track;
import ru.tim.TgMusicMiniApp.App.service.PlaySetService;
import ru.tim.TgMusicMiniApp.App.service.SettingsService;
import ru.tim.TgMusicMiniApp.App.service.TrackService;
import ru.tim.TgMusicMiniApp.App.service.TypeSettingsService;
import ru.tim.TgMusicMiniApp.telegram_bot.configuraction.BotProperty;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    private final BotProperty botProperty;
    private final TrackService trackService;
    private final PlaySetService playSetService;
    private final SettingsService settingsService;
    private final TypeSettingsService typeSettingsService;

    @Autowired
    public TelegramBot(BotProperty botProperty,
                       TrackService trackService,
                       PlaySetService playSetService,
                       SettingsService settingsService,
                       TypeSettingsService typeSettingsService){
        super(new DefaultBotOptions(), botProperty.token());
        this.botProperty = botProperty;
        this.trackService = trackService;
        this.playSetService = playSetService;
        this.settingsService = settingsService;
        this.typeSettingsService = typeSettingsService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()){
            if("/start".equals(update.getMessage().getText())){
                Long userId = update.getMessage().getFrom().getId();
                playSetService.createNewUserPlaySet(userId);
                typeSettingsService.createTypesSettings(userId);
                settingsService.createSettings(userId);
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
    }

    @Override
    public String getBotUsername() {
        return botProperty.name();
    }


    public void sendPlaySetTracks(Long userId){
        TypeSettings typeSettings = settingsService.getSettingsEntity(userId).getTypeSettings();
        Integer amountToDrop = typeSettings.getAmount();
        List<InputMedia> mediaAudios = new ArrayList<>();
        PlaySet playSet = playSetService.getPlaySet(userId);
        List<Track> trackList = playSet.getTracks();
        trackList.stream()
                .limit(amountToDrop)
                .filter(track -> track.getListPlace() > playSet.getAlreadyPlayed())
                .forEach(track -> {
                    if(track instanceof TgUserTrack tgTrack){
                        InputMediaAudio audio = new InputMediaAudio();
                        audio.setMedia(tgTrack.getAudioTgId());
                        audio.setCaption(tgTrack.getArtist() + " - " + tgTrack.getTitle());
                        mediaAudios.add(audio);
                    }
                });
        playSetService.updateAlreadyPlayed(userId, playSet.getAlreadyPlayed() + amountToDrop);
        if(!mediaAudios.isEmpty()){
            SendMediaGroup sendMediaGroup = new SendMediaGroup();
            sendMediaGroup.setChatId(userId.toString());
            sendMediaGroup.setMedias(mediaAudios);

            try {
                execute(sendMediaGroup);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }


    }

}