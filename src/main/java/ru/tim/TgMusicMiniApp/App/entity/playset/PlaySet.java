package ru.tim.TgMusicMiniApp.App.entity.playset;


import jakarta.persistence.*;
import lombok.*;
import ru.tim.TgMusicMiniApp.App.entity.track.Track;

import java.nio.channels.InterruptedByTimeoutException;
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

    @Column(nullable = false)
    private Integer alreadyPlayed;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "play_set_tracks",
            joinColumns = @JoinColumn(name = "play_set_id"),
            inverseJoinColumns = @JoinColumn(name = "track_id")
    )
    private List<Track> tracks;

}
