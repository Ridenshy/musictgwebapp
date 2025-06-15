package ru.tim.TgMusicMiniApp.App.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.tim.TgMusicMiniApp.App.entity.Album.AlbumGradient;

import java.util.List;

public interface AlbumGradientRepository extends JpaRepository<AlbumGradient, Long> {

    @Query("SELECT ag FROM AlbumGradient ag WHERE ag.telegramId = :userId")
    List<AlbumGradient> getAllUserGradients(Long userId);

    @Query("SELECT ag FROM AlbumGradient ag " +
            "WHERE ag.telegramId = :userId " +
            "AND ag.hexColor1 = :hex1 " +
            "AND ag.hexColor2 = :hex2 " +
            "AND ag.hexColor3 = :hex3")
    AlbumGradient getAlbumGradientByHexCombinationAndTgUserId(Long userId,
                                                              String hex1,
                                                              String hex2,
                                                              String hex3);


}