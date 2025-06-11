package ru.tim.TgMusicMiniApp.App.repo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<ru.tim.TgMusicMiniApp.App.entity.playset.Album, Long> {
}