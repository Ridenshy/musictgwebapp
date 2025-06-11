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
    private final TextEncryptor textEncryptor;

    @GetMapping("/getList")
    public List<TrackDto> getTracks(@RequestParam String encUserId){
        Long userId = Long.parseLong(textEncryptor.decrypt(encUserId));
        return trackService.getAllUserTracks(userId);
    }

    @DeleteMapping("/delete")
    public List<TrackDto> deleteTrack(@RequestParam String encTrackId, @RequestParam String encUserId){
        Long userId = Long.parseLong(textEncryptor.decrypt(encUserId));
        Long trackId = Long.parseLong(textEncryptor.decrypt(encTrackId));
        return trackService.deleteTrack(trackId, userId);
    }

    @PatchMapping("/changeListPlace")
    public List<TrackDto> updateListPlace(@RequestParam Integer newPlace,
                                          @RequestParam String encTrackId,
                                          @RequestParam String encUserId){
        Long userId = Long.parseLong(textEncryptor.decrypt(encUserId));
        Long trackId = Long.parseLong(textEncryptor.decrypt(encTrackId));
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

    @PostMapping("/testEnc")
    public String testEnc(@RequestParam Long userId){
        String encryptedText = textEncryptor.encrypt(userId.toString());
        String decryptedText = textEncryptor.decrypt(encryptedText);
        return "Enc: " + encryptedText + "\nDec: " + decryptedText;
    }

    @PostMapping("/testDec")
    public String testDec(@RequestParam String encr){
        return textEncryptor.decrypt(encr);
    }

    //TODO: add playList methods

}
