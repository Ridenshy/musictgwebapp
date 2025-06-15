package ru.tim.TgMusicMiniApp.App.annotation.hex_color;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = HexColorValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface HexColor {
    String message() default "Invalid hex color, must be #RRGGBB format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
