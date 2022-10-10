package com.example.demo.Controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Services.ProductService;
import com.example.demo.dto.ProductRequest;
import com.example.demo.dto.ProductResponse;

@RestController
@RequestMapping("/api/product")
@RefreshScope
public class ProductController {
	
	@Value("${test.name}")
	private String value;

		
	@Autowired
	private ProductService productService;
	
	
	@GetMapping("/value")
	public String getValue() {
		return value;
	}
	
	
	@PostMapping("/save")
	@ResponseStatus(HttpStatus.CREATED)
	public void saveProduct(@RequestBody ProductRequest product) {
		 productService.saveProduct(product);
	}
	
	
	@GetMapping("/")
	@ResponseStatus(HttpStatus.OK)
	public List<ProductResponse> getAllProducts(){
		return productService.getAllProducts();
	}
	
	
	@GetMapping("/getProductById/{productId}")
	@ResponseStatus(HttpStatus.OK)
	public ProductResponse getProductById(@PathVariable int productId) {
		return productService.getProductById(productId);
	}
	
	
	@DeleteMapping("/delete")
	@ResponseStatus(HttpStatus.OK)
	public String deletellProducts() {
		return productService.deleteAllProducts();
	}
}
