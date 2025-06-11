package ru.tim.TgMusicMiniApp.App.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.encrypt.TextEncryptor;
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

    private final TextEncryptor textEncryptor;

    @GetMapping("/getList")
    public List<SettingsDto> getSettingsDto(@RequestParam String encUserId){
        Long userId = Long.parseLong(textEncryptor.decrypt(encUserId));
        return settingsService.getAllUserSettings(userId);
    }

    @PostMapping("/setCurrent")
    public SettingsDto setCurrentSettings(@RequestBody @Valid SettingsDto settingsDto){
        return settingsService.setActiveSettings(settingsDto);
    }

    @PatchMapping("/update")
    public SettingsDto updateSettings(@RequestBody @Valid UpdatedSettings updatedSettings){
        return settingsService.updateSettings(updatedSettings);
    }

}
