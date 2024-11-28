package com.coffeeshop.mycoffee.dto.orderdto.response;

import com.coffeeshop.mycoffee.dto.orderdetaildto.response.OrderDetailResponse;
import com.coffeeshop.mycoffee.entity.OrderDetail;
import com.coffeeshop.mycoffee.entity.Payment;
import com.coffeeshop.mycoffee.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {

    String orderId;
//    String userId;

//    String paymentId;

    int table;

    float totalPrice;

    List<OrderDetailResponse> orderDetails;


}
