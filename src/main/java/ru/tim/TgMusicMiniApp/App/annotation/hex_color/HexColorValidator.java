package ru.tim.TgMusicMiniApp.App.annotation.hex_color;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HexColorValidator implements ConstraintValidator<HexColor, String> {

    private static final Pattern HEX_STANDARD = Pattern.compile("^#[0-9a-fA-F]{6}$");

    @Override
    public boolean isValid(String hexColorString, ConstraintValidatorContext constraintValidatorContext) {
        if( hexColorString == null){
            return false;
        }
        Matcher matcher = HEX_STANDARD.matcher(hexColorString);
        return matcher.matches();
    }
}
