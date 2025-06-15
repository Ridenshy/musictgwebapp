package ru.tim.TgMusicMiniApp.App.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.tim.TgMusicMiniApp.App.entity.Album.AlbumIcon;

import java.util.List;

public interface AlbumIconRepository extends JpaRepository<AlbumIcon, Long> {

    @Query("SELECT ai FROM AlbumIcon ai WHERE ai.tgUserId = :userId")
    List<AlbumIcon> getUserAlbumIcons(Long userId);

}