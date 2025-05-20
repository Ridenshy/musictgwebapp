package ru.tim.TgMusicMiniApp.App.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.tim.TgMusicMiniApp.App.entity.settings.Settings;

public interface SettingsRepository extends JpaRepository<Settings, Long> {

    @Modifying
    @Query("UPDATE Settings s SET s.typeSettings = " +
            "(SELECT ts FROM TypeSettings ts WHERE ts.tgUserId = :userId AND ts.typeName = :typeName) " +
            "WHERE s.tgUserId = :userId")
    void updateSettingsType(Long userId, String typeName);

    Settings getUserSettings();


}
