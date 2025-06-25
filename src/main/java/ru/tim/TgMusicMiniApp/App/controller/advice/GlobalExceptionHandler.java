package ru.tim.TgMusicMiniApp.App.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tim.TgMusicMiniApp.App.exeptions.UserIconLimitException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserIconLimitException.class)
    public ResponseEntity<String> handleUserIconLimit(UserIconLimitException e){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}
