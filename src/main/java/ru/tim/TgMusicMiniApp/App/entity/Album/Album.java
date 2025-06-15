package ru.tim.TgMusicMiniApp.App.entity.Album;


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
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long tgUserId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer playListPlace;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "icon_id")
    private AlbumIcon albumIcon;

    @ManyToOne
    @JoinColumn(name = "gradient_id")
    private AlbumGradient gradient;

    @Column(nullable = false)
    private Boolean isIcon;

    @ManyToMany(
            cascade = { CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "play_list_tracks",
            joinColumns = @JoinColumn(name = "play_list_id"),
            inverseJoinColumns = @JoinColumn(name = "track_id")
    )
    @OrderColumn(name = "track_order")
    List<Track> tracks = new ArrayList<>();

}
