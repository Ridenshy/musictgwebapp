package ru.tim.TgMusicMiniApp.App.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.tim.TgMusicMiniApp.App.dto.album.*;
import ru.tim.TgMusicMiniApp.App.dto.gradient.GradientDto;
import ru.tim.TgMusicMiniApp.App.dto.gradient.NewGradientDto;
import ru.tim.TgMusicMiniApp.App.dto.gradient.UpdatedGradientDto;
import ru.tim.TgMusicMiniApp.App.dto.icon.IconDto;
import ru.tim.TgMusicMiniApp.App.dto.track.TrackDto;

import java.util.List;

public interface AlbumService {

    @Transactional(readOnly = true)
    List<AlbumDto> getUserAlbums(String userId);

    @Transactional
    AlbumDto createAlbum(NewAlbumDto newAlbumDto);

    @Transactional
    AlbumDto updateAlbum(UpdatedAlbumDto albumDto);

    @Transactional
    void deleteAlbum(String userId, String albumId);

    @Transactional(readOnly = true)
    List<GradientDto> getUserGradients(String userId);

    @Transactional
    GradientDto saveAlbumGradient(NewGradientDto newGradientDto);

    @Transactional
    GradientDto updateGradient(UpdatedGradientDto updatedGradientDto);

    @Transactional
    void deleteGradient(String userId, String gradientId);

    @Transactional(readOnly = true)
    List<IconDto> getUserIcons(String userId);

    @Transactional
    IconDto saveNewIcon(MultipartFile newIcon);

    @Transactional
    MultipartFile getIconFile(String iconId);

    @Transactional(readOnly = true)
    List<TrackDto> getAlbumTracks(String albumId);

    @Transactional
    AlbumDto dropTrackFromAlbum(String albumId, String trackId);

    @Transactional
    void playAlbum(String albumId);
}
