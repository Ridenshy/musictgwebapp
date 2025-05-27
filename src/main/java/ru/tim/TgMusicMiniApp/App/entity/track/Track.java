package ru.tim.TgMusicMiniApp.App.entity.track;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.tim.TgMusicMiniApp.App.entity.playset.PlaySet;

import java.util.HashSet;
import java.util.Set;


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
    private Set<PlaySet> playSets = new HashSet<>();

}
