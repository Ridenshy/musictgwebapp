package ru.tim.TgMusicMiniApp.telegram_bot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.tim.TgMusicMiniApp.telegram_bot.entity.UserInfo;

import java.util.List;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    @Query("SELECT u.tgUserId FROM UserInfo u")
    List<Long> getAllUniqueUsers();

}
