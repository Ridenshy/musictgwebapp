package ru.tim.TgMusicMiniApp.App.entity.track;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.tim.TgMusicMiniApp.App.dto.mapper.TrackMapper;
import ru.tim.TgMusicMiniApp.App.dto.track.TrackDto;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
public class TgUserTrack extends Track{ //entity of track, dropped by user in tg bots chat

    @Column(nullable = false)
    private String audioTgId; //telegram message id


    @Override
    public TrackDto toDto(TrackMapper mapper, String encTrackId) {
        return mapper.toTrackDto(this, encTrackId);
    }
}
