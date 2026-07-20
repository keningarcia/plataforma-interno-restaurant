package com.keningarcia.plantaforma_interno_restaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = {
		"com.keningarcia.plantaforma_interno_restaurant",
		"controller",
		"dto",
		"entity",
		"exception",
		"mapper",
		"repository",
		"service"
})
@EnableJpaAuditing
public class PlantaformaInternoRestaurantApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlantaformaInternoRestaurantApplication.class, args);
	}

}
