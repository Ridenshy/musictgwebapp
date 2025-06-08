package ru.tim.TgMusicMiniApp.telegram_bot.utility;

import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.objects.menubutton.MenuButton;
import org.telegram.telegrambots.meta.api.objects.menubutton.MenuButtonWebApp;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;

import java.util.Collections;
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

    public static MenuButtonWebApp webAppButton(){

        return MenuButtonWebApp.builder()
                .webAppInfo(new WebAppInfo("https://www.wikipedia.org"))
                .text("Open")
                .build();

    }


}
