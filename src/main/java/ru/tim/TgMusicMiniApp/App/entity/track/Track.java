package ru.tim.TgMusicMiniApp.App.entity.track;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.tim.TgMusicMiniApp.App.dto.mapper.TrackMapper;
import ru.tim.TgMusicMiniApp.App.dto.track.TrackDto;
import ru.tim.TgMusicMiniApp.App.entity.Album.Album;
import ru.tim.TgMusicMiniApp.App.entity.playset.PlaySet;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Track { //abstract track entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String artist;

    @Column(nullable = false)
    private Integer duration;

    @Column(nullable = false)
    private Integer listPlace; //place in the list

    @Column(nullable = false)
    private Long tgUserId;  //telegram user id

    @ManyToMany(mappedBy = "tracks")
    private List<PlaySet> playSets = new ArrayList<>();

    @ManyToMany(mappedBy = "tracks")
    private List<Album> albums = new ArrayList<>();

    public abstract TrackDto toDto(TrackMapper mapper, String encTrackId);

}
