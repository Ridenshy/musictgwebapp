package ru.tim.TgMusicMiniApp.App.entity.settings;

import jakarta.persistence.*;
import lombok.*;
import ru.tim.TgMusicMiniApp.App.entity.enums.TypeType;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class TypeSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long tgUserId;

    @Column(nullable = false)
    private Integer amount;

    @Enumerated
    @Column(nullable = false)
    private TypeType typeType;
}
