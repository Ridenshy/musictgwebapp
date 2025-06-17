package ru.tim.TgMusicMiniApp.App.controller;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.web.bind.annotation.*;
import ru.tim.TgMusicMiniApp.App.service.PlaySetService;
import ru.tim.TgMusicMiniApp.telegram_bot.service.CallBackHandler;

import java.util.List;

@RestController
@RequestMapping("/apiV1/playSet")
@RequiredArgsConstructor
public class PlaySetController {

    private final PlaySetService playSetService;
    private final CallBackHandler callBackHandler;

    private final TextEncryptor textEncryptor;

    @PostMapping("/generateStandard")
    public String generateStandardPlaySet(@RequestParam @NotNull String encUserId){
        Long userId = Long.parseLong(textEncryptor.decrypt(encUserId));
        playSetService.generateStandardPlaySet(userId);
        callBackHandler.sendPlaySetTracks(userId, "");
        return "S play set generated";
    }

    @PostMapping("/generateStartWith")
    public String generateStartWithPlaySet(@RequestParam @NotNull String encUserId,
                                           @RequestParam @NotNull String encTrackId){
        Long userId = Long.parseLong(textEncryptor.decrypt(encUserId));
        Long trackId = Long.parseLong(textEncryptor.decrypt(encTrackId));
        playSetService.generateStartWithPlaySet(userId, trackId);
        callBackHandler.sendPlaySetTracks(userId, "");
        return "SW play set generated";
    }

    @PostMapping("/generatePack")
    public String generatePackPlaySet(@RequestParam @NotNull String encUserId,
                                      @RequestParam @NotEmpty List<String> encTrackIdList){
        List<Long> trackIdList = encTrackIdList.stream()
                .map(id -> Long.parseLong(textEncryptor.decrypt(id))).toList();
        Long userId = Long.parseLong(textEncryptor.decrypt(encUserId));
        playSetService.generatePackPlaySet(userId, trackIdList);
        callBackHandler.sendPlaySetTracks(userId, "");
        return "SW play set generated";
    }

}
