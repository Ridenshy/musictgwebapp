package ru.tim.TgMusicMiniApp.App.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.tim.TgMusicMiniApp.App.entity.playset.PlaySet;
import ru.tim.TgMusicMiniApp.App.entity.track.Track;

import java.util.List;

public interface PlaySetRepository extends JpaRepository<PlaySet, Long> {

    @Modifying
    @Query("UPDATE PlaySet ps SET ps.tracks = :tracks WHERE ps.tgUserId = :userId")
    void updatePlaySetTracks(List<Track> tracks, Long userId);

    @Modifying
    @Query("UPDATE PlaySet ps SET ps.alreadyPlayed = :newAmount WHERE ps.tgUserId = :userId")
    void updateAlreadyPlayedAmount(Long userId, Integer newAmount);

    @Query("SELECT ps FROM PlaySet ps WHERE ps.tgUserId = :userId")
    PlaySet getPlaySet(Long userId);


    boolean existsByTgUserId(Long userId);


}
