package ru.tim.TgMusicMiniApp.App.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tim.TgMusicMiniApp.App.entity.enums.TypeName;
import ru.tim.TgMusicMiniApp.App.entity.enums.TypeType;
import ru.tim.TgMusicMiniApp.App.entity.settings.Settings;

import java.util.List;

public interface SettingsRepository extends JpaRepository<Settings, Long> {

    @Query("SELECT s FROM Settings s WHERE s.tgUserId = :userId")
    List<Settings> getAllUserSettings(Long userId);

    @Query("SELECT s FROM Settings s WHERE s.tgUserId = :userId AND s.typeName = :typeName")
    Settings getSettings(Long userId, TypeName typeName);

    boolean existsByTgUserId(Long userId);

    @Query("SELECT s FROM Settings s WHERE s.tgUserId = :userId AND s.active = true")
    Settings getActiveSettings(Long userId);

    @Modifying
    @Query("UPDATE Settings s SET s.active = " +
            "CASE WHEN s.typeName = :typeName THEN true ELSE false END WHERE s.tgUserId = :userId")
    void setActiveSettings(Long userId, TypeName typeName);

    @Modifying
    @Query("UPDATE Settings s SET s.typeType = :newTypeType, s.amount = :newAmount " +
            "WHERE s.tgUserId = :userId AND s.typeName = :typeName")
    void updateSettings(Long userId, TypeName typeName, TypeType newTypeType, Integer newAmount);



}
