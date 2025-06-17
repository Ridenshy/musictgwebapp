package ru.tim.TgMusicMiniApp.App.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.tim.TgMusicMiniApp.App.dto.album.AlbumDto;
import ru.tim.TgMusicMiniApp.App.dto.album.AlbumGradientDto;
import ru.tim.TgMusicMiniApp.App.dto.album.AlbumIconDto;
import ru.tim.TgMusicMiniApp.App.dto.album.NewAlbumDto;
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

    @PostMapping("/create")
    public List<AlbumDto> createAlbum(@RequestPart @Valid NewAlbumDto newAlbumDto,
                                      @RequestPart(required = false) @Valid AlbumIconDto albumIconDto,
                                      @RequestPart(required = false) MultipartFile iconFile){
        if(iconFile != null){
            return albumService.createAlbumWithNewIcon(newAlbumDto, iconFile);
        }else if(albumIconDto != null){
            return albumService.createAlbumWithExistingIcon(newAlbumDto, albumIconDto);
        }else {
            return albumService.createAlbumNoIcon(newAlbumDto);
        }
    }

    @PatchMapping("/editAlbum")
    public List<AlbumDto> editAlbum(@RequestBody @Valid AlbumDto albumDto){
        return albumService.updateAlbum(albumDto);
    }

    @DeleteMapping("/deleteAlbum")
    public List<AlbumDto> deleteAlbum(@RequestParam String userId, String albumId){
        return albumService.deleteAlbum(userId, albumId);
    }

    @GetMapping("/getGradients")
    public List<AlbumGradientDto> getUserAlbumGradients(@RequestParam String userId){
        return albumService.getUserGradients(userId);
    }

    @PostMapping("/saveNewGradient")
    public List<AlbumGradientDto> saveNewAlbumGradient(@RequestPart @Valid AlbumGradientDto gradientDto){
        return albumService.saveAlbumGradient(gradientDto);
    }

    @DeleteMapping("/deleteGradient")
    public List<AlbumGradientDto> deleteAlbumGradient(@RequestParam String userId, String gradientId){
        return null;
    }

    @GetMapping("/getUserIconsList")
    public List<AlbumIconDto> getUserAlbumIcons(@RequestParam String userId){
        return albumService.getUserIcons(userId);
    }

    @DeleteMapping("/deleteIcon")
    public List<AlbumIconDto> deleteAlbumIcon(@RequestParam String userId, String iconId){
        return null;
    }

    @GetMapping("/getUserIcon/{iconId}")
    public MultipartFile getIconFile(@PathVariable String iconId){
        return null;
    }

    


}
