package com.example.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
