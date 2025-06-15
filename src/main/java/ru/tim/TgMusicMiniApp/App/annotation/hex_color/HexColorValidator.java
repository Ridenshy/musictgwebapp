package ru.tim.TgMusicMiniApp.App.annotation.hex_color;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HexColorValidator implements ConstraintValidator<HexColor, String> {

    private static final String HEX_STANDARD = "^#[0-9a-fA-F]{6}$";

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s == null){
            return false;
        }
        return s.matches(HEX_STANDARD);
    }
}
