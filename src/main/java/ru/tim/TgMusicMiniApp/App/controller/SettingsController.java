package ru.tim.TgMusicMiniApp.App.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
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
    public List<SettingsDto> getSettingsDto(@RequestParam @NotNull String encUserId){
        return settingsService.getAllUserSettings(encUserId);
    }

    @PostMapping("/setCurrent")
    public SettingsDto setCurrentSettings(@Validated @RequestBody SettingsDto settingsDto){
        return settingsService.setActiveSettings(settingsDto);
    }

    @PatchMapping("/update")
    public SettingsDto updateSettings(@Validated @RequestBody UpdatedSettings updatedSettings){
        return settingsService.updateSettings(updatedSettings);
    }

}
