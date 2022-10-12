package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.Entities.Inventory;
import com.example.demo.Repositories.InventoryRepo;

@SpringBootApplication
public class InventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryApplication.class, args);
	}
	
	
	@Bean
	public CommandLineRunner loadData(InventoryRepo repo) {
		return args->{
			
			Inventory i1 = new Inventory();
			i1.setSkuCode("Nokia");
			i1.setQuantity(2);
			
			Inventory i2 = new Inventory();
			i1.setSkuCode("iPhone");
			i1.setQuantity(10);
			
			repo.save(i1);
			repo.save(i2);
		};
	}

}
