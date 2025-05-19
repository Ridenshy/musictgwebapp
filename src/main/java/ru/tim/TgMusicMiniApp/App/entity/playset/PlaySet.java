package ru.tim.TgMusicMiniApp.App.entity.playset;


import jakarta.persistence.*;
import lombok.*;
import ru.tim.TgMusicMiniApp.App.entity.track.Track;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class PlaySet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long tgUserId;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "play_set_tracks",
            joinColumns = @JoinColumn(name = "play_set_id"),
            inverseJoinColumns = @JoinColumn(name = "track_id")
    )
    private Set<Track> tracks = new HashSet<>();

}
