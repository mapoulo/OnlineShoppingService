package com.example.demo.Services;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.DTOs.InventoryResponse;
import com.example.demo.DTOs.OrderLineItemsDto;
import com.example.demo.DTOs.OrderRequest;
import com.example.demo.Entities.Order;
import com.example.demo.Entities.OrderLineItems;
import com.example.demo.Producers.OrderEvent;
import com.example.demo.Repositories.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderSevice {
	
	private  final OrderRepository orderRepo;
	private  final Tracer tracer;
	
	private KafkaTemplate<String, OrderEvent> kafkaTemplate;
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	public String saveOrder(OrderRequest orderRequest) {
        log.info("111111111111111");
		Order order  = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		
		List<OrderLineItems> orderList = orderRequest.getOrderLineItems().stream().map(orderItemDto -> mapDTOtoOrderLineItem(orderItemDto)).toList();
		order.setOrderLineItemsLists(orderList);
		
		List<String> skuCodes = order.getOrderLineItemsLists().stream().map(OrderLineItems::getSkuCode).toList();
		Span inventoryServiceLookup = tracer.nextSpan().name("inventoryServiceLookup");
		
		try(Tracer.SpanInScope spanInScope = tracer.withSpan(inventoryServiceLookup.start())){
			
			InventoryResponse[] inventoryArrayList =  webClientBuilder.build().get().uri("http://inventory-service/api/inventory", urlBuilder -> urlBuilder.queryParam("skuCode", skuCodes).build()).retrieve().bodyToMono(InventoryResponse[].class).block();

		    boolean allProductsInStock = Arrays.stream(inventoryArrayList).toList().size()>2;
		    if(allProductsInStock) {
				orderRepo.save(order);
				kafkaTemplate.send("notification", new OrderEvent(order.getOrderNumber()));
				log.info("saveOrder method in OrderService is executed. The order is placed successfully");
			    return "Order is placed successfully";
			}else {
				throw new IllegalArgumentException("Item is not in stock");
			}
			
		}finally {
			log.info("55555555555555");
			inventoryServiceLookup.start();
		}
		
	

		
	}
	
	
	private OrderLineItems mapDTOtoOrderLineItem(OrderLineItemsDto orderLineItemsDto) {
		OrderLineItems orderLineItems = new OrderLineItems();
		orderLineItems.setPrice(orderLineItemsDto.getPrice());
		orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
		orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
		return orderLineItems;
	}

}
