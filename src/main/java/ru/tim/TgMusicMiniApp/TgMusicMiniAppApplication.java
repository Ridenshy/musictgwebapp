package ru.tim.TgMusicMiniApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class TgMusicMiniAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TgMusicMiniAppApplication.class, args);
	}

}
