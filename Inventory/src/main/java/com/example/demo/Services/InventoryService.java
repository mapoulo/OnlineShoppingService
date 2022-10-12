package com.example.demo.Services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTOs.InventoryResponse;
import com.example.demo.Entities.Inventory;
import com.example.demo.Repositories.InventoryRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InventoryService {
	
	@Autowired
	private InventoryRepo inventoryRepo;
	
	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public List<InventoryResponse> isPresent(List<String> skuCode) {
		log.info("isPresent() in the InventoryService is calles");
		return inventoryRepo.findBySkuCodeIn(skuCode).stream().map(inventory -> 
		InventoryResponse.builder()
		.skuCode(inventory.getSkuCode())
		.isInstock(inventory.getQuantity()>0)
		.build()
				).toList();
	}

}
