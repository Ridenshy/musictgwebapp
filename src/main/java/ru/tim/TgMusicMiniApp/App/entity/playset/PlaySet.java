package ru.tim.TgMusicMiniApp.App.entity.playset;


import jakarta.persistence.*;
import lombok.*;
import ru.tim.TgMusicMiniApp.App.entity.track.Track;

import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false)
    private Integer lastDropAmount;

    @ManyToMany(
            cascade = { CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "play_set_tracks",
            joinColumns = @JoinColumn(name = "play_set_id"),
            inverseJoinColumns = @JoinColumn(name = "track_id")
    )
    @OrderColumn(name = "track_order")
    private List<Track> tracks = new ArrayList<>();

}
