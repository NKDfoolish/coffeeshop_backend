package com.coffeeshop.mycoffee.mapper;

import com.coffeeshop.mycoffee.dto.orderdetaildto.request.OrderDetailCreationRequest;
import com.coffeeshop.mycoffee.dto.orderdetaildto.response.OrderDetailResponse;
import com.coffeeshop.mycoffee.entity.OrderDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
    OrderDetail toOrderDetail(OrderDetailCreationRequest request);

    OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail);
}
