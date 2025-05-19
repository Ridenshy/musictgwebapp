package ru.tim.TgMusicMiniApp.App.entity.settings;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Settings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @OneToOne
    @JoinColumn(name = "type_settings_id", referencedColumnName = "id")
    private TypeSettings typeSettings;

}
