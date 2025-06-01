package ru.tim.TgMusicMiniApp.App.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tim.TgMusicMiniApp.App.dto.settings.SettingsDto;
import ru.tim.TgMusicMiniApp.App.dto.settings.UpdatedSettings;
import ru.tim.TgMusicMiniApp.App.service.SettingsService;

import java.util.List;


@RestController
@RequestMapping("/apiV1/settings")
@RequiredArgsConstructor
public class SettingsController {

    private final SettingsService settingsService;

    @GetMapping("/getList")
    public List<SettingsDto> getSettingsDto(@RequestParam Long userId){
        return settingsService.getAllUserSettings(userId);
    }

    @PostMapping("/setCurrent")
    public SettingsDto setCurrentSettings(@RequestBody SettingsDto settingsDto){
        return settingsService.setActiveSettings(settingsDto);
    }

    @PatchMapping("/update")
    public SettingsDto updateSettings(@RequestBody UpdatedSettings updatedSettings){
        return settingsService.updateSettings(updatedSettings);
    }

}
