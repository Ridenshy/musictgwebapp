package ru.tim.TgMusicMiniApp.App.repo;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.tim.TgMusicMiniApp.App.entity.Album.Album;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    @EntityGraph(attributePaths = {"albumIcon", "gradient", "tracks"})
    @Query("SELECT a FROM Album a WHERE a.tgUserId = :userId")
    List<Album> getAllUserAlbums(Long userId);

    @Query("SELECT COUNT(a) FROM Album a WHERE a.tgUserId = :userId")
    Integer getUserAlbumsCount(Long userId);

}