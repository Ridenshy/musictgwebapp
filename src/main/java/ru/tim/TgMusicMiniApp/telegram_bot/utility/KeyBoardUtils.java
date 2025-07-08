package ru.tim.TgMusicMiniApp.telegram_bot.utility;

import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.meta.api.objects.menubutton.MenuButtonWebApp;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;

import java.util.List;


@UtilityClass
public class KeyBoardUtils {

    public static InlineKeyboardMarkup moveButton(){
        InlineKeyboardButton nextButton = InlineKeyboardButton.builder()
                .text("Далее ⏩️")
                .callbackData("next_")
                .build();
        InlineKeyboardButton backButton = InlineKeyboardButton.builder()
                .text("⏪ Назад")
                .callbackData("back_")
                .build();

        return  InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(backButton, nextButton))
                .build();
    }

    public static MenuButtonWebApp webAppButton(String userId){
        String webAppUrl = String.format("https://ridporj.axldev.space:8443/?userId=%s", userId);
        System.out.println(webAppUrl);
        return MenuButtonWebApp.builder()
                .webAppInfo(new WebAppInfo(webAppUrl))
                .text("Open App test")
                .build();

    }

}
