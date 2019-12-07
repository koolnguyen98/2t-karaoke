package com.karaoke.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.karaoke.management.service.helper.FileStorageProperties;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class KaraokeManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(KaraokeManagementApplication.class, args);
	}

}
