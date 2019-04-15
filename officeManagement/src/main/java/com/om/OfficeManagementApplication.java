package com.om;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.om.domain.StorageProperties;
import com.om.services.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class OfficeManagementApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(OfficeManagementApplication.class, args);
	}
	
	@Bean
    CommandLineRunner init(StorageService storageService) {
		return (args) -> {
            //storageService.deleteAll();
            storageService.init();
        };
    }

}

