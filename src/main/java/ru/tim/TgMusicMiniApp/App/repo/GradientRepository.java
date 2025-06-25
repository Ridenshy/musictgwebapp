package ru.tim.TgMusicMiniApp.App.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.tim.TgMusicMiniApp.App.entity.Album.Gradient;

import java.util.List;

public interface GradientRepository extends JpaRepository<Gradient, Long> {

    @Query("SELECT g FROM Gradient g WHERE g.tgUserId = :userId")
    List<Gradient> getAllUserGradients(Long userId);

    @Query("SELECT g FROM Gradient g " +
            "WHERE g.tgUserId = :userId AND g.id = :gradientId")
    Gradient getAlbumGradientByIdAndUserId(Long gradientId, Long userId);

    @Modifying
    @Query("DELETE FROM Gradient g WHERE g.id = :gradientId")
    void deleteById(Long gradientId);

}