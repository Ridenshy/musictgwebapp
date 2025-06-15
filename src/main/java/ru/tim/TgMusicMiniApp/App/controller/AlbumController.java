package ru.tim.TgMusicMiniApp.App.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.tim.TgMusicMiniApp.App.dto.album.AlbumDto;
import ru.tim.TgMusicMiniApp.App.dto.album.AlbumIconDto;
import ru.tim.TgMusicMiniApp.App.dto.album.NewAlbumDto;
import ru.tim.TgMusicMiniApp.App.service.AlbumService;

import java.awt.*;
import java.io.File;
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

    @PostMapping(value = "/create" )
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
}
