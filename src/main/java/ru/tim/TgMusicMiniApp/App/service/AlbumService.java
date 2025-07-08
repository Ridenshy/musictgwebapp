package ru.tim.TgMusicMiniApp.App.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.tim.TgMusicMiniApp.App.dto.album.*;
import ru.tim.TgMusicMiniApp.App.dto.gradient.GradientDto;
import ru.tim.TgMusicMiniApp.App.dto.gradient.NewGradientDto;
import ru.tim.TgMusicMiniApp.App.dto.gradient.UpdatedGradientDto;
import ru.tim.TgMusicMiniApp.App.dto.icon.IconDto;
import ru.tim.TgMusicMiniApp.App.dto.track.TrackDto;
import ru.tim.TgMusicMiniApp.App.entity.enums.TrackAlbumType;

import java.util.List;
import java.util.Map;

public interface AlbumService {

    @Transactional(readOnly = true)
    List<AlbumDto> getUserAlbums(String userId);

    @Transactional
    AlbumDto createAlbum(NewAlbumDto newAlbumDto);

    @Transactional
    AlbumDto updateAlbum(UpdatedAlbumDto albumDto);

    @Transactional
    void deleteAlbum(String albumId);

    @Transactional(readOnly = true)
    List<GradientDto> getUserGradients(String userId);

    @Transactional
    GradientDto saveAlbumGradient(NewGradientDto newGradientDto);

    @Transactional
    GradientDto updateGradient(UpdatedGradientDto updatedGradientDto);

    @Transactional
    void deleteGradient(String gradientId);

    @Transactional(readOnly = true)
    List<IconDto> getUserIcons(String userId);

    @Transactional
    IconDto saveNewIcon(MultipartFile newIcon, String userId);

    @Transactional
    void deleteIcon(String iconId);

    @Transactional
    byte[] getIconFile(String iconId);

    @Transactional(readOnly = true)
    List<TrackDto> getAlbumTracks(List<String> trackIds);

    @Transactional(readOnly = true)
    Map<TrackAlbumType, List<ShortAlbumDto>> getTrackAlbumMap(String trackId, String userId);

    @Transactional
    void addTrackToAlbum(String albumId, String trackId);

    @Transactional
    void removeTrackFromAlbum(String albumId, String trackId);
}
