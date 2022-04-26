package com.creativehub.backend.services.mapper;

import com.creativehub.backend.models.Order;
import com.creativehub.backend.services.dto.OrderDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface OrderMapper {
	Order orderDtoToOrder(OrderDto orderDto);

	OrderDto orderToOrderDto(Order order);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateOrderFromOrderDto(OrderDto orderDto, @MappingTarget Order order);
}
