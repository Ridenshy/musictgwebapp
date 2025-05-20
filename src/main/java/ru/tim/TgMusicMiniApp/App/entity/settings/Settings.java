package ru.tim.TgMusicMiniApp.App.entity.settings;

import jakarta.persistence.*;
import lombok.*;
import ru.tim.TgMusicMiniApp.App.entity.enums.TypeName;
import ru.tim.TgMusicMiniApp.App.entity.enums.TypeType;

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

    @Column(nullable = false)
    private Long tgUserId;

    private Integer amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeName typeName;

    @Enumerated(EnumType.STRING)
    private TypeType typeType;

    private Boolean active;

}
