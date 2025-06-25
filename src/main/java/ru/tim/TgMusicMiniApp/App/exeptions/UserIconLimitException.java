package ru.tim.TgMusicMiniApp.App.exeptions;

public class UserIconLimitException extends RuntimeException{
    public UserIconLimitException() {
        super("User icon limit exceeded");
    }
}
