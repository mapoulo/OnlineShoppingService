package com.example.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entities.Product;

public interface ProductRepo extends JpaRepository<Product, Integer> {

}
