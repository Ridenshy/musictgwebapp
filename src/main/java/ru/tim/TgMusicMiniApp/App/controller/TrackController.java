package ru.tim.TgMusicMiniApp.App.controller;


import lombok.RequiredArgsConstructor;
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
    public List<TrackDto> getTracks(@RequestParam Long userId){
        return trackService.getAllUserTracks(userId);
    }

    @DeleteMapping("/delete")
    public List<TrackDto> deleteTrack(@RequestParam Long trackId, @RequestParam Long userId){
        return trackService.deleteTrack(trackId, userId);
    }

    @PatchMapping("/changeListPlace")
    public List<TrackDto> updateListPlace(@RequestParam Integer newPlace,
                                          @RequestParam Long trackId,
                                          @RequestParam Long userId){
        return trackService.updateListPlace(newPlace, trackId, userId);
    }

    @PostMapping("/testAddTracks")
    public String testAddTracks(@RequestParam Long userId){
        for(int i = 0; i <= 100; i++){
            TgUserTrack trackEntity = TgUserTrack
                    .builder()
                    .audioTgId("0")
                    .title("title_" + i)
                    .artist("artist_" + i)
                    .duration(100)
                    .tgUserId(userId)
                    .listPlace(trackService.getTracksAmount(userId))
                    .build();
            trackService.saveTgUserTrack(trackEntity);
        }
        return "success";
    }

    //TODO: add playList methods

}
