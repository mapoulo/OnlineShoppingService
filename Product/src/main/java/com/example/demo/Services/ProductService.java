package com.example.demo.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.Product;
import com.example.demo.Repositories.ProductRepo;


@Service
public class ProductService {
	
	
	@Autowired
	private ProductRepo productRepo;
	
	
	public Product saveProduct(Product product) {
		return productRepo.save(product);
	}
	
	
	public List<Product> getAllProducts(){
		return productRepo.findAll().stream().collect(Collectors.toList());
	}
	
    public Product getProductById(int productId){
    	return productRepo.findById(productId).get();
    }
    
    
    public String deleteAllProducts() {
    	productRepo.deleteAll();
    	return "Products Deleted";
    }

}
