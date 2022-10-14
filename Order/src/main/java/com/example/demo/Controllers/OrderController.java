package com.example.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTOs.OrderRequest;
import com.example.demo.Services.OrderSevice;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/api/order")
public class OrderController {
	
	@Autowired
	private OrderSevice orderService;
	
	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	@CircuitBreaker(name="inventory", fallbackMethod = "saveOrderFallback")
	public String saveOrder(@RequestBody OrderRequest orderRequest) {
		orderService.saveOrder(orderRequest);
	   return "Order placed successfully";
	}
	
	
	public String saveOrderFallback(OrderRequest orderRequest, Exception e) {
		return "Ooops! Just Oooops!";
	}

}
