package ru.tim.TgMusicMiniApp.App.repo;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.tim.TgMusicMiniApp.App.entity.playset.PlaySet;

public interface PlaySetRepository extends JpaRepository<PlaySet, Long> {

    @Modifying
    @Query("UPDATE PlaySet ps SET ps.alreadyPlayed = :newAmount, ps.lastDropAmount = :newLastAmount WHERE ps.tgUserId = :userId")
    void updateAlreadyPlayedAmount(Long userId, Integer newAmount, Integer newLastAmount);

    @EntityGraph(attributePaths = "tracks")
    @Query("SELECT ps FROM PlaySet ps WHERE ps.tgUserId = :userId")
    PlaySet getPlaySet(Long userId);

    boolean existsByTgUserId(Long userId);

}
