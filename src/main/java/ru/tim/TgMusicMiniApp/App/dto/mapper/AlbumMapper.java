package ru.tim.TgMusicMiniApp.App.dto.mapper;

import org.mapstruct.*;
import ru.tim.TgMusicMiniApp.App.dto.album.AlbumDto;
import ru.tim.TgMusicMiniApp.App.dto.album.AlbumGradientDto;
import ru.tim.TgMusicMiniApp.App.dto.album.AlbumIconDto;
import ru.tim.TgMusicMiniApp.App.entity.Album.Album;
import ru.tim.TgMusicMiniApp.App.dto.album.NewAlbumDto;
import ru.tim.TgMusicMiniApp.App.entity.Album.Gradient;
import ru.tim.TgMusicMiniApp.App.entity.Album.Icon;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface AlbumMapper {

    @Mapping(target = "id", expression = "java(encId)")
    @Mapping(target = "tgUserId", expression = "java(encUserId)")
    @Mapping(target = "trackIds", expression = "java(encTrackIds)")
    @Mapping(target = "albumIconPath", expression = "java(getIconPath(album))")
    @Mapping(target = "hexColor1", expression = "java(getHexColor1(album))")
    @Mapping(target = "hexColor2", expression = "java(getHexColor2(album))")
    @Mapping(target = "hexColor3", expression = "java(getHexColor3(album))")
    AlbumDto albumToAlbumDto(Album album,
                             String encId,
                             String encUserId,
                             List<String> encTrackIds);

    @Mapping(target = "tgUserId", expression = "java(decUserId)")
    @Mapping(target = "icon", expression = "java(albumIcon)")
    @Mapping(target = "gradient", expression = "java(convertGradient(albumDto, decUserId, decGradientId))")
    Album newAlbumDtoToAlbum(NewAlbumDto albumDto,
                             Icon icon,
                             Long decUserId,
                             Long decGradientId);

    @Mapping(target = "tgUserId", expression = "java(decUserId)")
    @Mapping(target = "icon", expression = "java(albumIcon)")
    @Mapping(target = "gradient", expression = "java(convertGradient(albumDto, decUserId, decGradientId))")
    Album newAlbumDtoToAlbum(NewAlbumDto albumDto,
                             Icon icon,
                             Long decUserId,
                             Long decGradientId,
                             Long decIconId);

    @Mapping(target = "tgUserId", expression = "java(decUserId)")
    @Mapping(target = "gradient", expression = "java(convertGradient(albumDto, decUserId, decGradientId))")
    Album newAlbumDtoToAlbum(NewAlbumDto albumDto,
                             Long decUserId,
                             Long decGradientId);


    AlbumGradientDto albumGradientToAlbumGradientDto(Gradient gradient);

    Gradient albumGradientDtoToAlbumGradient(AlbumGradientDto albumGradientDto);

    AlbumIconDto albumIconToAlbumIconDto(Icon icon);

    Icon albumIconDtoToAlbumIcon(AlbumIconDto albumIconDto);


    default Gradient convertGradient(NewAlbumDto albumDto,
                                     Long decUserId,
                                     Long decGradientId) {
        if(albumDto.getGradient() != null) {
            return Gradient.builder()
                    .id(decGradientId)
                    .telegramId(decUserId)
                    .hexColor1(albumDto.getGradient().getHexColor1())
                    .hexColor2(albumDto.getGradient().getHexColor2())
                    .hexColor3(albumDto.getGradient().getHexColor3())
                    .build();
        }
        return null;
    }


    default String getHexColor1(Album album) {
        return album.getGradient() != null ? album.getGradient().getHexColor1() : null;
    }

    default String getHexColor2(Album album) {
        return album.getGradient() != null ? album.getGradient().getHexColor2() : null;
    }

    default String getHexColor3(Album album) {
        return album.getGradient() != null ? album.getGradient().getHexColor3() : null;
    }

    default String getIconPath(Album album) {
        return album.getIcon() != null ? album.getIcon().getPath() : null;
    }

}
