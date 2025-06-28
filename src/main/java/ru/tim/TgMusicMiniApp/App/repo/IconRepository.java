package ru.tim.TgMusicMiniApp.App.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.tim.TgMusicMiniApp.App.entity.Album.Icon;

import java.util.List;

public interface IconRepository extends JpaRepository<Icon, Long> {

    @Query("SELECT ai FROM Icon ai WHERE ai.tgUserId = :userId")
    List<Icon> getUserAlbumIcons(Long userId);

    @Query("SELECT COUNT(i) FROM Icon i WHERE i.tgUserId = :userId")
    Integer getUserIconCount(Long userId);

}