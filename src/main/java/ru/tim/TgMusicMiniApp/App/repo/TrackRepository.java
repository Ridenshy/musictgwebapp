package ru.tim.TgMusicMiniApp.App.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.tim.TgMusicMiniApp.App.entity.track.Track;

import java.util.List;

public interface TrackRepository extends JpaRepository<Track, Long> {

    @Query("SELECT t FROM Track t WHERE t.tgUserId = :userId ORDER BY t.listPlace ASC")
    List<Track> getAllTracks(Long userId);

    @Query("SELECT t FROM Track t WHERE t.tgUserId = :userId ORDER BY t.listPlace DESC")
    List<Track> getAllTracksDesc(Long userId);

    @Query("SELECT t FROM Track t WHERE t.tgUserId = :userId AND t.listPlace >= :listPlace ORDER BY t.listPlace ASC")
    List<Track> getAllTracksAfter(Long userId, Integer listPlace);

    @Query("SELECT t FROM Track t WHERE t.tgUserId = :userId AND t.listPlace >= :listPlace ORDER BY t.listPlace DESC")
    List<Track> getAllTracksAfterDesc(Long userId, Integer listPlace);

    @Query("SELECT t FROM Track t WHERE t.tgUserId = :userId and t.listPlace IN :trackPlaceList ORDER BY t.listPlace ASC")
    List<Track> getAllListPlace(Long userId, List<Integer> trackPlaceList);

    @Query("SELECT t FROM Track t WHERE t.tgUserId = :userId and t.listPlace IN :trackPlaceList ORDER BY t.listPlace DESC")
    List<Track> getAllListPlaceDesc(Long userId, List<Integer> trackPlaceList);

    @Query("SELECT (COUNT(t)) FROM Track t WHERE t.tgUserId = :userId")
    Integer getLastListIndex(Long userId);

    Track findByIdAndTgUserId(Long id, Long userId);

    @Modifying
    @Query("UPDATE Track t SET t.listPlace = t.listPlace - 1 WHERE t.tgUserId = :userId AND t.listPlace > :deletedPosition")
    void updatePlaceAfterDelete(Long userId, Integer deletedPosition);



}