package com.example.demo.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repositories.InventoryRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InventoryService {
	
	@Autowired
	private InventoryRepo inventoryRepo;
	
	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public boolean isPresent(String skuCode) {
		log.info("isPresent() in the InventoryService is calles");
		return inventoryRepo.findBySkuCode(skuCode).isPresent();
	}

}
