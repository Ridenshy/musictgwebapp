package ru.tim.TgMusicMiniApp.App.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.tim.TgMusicMiniApp.App.dto.album.AlbumDto;
import ru.tim.TgMusicMiniApp.App.dto.album.UpdatedAlbumDto;
import ru.tim.TgMusicMiniApp.App.dto.gradient.GradientDto;
import ru.tim.TgMusicMiniApp.App.dto.gradient.NewGradientDto;
import ru.tim.TgMusicMiniApp.App.dto.album.NewAlbumDto;
import ru.tim.TgMusicMiniApp.App.dto.gradient.UpdatedGradientDto;
import ru.tim.TgMusicMiniApp.App.dto.icon.IconDto;
import ru.tim.TgMusicMiniApp.App.dto.track.TrackDto;
import ru.tim.TgMusicMiniApp.App.service.AlbumService;

import java.util.List;

@RestController
@RequestMapping("/apiV1/album/")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping("/getList")
    public List<AlbumDto> getUserAlbums(@RequestParam @NotNull String userId){
        return albumService.getUserAlbums(userId);
    }

    @PostMapping("/createAlbum")
    public AlbumDto createAlbum(@Validated @RequestBody NewAlbumDto newAlbumDto){
        return albumService.createAlbum(newAlbumDto);
    }

    @PatchMapping("/editAlbum")
    public AlbumDto editAlbum(@Validated @RequestBody UpdatedAlbumDto updatedAlbumDto) {
        return albumService.updateAlbum(updatedAlbumDto);
    }

    @DeleteMapping("/deleteAlbum")
    public String deleteAlbum(@RequestParam @NotNull String userId,
                              @RequestParam @NotNull String albumId){
        albumService.deleteAlbum(userId, albumId);
        return albumId;
    }

    @GetMapping("/getGradients")
    public List<GradientDto> getUserAlbumGradients(@RequestParam @NotNull String userId){
        return albumService.getUserGradients(userId);
    }

    @PostMapping("/saveNewGradient")
    public GradientDto saveNewAlbumGradient(@Validated @RequestBody NewGradientDto newGradientDto){
        return albumService.saveAlbumGradient(newGradientDto);
    }

    @PatchMapping("/editGradient")
    public GradientDto editAlbumGradient(@Validated @RequestBody UpdatedGradientDto updatedGradientDto){
        return albumService.updateGradient(updatedGradientDto);
    }

    @DeleteMapping("/deleteGradient")
    public String deleteAlbumGradient(@RequestParam @NotNull String userId,
                                      @RequestParam @NotNull String gradientId){
        albumService.deleteGradient(userId, gradientId);
        return gradientId;
    }

    @GetMapping("/getUserIconsList")
    public List<IconDto> getUserAlbumIcons(@RequestParam @NotNull String userId){
        return albumService.getUserIcons(userId);
    }

    @PostMapping("/saveAlbumIcon")
    public IconDto saveNewIcon(@RequestPart @NotNull String userId,
                               @RequestPart MultipartFile newIconFile){
        return albumService.saveNewIcon(newIconFile, userId);
    }

    @DeleteMapping("/deleteIcon")
    public String deleteAlbumIcon(@RequestParam @NotNull String iconId){
        albumService.deleteIcon(iconId);
        return iconId;
    }

    @GetMapping(
            value = "/getIconFile/{iconId}",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public byte[] getIconFile(@PathVariable String iconId){
        return albumService.getIconFile(iconId);
    }

    @GetMapping()
    public List<TrackDto> getAlbumTracks(@RequestParam List<String> trackIds){
        return albumService.getAlbumTracks(trackIds);
    }

}
