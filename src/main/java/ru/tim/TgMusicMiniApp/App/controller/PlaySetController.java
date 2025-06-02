package ru.tim.TgMusicMiniApp.App.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tim.TgMusicMiniApp.App.service.PlaySetService;
import ru.tim.TgMusicMiniApp.telegram_bot.service.CallBackHandler;

import java.util.List;

@RestController
@RequestMapping("/apiV1/playSet")
@RequiredArgsConstructor
public class PlaySetController {

    private final PlaySetService playSetService;
    private final CallBackHandler callBackHandler;

    @PostMapping("/generateStandard")
    public String generateStandardPlaySet(@RequestParam Long userId){
        playSetService.generateStandardPlaySet(userId);
        callBackHandler.sendPlaySetTracks(userId, "");
        return "S play set generated";
    }

    @PostMapping("/generateStartWith")
    public String generateStartWithPlaySet(@RequestParam Long userId, @RequestParam Integer listPosition){
        playSetService.generateStartWithPlaySet(userId, listPosition);
        callBackHandler.sendPlaySetTracks(userId, "");
        return "SW play set generated";
    }

    @PostMapping("/generatePack")
    public String generatePackPlaySet(@RequestParam Long userId, @RequestParam List<Integer> trackPositionList){
        playSetService.generatePackPlaySet(userId, trackPositionList);
        callBackHandler.sendPlaySetTracks(userId, "");
        return "SW play set generated";
    }

}
