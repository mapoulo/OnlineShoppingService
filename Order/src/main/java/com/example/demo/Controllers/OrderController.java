package com.example.demo.Controllers;

import java.util.concurrent.CompletableFuture;

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
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RestController
@RequestMapping("/api/order")
public class OrderController {
	
	@Autowired
	private OrderSevice orderService;
	
	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	@CircuitBreaker(name="inventory", fallbackMethod = "saveOrderFallback")
	@TimeLimiter(name="inventory")
	@Retry(name="inventory")
	public CompletableFuture<String> saveOrder(@RequestBody OrderRequest orderRequest) {
		return CompletableFuture.supplyAsync(()-> orderService.saveOrder(orderRequest));
	}
	
	
	public CompletableFuture<String> saveOrderFallback(OrderRequest orderRequest, Exception e) {
		return  CompletableFuture.supplyAsync(()-> "Ooops! Just Oooops!");
	}

}
