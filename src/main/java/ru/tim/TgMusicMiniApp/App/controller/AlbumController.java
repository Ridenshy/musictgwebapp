package ru.tim.TgMusicMiniApp.App.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.tim.TgMusicMiniApp.App.dto.album.AlbumDto;
import ru.tim.TgMusicMiniApp.App.dto.album.UpdatedAlbumDto;
import ru.tim.TgMusicMiniApp.App.dto.gradient.GradientDto;
import ru.tim.TgMusicMiniApp.App.dto.gradient.NewGradientDto;
import ru.tim.TgMusicMiniApp.App.dto.album.NewAlbumDto;
import ru.tim.TgMusicMiniApp.App.dto.icon.IconDto;
import ru.tim.TgMusicMiniApp.App.service.AlbumService;

import java.util.List;

@RestController
@RequestMapping("/apiV1/album/")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping("/getList")
    public List<AlbumDto> getUserAlbums(@RequestParam String userId){
        return albumService.getUserAlbums(userId);
    }

    @PostMapping("/createAlbum")
    public AlbumDto createAlbum(@RequestBody @Valid NewAlbumDto newAlbumDto){
        return albumService.createAlbum(newAlbumDto);
    }

    @PatchMapping("/editAlbum")
    public AlbumDto editAlbum(@RequestBody @Valid UpdatedAlbumDto updatedAlbumDto) {
        return albumService.updateAlbum(updatedAlbumDto);
    }

    @DeleteMapping("/deleteAlbum")
    public String deleteAlbum(@RequestParam String userId, String albumId){
        albumService.deleteAlbum(userId, albumId);
        return albumId;
    }

    @GetMapping("/getGradients")
    public List<GradientDto> getUserAlbumGradients(@RequestParam String userId){
        return albumService.getUserGradients(userId);
    }

    @PostMapping("/saveNewGradient")
    public GradientDto saveNewAlbumGradient(@RequestBody @Valid NewGradientDto newGradientDto){
        return albumService.saveAlbumGradient(newGradientDto);
    }

    @DeleteMapping("/deleteGradient")
    public String deleteAlbumGradient(@RequestParam String userId, String gradientId){
        albumService.deleteGradient(userId, gradientId);
        return gradientId;
    }

    @GetMapping("/getUserIconsList")
    public List<IconDto> getUserAlbumIcons(@RequestParam String userId){
        return albumService.getUserIcons(userId);
    }

    @PostMapping("/saveAlbumIcon")
    public IconDto saveNewIcon(@RequestBody MultipartFile newIconFile){
        return null;
    }

    @DeleteMapping("/deleteIcon")
    public String deleteAlbumIcon(@RequestParam String userId, String iconId){
        return null;
    }

    @GetMapping("/getIconFile/{iconId}")
    public MultipartFile getIconFile(@PathVariable String iconId){
        return null;
    }


}
