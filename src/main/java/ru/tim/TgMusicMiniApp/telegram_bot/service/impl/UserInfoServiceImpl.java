package ru.tim.TgMusicMiniApp.telegram_bot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tim.TgMusicMiniApp.telegram_bot.entity.UserInfo;
import ru.tim.TgMusicMiniApp.telegram_bot.repo.UserInfoRepository;
import ru.tim.TgMusicMiniApp.telegram_bot.service.UserInfoService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;

    @Override
    public boolean isUserExists(Long userId) {
        return userInfoRepository.existsByTgUserId(userId);
    }

    @Override
    public void addNewUser(Long userId) {
        userInfoRepository.save(UserInfo.builder()
                .tgUserId(userId)
                .build());
    }

    @Override
    public List<Long> getAllUniqueUserIds() {
        return userInfoRepository.getAllUniqueUsers();
    }


}
