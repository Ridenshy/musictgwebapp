package ru.tim.TgMusicMiniApp.App.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.web.bind.annotation.*;
import ru.tim.TgMusicMiniApp.App.dto.track.TrackDto;
import ru.tim.TgMusicMiniApp.App.entity.track.TgUserTrack;
import ru.tim.TgMusicMiniApp.App.service.TrackService;

import java.util.List;

@RestController
@RequestMapping("/apiV1/track")
@RequiredArgsConstructor
public class TrackController {

    private final TrackService trackService;

    @GetMapping("/getList")
    public List<TrackDto> getTracks(@RequestParam String encUserId){
        return trackService.getAllUserTracks(encUserId);
    }

    @DeleteMapping("/delete")
    public List<TrackDto> deleteTrack(@RequestParam String encTrackId, @RequestParam String encUserId){
        return trackService.deleteTrack(encTrackId, encUserId);
    }

    @PatchMapping("/changeListPlace")
    public List<TrackDto> updateListPlace(@RequestParam Integer newPlace,
                                          @RequestParam String encTrackId,
                                          @RequestParam String encUserId){
        return trackService.updateListPlace(newPlace, encTrackId, encUserId);
    }

}
