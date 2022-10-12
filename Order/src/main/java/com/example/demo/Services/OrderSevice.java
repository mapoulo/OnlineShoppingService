package com.example.demo.Services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.DTOs.OrderLineItemsDto;
import com.example.demo.DTOs.OrderRequest;
import com.example.demo.Entities.Order;
import com.example.demo.Entities.OrderLineItems;
import com.example.demo.Repositories.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderSevice {
	
	private  final OrderRepository orderRepo;
	
	
	public void saveOrder(OrderRequest orderRequest) {
		Order order  = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		
		List<OrderLineItems> orderList = orderRequest.getOrderLineItems().stream().map(orderItemDto -> mapDTOtoOrderLineItem(orderItemDto)).toList();
		order.setOrderLineItemsLists(orderList);
		
		orderRepo.save(order);
		log.info("saveOrder method in OrderService is executed");
	}
	
	
	private OrderLineItems mapDTOtoOrderLineItem(OrderLineItemsDto orderLineItemsDto) {
		OrderLineItems orderLineItems = new OrderLineItems();
		orderLineItems.setPrice(orderLineItemsDto.getPrice());
		orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
		orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
		return orderLineItems;
	}

}
