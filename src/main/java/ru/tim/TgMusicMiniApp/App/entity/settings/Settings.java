package ru.tim.TgMusicMiniApp.App.entity.settings;

import jakarta.persistence.*;
import lombok.*;
import ru.tim.TgMusicMiniApp.App.entity.enums.SettingsType;

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
    private String tgUserId;

    @OneToOne(
            cascade = CascadeType.ALL,
            optional = false
    )
    @JoinColumn(name = "type_settings_id",
            referencedColumnName = "id",
            unique = true)
    private TypeSettings typeSettings;

}
