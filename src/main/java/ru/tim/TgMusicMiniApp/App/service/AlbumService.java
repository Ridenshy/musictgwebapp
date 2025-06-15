package ru.tim.TgMusicMiniApp.App.service;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.tim.TgMusicMiniApp.App.dto.album.AlbumDto;
import ru.tim.TgMusicMiniApp.App.dto.album.AlbumGradientDto;
import ru.tim.TgMusicMiniApp.App.dto.album.AlbumIconDto;
import ru.tim.TgMusicMiniApp.App.dto.album.NewAlbumDto;
import ru.tim.TgMusicMiniApp.App.dto.track.TrackDto;

import java.io.File;
import java.util.List;

public interface AlbumService {

    @Transactional(readOnly = true)
    List<AlbumDto> getUserAlbums(String userId);

    @Transactional
    List<AlbumDto> createAlbumWithNewIcon(NewAlbumDto newAlbum, MultipartFile albumIcon);

    @Transactional
    List<AlbumDto> createAlbumWithExistingIcon(NewAlbumDto newAlbumDto, AlbumIconDto albumIconDto);

    @Transactional
    List<AlbumDto> createAlbumNoIcon(NewAlbumDto newAlbumDto);

    @Transactional
    List<AlbumDto> updateAlbum(AlbumDto albumDto);

    @Transactional
    List<AlbumDto> deleteAlbum(AlbumDto albumDto);

    @Transactional
    void playAlbum(AlbumDto albumDto);

    @Transactional(readOnly = true)
    List<AlbumGradientDto> getUserGradients(String userId);

    @Transactional
    List<AlbumGradientDto> saveAlbumGradient(AlbumGradientDto albumGradientDto);

    @Transactional(readOnly = true)
    List<AlbumIconDto> getUserIcons(String userId);

    @Transactional
    List<AlbumIconDto> saveUserIcon(AlbumIconDto albumIconDto);

    @Transactional(readOnly = true)
    List<TrackDto> getAlbumTracks(String albumId, String userId);


}
