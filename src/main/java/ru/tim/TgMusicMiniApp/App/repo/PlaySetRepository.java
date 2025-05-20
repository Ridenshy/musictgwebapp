package ru.tim.TgMusicMiniApp.App.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.tim.TgMusicMiniApp.App.entity.playset.PlaySet;

public interface PlaySetRepository extends JpaRepository<PlaySet, Long> {



}
