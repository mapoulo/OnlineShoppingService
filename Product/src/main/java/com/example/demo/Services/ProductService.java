package com.example.demo.Services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.Product;
import com.example.demo.Repositories.ProductRepo;
import com.example.demo.dto.ProductRequest;
import com.example.demo.dto.ProductResponse;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class ProductService {
	
	
	@Autowired
	private ProductRepo productRepo;
	
	
	public void saveProduct(ProductRequest productResponse) {
		
		Product product  = Product.builder()
				           .name(productResponse.getName())
				           .price(productResponse.getPrice())
				           .productId(0).build();
		
		Product myProduct =  productRepo.save(product);
		log.info("Product with id {} is saved "+myProduct.getProductId());
	}
	
	
	public List<ProductResponse> getAllProducts(){
		log.info("getAllProcts int the Product service is called");
		return productRepo.findAll().stream().map(product -> mapProductToProductResponse(product)).toList();
	}
	
	
	
	private ProductResponse mapProductToProductResponse(Product product) {
		return ProductResponse.builder()
				              .name(product.getName())
				              .price(product.getPrice())
				              .productId(product.getProductId()).build();
	}
	
	
	
	
    public ProductResponse  getProductById(int productId){
    	Product myProduct =  productRepo.findById(productId).orElse(new Product("UNKNOWN", 0, 0));
    	ProductResponse productResponse = mapProductToProductResponse(myProduct);
    	log.info("The getProductById method is called");
    	return productResponse;
    }
    
    
    public String deleteAllProducts() {
		log.info("deleteAllProducts int the Product service is called");
    	productRepo.deleteAll();
    	return "Products Deleted";
    }

}
