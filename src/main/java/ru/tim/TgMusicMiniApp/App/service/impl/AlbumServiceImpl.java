package ru.tim.TgMusicMiniApp.App.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.tim.TgMusicMiniApp.App.dto.album.*;
import ru.tim.TgMusicMiniApp.App.dto.gradient.GradientDto;
import ru.tim.TgMusicMiniApp.App.dto.gradient.NewGradientDto;
import ru.tim.TgMusicMiniApp.App.dto.gradient.UpdatedGradientDto;
import ru.tim.TgMusicMiniApp.App.dto.icon.IconDto;
import ru.tim.TgMusicMiniApp.App.dto.mapper.AlbumMapper;
import ru.tim.TgMusicMiniApp.App.dto.mapper.TrackMapper;
import ru.tim.TgMusicMiniApp.App.dto.track.TrackDto;
import ru.tim.TgMusicMiniApp.App.entity.Album.Album;
import ru.tim.TgMusicMiniApp.App.entity.Album.Gradient;
import ru.tim.TgMusicMiniApp.App.entity.Album.Icon;
import ru.tim.TgMusicMiniApp.App.entity.track.Track;
import ru.tim.TgMusicMiniApp.App.repo.GradientRepository;
import ru.tim.TgMusicMiniApp.App.repo.IconRepository;
import ru.tim.TgMusicMiniApp.App.repo.AlbumRepository;
import ru.tim.TgMusicMiniApp.App.repo.TrackRepository;
import ru.tim.TgMusicMiniApp.App.service.AlbumService;
import ru.tim.TgMusicMiniApp.App.service.PlaySetService;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {

    private final PlaySetService playSetService;
    private final GoogleDriveService googleDriveService;

    private final AlbumRepository albumRepository;
    private final GradientRepository gradientRepository;
    private final IconRepository iconRepository;
    private final TrackRepository trackRepository;

    private final AlbumMapper albumMapper;
    private final TrackMapper trackMapper;
    private final TextEncryptor textEncryptor;

    @Value("${file-save-dir}")
    private String albumIconSaveDir;


    @Override
    public List<AlbumDto> getUserAlbums(String userId) {

        Long decUserId = Long.parseLong(textEncryptor.decrypt(userId));

        return albumRepository.getAllUserAlbums(decUserId)
                .stream().map(
                        album -> {
                            String encAlbumId = textEncryptor.encrypt(album.getId().toString());
                            List<String> encTrackIds = new ArrayList<>();
                            List<Track> tracks = album.getTracks();
                            if(tracks != null){
                                encTrackIds = tracks
                                        .stream()
                                        .map(track ->
                                                textEncryptor.encrypt(track.getId().toString()))
                                        .toList();
                            }
                            return albumMapper.albumToAlbumDto(album, encAlbumId, userId, encTrackIds);
                        }
                ).toList();

    }

    private AlbumDto convertAlbumToDto(Album album){
        String encAlbumId = textEncryptor.encrypt(album.getId().toString());
        String encUserId = textEncryptor.encrypt(album.getTgUserId().toString());

        List<String> encTrackIds = new ArrayList<>();
        List<Track> tracks = album.getTracks();
        if(tracks != null){
            encTrackIds = tracks
                    .stream()
                    .map(track -> textEncryptor.encrypt(track.getId().toString())).toList();
        }


        return albumMapper.albumToAlbumDto(album, encAlbumId, encUserId, encTrackIds);
    }

    @Override
    public AlbumDto createAlbum(NewAlbumDto newAlbumDto) {
        Long decUserId = Long.parseLong(textEncryptor.decrypt(newAlbumDto.getTgUserId()));

        String encGradientId = newAlbumDto.getGradientId();
        Long decGradientId = null;
        Gradient gradient = null;
        if(encGradientId != null){
            decGradientId = Long.parseLong(textEncryptor.decrypt(encGradientId));
            gradient = gradientRepository.findById(decGradientId).orElse(null);
        }

        String encIconId = newAlbumDto.getIconId();
        Long decIconId = null;
        Icon icon = null;
        boolean isIcon = false;
        if(encIconId != null){
            decIconId = Long.parseLong(textEncryptor.decrypt(encIconId));
            icon = iconRepository.findById(decIconId).orElse(null);
            isIcon = true;
        }

        Integer playListPlace = albumRepository.getUserAlbumsCount(decUserId);

        Album album = albumMapper.newAlbumDtoToAlbum(
                newAlbumDto,
                decUserId,
                isIcon,
                playListPlace
        );

        album.setGradient(gradient);
        album.setIcon(icon);

        Album savedAlbum = albumRepository.save(album);

        return convertAlbumToDto(savedAlbum);
    }

    @Override
    public AlbumDto updateAlbum(UpdatedAlbumDto updatedAlbumDto) {

        Long decAlbumId = Long.parseLong(textEncryptor.decrypt(updatedAlbumDto.getId()));
        Long decGradientId = Long.parseLong(textEncryptor.decrypt(updatedAlbumDto.getGradientId()));


        Album album = albumRepository.findById(decAlbumId)
                .orElseThrow(() -> new EntityNotFoundException("Album not found"));
        Gradient gradient = gradientRepository.findById(decGradientId).orElse(null);
        Icon icon = iconRepository.findById(decGradientId).orElse(null);

        album.setName(updatedAlbumDto.getName());
        album.setGradient(gradient);
        album.setIcon(icon);

        Album savedAlbum = albumRepository.save(album);

        return convertAlbumToDto(savedAlbum);
    }

    @Override
    public void deleteAlbum(String userId, String albumId) {
        Long decAlbumId = Long.parseLong(textEncryptor.decrypt(albumId));
        Long decUserId = Long.parseLong(textEncryptor.decrypt(userId));

        albumRepository.deleteUserAlbum(decUserId, decAlbumId);

    }

    @Override
    public List<GradientDto> getUserGradients(String userId) {
        Long decUserId = Long.parseLong(textEncryptor.decrypt(userId));

        return gradientRepository.getAllUserGradients(decUserId)
                .stream().map(gradient -> {
                    String encGradientId = textEncryptor.encrypt(gradient.getId().toString());
                    return albumMapper.gradientToGradientDto(gradient, encGradientId, userId);
                }).toList();
    }

    private GradientDto convertGradientToDto(Gradient gradient) {
        String encGradientId = textEncryptor.encrypt(gradient.getId().toString());
        String encUserId = textEncryptor.encrypt(gradient.getTgUserId().toString());
        return albumMapper.gradientToGradientDto(gradient, encGradientId, encUserId);
    }

    @Override
    public GradientDto saveAlbumGradient(NewGradientDto newGradientDto) {
        Long decUserId = Long.parseLong(textEncryptor.decrypt(newGradientDto.getTgUserId()));
        Gradient gradient = albumMapper.newGradientDtoToGradient(newGradientDto, decUserId);
        return convertGradientToDto(gradientRepository.save(gradient));
    }

    @Override
    public GradientDto updateGradient(UpdatedGradientDto updatedGradientDto) {
        Long decGradientId = Long.parseLong(textEncryptor.decrypt(updatedGradientDto.getId()));

        Gradient gradient = gradientRepository.findById(decGradientId)
                .orElseThrow(() -> new EntityNotFoundException("Gradient not found"));

        gradient.setHexColor1(updatedGradientDto.getHexColor1());
        gradient.setHexColor2(updatedGradientDto.getHexColor2());
        gradient.setHexColor3(updatedGradientDto.getHexColor3());
        return convertGradientToDto(gradientRepository.save(gradient));
    }


    @Override
    public void deleteGradient(String userId, String gradientId) {
        Long decUserId = Long.parseLong(textEncryptor.decrypt(userId));
        Long decGradientId = Long.parseLong(textEncryptor.decrypt(gradientId));

        List<Album> albums = albumRepository.findAlbumByGradient_Id(decGradientId);
        albums.forEach(album -> album.setGradient(null));

        albumRepository.saveAllAndFlush(albums);

        gradientRepository.deleteByIdAndUserId(decGradientId, decUserId);
    }

    @Override
    public List<IconDto> getUserIcons(String userId) {
        Long decUserId = Long.parseLong(textEncryptor.decrypt(userId));

        return iconRepository.getUserAlbumIcons(decUserId)
                .stream().map(icon -> {
                    String encIconId = textEncryptor.encrypt(icon.getId().toString());
                    return albumMapper.iconToIconDto(icon, encIconId, userId);
                }
                ).toList();
    }

    @Override
    public IconDto saveNewIcon(MultipartFile newIcon, String userId) {
        String decUserId = textEncryptor.decrypt(userId);

        try {
            String fileId = googleDriveService.uploadFile(newIcon, decUserId);
            Icon icon = Icon.builder()
                    .tgUserId(Long.parseLong(decUserId))
                    .path(fileId)
                    .build();
            Icon savedIcon = iconRepository.save(icon);
            String encIconId = textEncryptor.encrypt(savedIcon.getId().toString());
            return albumMapper.iconToIconDto(savedIcon, encIconId, userId);
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteIcon( String iconId) {
        Long decIconId = Long.parseLong(textEncryptor.decrypt(iconId));

        Icon icon = iconRepository.findById(decIconId)
                .orElseThrow(() -> new EntityNotFoundException("Icon not found"));

        List<Album> albums = albumRepository.findAlbumByIcon_Id(decIconId);
        albums.forEach(album -> album.setIcon(null));
        albumRepository.saveAllAndFlush(albums);

        try {
            googleDriveService.deleteFile(icon.getPath());
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }

        iconRepository.delete(icon);
    }

    @Override
    public byte[] getIconFile(String iconId) {
        try {
            return googleDriveService.downloadFile(iconId);
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TrackDto> getAlbumTracks(String albumId) {
        return List.of();
    }

    @Override
    public AlbumDto dropTrackFromAlbum(String albumId, String trackId) {
        return null;
    }

    @Override
    public void playAlbum(String albumId) {

    }
}
