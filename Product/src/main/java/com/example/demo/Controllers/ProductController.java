package com.example.demo.Controllers;

import java.util.List;

import javax.persistence.PostRemove;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entities.Product;
import com.example.demo.Services.ProductService;

@RestController
@RequestMapping("/api/products")
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
	public Product saveProduct(@RequestBody Product product) {
		return productService.saveProduct(product);
	}
	
	
	@GetMapping("/")
	public List<Product> getAllProducts(){
		return productService.getAllProducts();
	}
	
	
	@GetMapping("/getProductById/{productId}")
	public Product getProductById(@PathVariable int productId) {
		return productService.getProductById(productId);
	}
	
	
	@DeleteMapping("/delete")
	public String deletellProducts() {
		return productService.deleteAllProducts();
	}
}
