package ru.tim.TgMusicMiniApp.App.dto.mapper;

import org.mapstruct.*;
import ru.tim.TgMusicMiniApp.App.dto.album.AlbumDto;
import ru.tim.TgMusicMiniApp.App.dto.album.NewAlbumDto;
import ru.tim.TgMusicMiniApp.App.dto.gradient.GradientDto;
import ru.tim.TgMusicMiniApp.App.dto.gradient.NewGradientDto;
import ru.tim.TgMusicMiniApp.App.dto.icon.IconDto;
import ru.tim.TgMusicMiniApp.App.entity.Album.Album;
import ru.tim.TgMusicMiniApp.App.entity.Album.Gradient;
import ru.tim.TgMusicMiniApp.App.entity.Album.Icon;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface AlbumMapper {

    @Mapping(target = "id", expression = "java(encAlbumId)")
    @Mapping(target = "tgUserId", expression = "java(encUserId)")
    @Mapping(target = "trackIds", expression = "java(encTrackIds)")
    AlbumDto albumToAlbumDto(Album album,
                             String encAlbumId,
                             String encUserId,
                             List<String> encTrackIds);


    @Mapping(target = "tgUserId", expression = "java(decUserId)")
    @Mapping(target = "isIcon", expression = "java(isIcon)")
    Album newAlbumDtoToAlbum(NewAlbumDto newAlbum,
                          Long decUserId,
                          Boolean isIcon,
                          Integer playListPlace);

    @Mapping(target = "id", expression = "java(encGradientId)")
    @Mapping(target = "tgUserId", expression = "java(encUserId)")
    GradientDto gradientToGradientDto(Gradient gradient, String encGradientId, String encUserId);

    @Mapping(target = "tgUserId", expression = "java(decUserId)")
    Gradient newGradientDtoToGradient(NewGradientDto newGradientDto, Long decUserId);

    @Mapping(target = "id", expression = "java(encIconId)")
    @Mapping(target = "tgUserId", expression = "java(encUserId)")
    IconDto iconToIconDto(Icon icon, String encIconId, String encUserId);

}
