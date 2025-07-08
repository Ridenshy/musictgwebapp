package ru.tim.TgMusicMiniApp.App.repo;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.tim.TgMusicMiniApp.App.entity.Album.Album;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    @EntityGraph(attributePaths = {"icon", "gradient", "tracks"})
    @Query("SELECT a FROM Album a WHERE a.tgUserId = :userId ORDER BY a.playListPlace")
    List<Album> getAllUserAlbums(Long userId);

    @Query("SELECT COUNT(a) FROM Album a WHERE a.tgUserId = :userId")
    Integer getUserAlbumsCount(Long userId);

    @Modifying
    @Query("DELETE FROM Album a WHERE a.id = :albumId")
    void deleteUserAlbum(Long albumId);

    List<Album> findAlbumByGradient_Id(Long gradientId);

    List<Album> findAlbumByIcon_Id(Long iconId);

    @Query("SELECT DISTINCT a FROM Album a JOIN a.tracks t WHERE t.id = :trackId AND t.tgUserId = :userId")
    List<Album> findAlbumsContainingTrack(Long trackId, Long userid);

    @Query("SELECT a FROM Album a WHERE NOT EXISTS " +
            "(SELECT t FROM Track t JOIN t.albums ta WHERE t.id = :trackId AND ta.id = a.id AND t.tgUserId = :userId)")
    List<Album> findAlbumsNotContainingTrack(Long trackId, Long userId);
}