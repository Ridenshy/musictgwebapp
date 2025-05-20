package ru.tim.TgMusicMiniApp.App.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.tim.TgMusicMiniApp.App.entity.track.Track;

import java.util.List;

public interface TrackRepository extends JpaRepository<Track, Long> {

    @Query("SELECT track FROM Track track WHERE track.tgUserId = :userId ORDER BY track.listPlace ASC")
    List<Track> getAllTracks(Long userId);

    @Query("SELECT track FROM Track track WHERE track.tgUserId = :userId ORDER BY track.listPlace DESC")
    List<Track> getAllTracksDesc(Long userId);

    @Query("SELECT (COUNT(track)) FROM Track track WHERE track.tgUserId = :userId")
    Integer getLastListIndex(Long userId);

    Track findByIdAndTgUserId(Long id, Long userId);

    @Modifying
    @Query("UPDATE Track t SET t.listPlace = t.listPlace - 1 WHERE t.tgUserId = :userId AND t.listPlace > :deletedPosition")
    void updatePlaceAfterDelete(Long userId, Integer deletedPosition);



}