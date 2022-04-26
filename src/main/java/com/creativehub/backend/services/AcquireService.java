package com.creativehub.backend.services;

import com.creativehub.backend.services.dto.OrderDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AcquireService {
	String acquireArtwork(OrderDto OrderDto);

	List<OrderDto> getAllOrders(UUID id);

	Optional<OrderDto> findOrderById(UUID id);

	void updateOrder(UUID id, OrderDto orderDto);

	void deleteOrderById(UUID id);

	String successAcquire(String paymentId, String payerId);
}
