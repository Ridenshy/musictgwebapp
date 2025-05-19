package ru.tim.TgMusicMiniApp.App.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tim.TgMusicMiniApp.App.dto.TrackDto;
import ru.tim.TgMusicMiniApp.App.service.TrackService;

import java.util.List;

@RestController
@RequestMapping("/apiV1/track")
@RequiredArgsConstructor
public class TrackController {

    private final TrackService trackService;

    @GetMapping("/getList")
    public List<TrackDto> getTracks(@RequestParam Long userId){
        return trackService.getAllUserTracks(userId);
    }

    @DeleteMapping("/delete")
    public List<TrackDto> deleteTrack(@RequestParam Long trackId, @RequestParam Long userId){
        return trackService.deleteTrack(trackId, userId);
    }

    @PatchMapping("/changeListPlace")
    public List<TrackDto> updateListPlace(@RequestParam Integer newPlace, @RequestParam Long trackId, @RequestParam Long userId){
        return trackService.updateListPlace(newPlace, trackId, userId);
    }


}
