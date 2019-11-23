package com.karaoke.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KaraokeManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(KaraokeManagementApplication.class, args);
	}

}
