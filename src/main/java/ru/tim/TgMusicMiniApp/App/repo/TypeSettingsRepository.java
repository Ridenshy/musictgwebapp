package ru.tim.TgMusicMiniApp.App.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.tim.TgMusicMiniApp.App.entity.enums.SettingsType;
import ru.tim.TgMusicMiniApp.App.entity.settings.TypeSettings;

public interface TypeSettingsRepository extends JpaRepository<TypeSettings, Long> {

    @Query("SELECT ts FROM TypeSettings ts WHERE ts.tgUserId = :userId AND ts.typeName = :settingsType")
    TypeSettings getTypeSettingsForSettings(Long userId, SettingsType settingsType);

}
