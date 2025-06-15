package ru.tim.TgMusicMiniApp.App.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.tim.TgMusicMiniApp.App.entity.Album.AlbumGradient;

import java.util.List;

public interface AlbumGradientRepository extends JpaRepository<AlbumGradient, Long> {

    @Query("SELECT ag FROM AlbumGradient ag WHERE ag.telegramId = :userId")
    List<AlbumGradient> getAllUserGradients(Long userId);



}