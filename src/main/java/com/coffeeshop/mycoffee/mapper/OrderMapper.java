package com.coffeeshop.mycoffee.mapper;

import com.coffeeshop.mycoffee.dto.orderdto.request.OrderCreationRequest;
import com.coffeeshop.mycoffee.dto.orderdto.response.OrderResponse;
import com.coffeeshop.mycoffee.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toOrder(OrderCreationRequest request);

    OrderResponse toOrderResponse(Order order);
}
