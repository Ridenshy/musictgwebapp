package ru.tim.TgMusicMiniApp.App.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.tim.TgMusicMiniApp.App.entity.enums.TypeName;
import ru.tim.TgMusicMiniApp.App.entity.settings.Settings;

public interface SettingsRepository extends JpaRepository<Settings, Long> {

    @Query("SELECT s FROM Settings s WHERE s.tgUserId = :userId AND s.typeName = :typeName")
    Settings getSettings(Long userId, TypeName typeName);

    boolean existsByTgUserId(Long userId);

    @Query("SELECT s FROM Settings s WHERE s.tgUserId = :userId AND s.active = true")
    Settings getActiveSettings(Long userId);

}
