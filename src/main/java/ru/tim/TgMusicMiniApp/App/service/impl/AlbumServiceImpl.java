package ru.tim.TgMusicMiniApp.App.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.tim.TgMusicMiniApp.App.dto.album.AlbumDto;
import ru.tim.TgMusicMiniApp.App.dto.album.AlbumGradientDto;
import ru.tim.TgMusicMiniApp.App.dto.album.AlbumIconDto;
import ru.tim.TgMusicMiniApp.App.dto.album.NewAlbumDto;
import ru.tim.TgMusicMiniApp.App.dto.mapper.AlbumMapper;
import ru.tim.TgMusicMiniApp.App.dto.track.TrackDto;
import ru.tim.TgMusicMiniApp.App.entity.Album.Album;
import ru.tim.TgMusicMiniApp.App.entity.Album.AlbumIcon;
import ru.tim.TgMusicMiniApp.App.repo.AlbumGradientRepository;
import ru.tim.TgMusicMiniApp.App.repo.AlbumIconRepository;
import ru.tim.TgMusicMiniApp.App.repo.AlbumRepository;
import ru.tim.TgMusicMiniApp.App.service.AlbumService;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;
    private final AlbumGradientRepository albumGradientRepository;
    private final AlbumIconRepository albumIconRepository;

    private final AlbumMapper albumMapper;
    private final TextEncryptor textEncryptor;

    @Value("${file-save-dir}")
    private String albumIconSaveDir;

    private List<AlbumDto> getAlbumDtoList(String userId){
        Long decUserId = Long.parseLong(textEncryptor.decrypt(userId));

        return albumRepository.getAllUserAlbums(decUserId)
                .stream().map(album -> {
                            String encAlbumId = textEncryptor.encrypt(album.getId().toString());
                            List<String> encTrackIds = new ArrayList<>();
                            if(album.getTracks() != null){
                                 encTrackIds = album.getTracks()
                                        .stream()
                                        .map(track ->
                                                textEncryptor.encrypt(track.getId().toString()))
                                        .toList();
                            }
                            return albumMapper.albumToAlbumDto(album, encAlbumId, userId, encTrackIds);
                        }
                ).toList();
    }

    private String saveAlbumIconFile(MultipartFile albumIcon, String encUserId){
        try{
            String decUserId = textEncryptor.decrypt(encUserId);

            Path userDir = Paths.get(albumIconSaveDir, decUserId);
            if(!Files.exists(userDir)){
                Files.createDirectories(userDir);
            }

            String fileName = UUID.randomUUID() + ".png";
            Path path = userDir.resolve(fileName);
            albumIcon.transferTo(path.toFile());
            return path.toString();
        } catch (Exception e){
            throw new RuntimeException("Failed to save icon ", e);
        }
    }

    private Long decodeGradientId(NewAlbumDto newAlbumDto){
        if(newAlbumDto.getGradient() != null){
            String gradientId = newAlbumDto.getGradient().getId();
            Long decGradientId = null;
            if(gradientId != null){
                decGradientId = Long.parseLong(textEncryptor.decrypt(newAlbumDto.getGradient().getId()));
            }
            return decGradientId;
        }
        return null;
    }

    @Override
    public List<AlbumDto> getUserAlbums(String userId) {
        return getAlbumDtoList(userId);
    }

    @Override
    public List<AlbumDto> createAlbumWithNewIcon(NewAlbumDto newAlbumDto, MultipartFile albumIcon) {
        AlbumIcon newAlbumIcon = AlbumIcon
                    .builder()
                    .path(saveAlbumIconFile(albumIcon, newAlbumDto.getTgUserId()))
                    .build();
        Long decUserId = Long.parseLong(textEncryptor.decrypt(newAlbumDto.getTgUserId()));
        Long decGradientId = decodeGradientId(newAlbumDto);
        Album newAlbum = albumMapper.newAlbumDtoToAlbum(
                newAlbumDto,
                newAlbumIcon,
                decUserId,
                decGradientId);
        newAlbum.setPlayListPlace(albumRepository.getUserAlbumsCount(decUserId));
        albumRepository.save(newAlbum);
        return getAlbumDtoList(newAlbumDto.getTgUserId());
    }

    @Override
    public List<AlbumDto> createAlbumWithExistingIcon(NewAlbumDto newAlbumDto, AlbumIconDto albumIconDto) {
        Long decUserId = Long.parseLong(textEncryptor.decrypt(newAlbumDto.getTgUserId()));
        Long decGradientId = decodeGradientId(newAlbumDto);
        Long decIconId = Long.parseLong(albumIconDto.getId());
        AlbumIcon existingIcon = albumIconRepository.getReferenceById(decIconId);
        Album newAlbum = albumMapper.newAlbumDtoToAlbum(
                newAlbumDto,
                existingIcon,
                decUserId,
                decGradientId,
                decIconId);
        newAlbum.setPlayListPlace(albumRepository.getUserAlbumsCount(decUserId));
        albumRepository.save(newAlbum);

        return getAlbumDtoList(newAlbumDto.getTgUserId());
    }

    @Override
    public List<AlbumDto> createAlbumNoIcon(NewAlbumDto newAlbumDto) {
        Long decUserId = Long.parseLong(textEncryptor.decrypt(newAlbumDto.getTgUserId()));
        Long decGradientId = decodeGradientId(newAlbumDto);
        Album newAlbum = albumMapper.newAlbumDtoToAlbum(
                newAlbumDto,
                decUserId,
                decGradientId);
        newAlbum.setPlayListPlace(albumRepository.getUserAlbumsCount(decUserId));
        albumRepository.save(newAlbum);

        return getAlbumDtoList(newAlbumDto.getTgUserId());
    }

    @Override
    public List<AlbumDto> updateAlbum(AlbumDto albumDto) {
        return List.of();
    }

    @Override
    public List<AlbumDto> deleteAlbum(AlbumDto albumDto) {
        return List.of();
    }

    @Override
    public void playAlbum(AlbumDto albumDto) {

    }

    private List<AlbumGradientDto> getAlbumGradientDtoList(String userId) {
        Long decUserId = Long.parseLong(textEncryptor.decrypt(userId));

        return albumGradientRepository.getAllUserGradients(decUserId)
                .stream()
                .map(albumMapper::albumGradientToAlbumGradientDto)
                .toList();
    }

    @Override
    public List<AlbumGradientDto> getUserGradients(String userId) {
        return getAlbumGradientDtoList(userId);
    }

    @Override
    public List<AlbumGradientDto> saveAlbumGradient(AlbumGradientDto albumGradientDto) {
        albumGradientRepository.save(albumMapper.albumGradientDtoToAlbumGradient(albumGradientDto));

        return getAlbumGradientDtoList(albumGradientDto.getTelegramId());
    }

    private List<AlbumIconDto> getAlbumIconDtoList(String userId){
        Long decUserId = Long.parseLong(textEncryptor.decrypt(userId));

        return albumIconRepository.getUserAlbumIcons(decUserId)
                .stream()
                .map(albumMapper::albumIconToAlbumIconDto)
                .toList();
    }

    @Override
    public List<AlbumIconDto> getUserIcons(String userId) {
        return getAlbumIconDtoList(userId);
    }

    @Override
    public List<AlbumIconDto> saveUserIcon(AlbumIconDto albumIconDto) {
        albumIconRepository.save(albumMapper.albumIconDtoToAlbumIcon(albumIconDto));

        return getAlbumIconDtoList(albumIconDto.getTgUserId());
    }


    @Override
    public List<TrackDto> getAlbumTracks(String albumId, String userId) {
        return List.of();
    }
}
