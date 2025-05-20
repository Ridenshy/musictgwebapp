package ru.tim.TgMusicMiniApp.App.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tim.TgMusicMiniApp.App.service.PlaySetService;
import ru.tim.TgMusicMiniApp.telegram_bot.TelegramBot;

@RestController
@RequestMapping("/apiV1/playSet")
@RequiredArgsConstructor
public class PlaySetController {

    private final PlaySetService playSetService;
    private final TelegramBot bot;

    @PostMapping("/generateStraight")
    public String generateStandardPlaySet(@RequestParam Long userId){
        playSetService.generateStandardPlaySet(userId);
        bot.sendPlaySetTracks(userId);
        return "Play set generated";
    }

}
