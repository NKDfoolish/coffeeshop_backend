package com.coffeeshop.mycoffee.dto.orderdto.response;

import com.coffeeshop.mycoffee.entity.Payment;
import com.coffeeshop.mycoffee.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {

    String userId;

    String paymentId;

    int table;

    float totalPrice;
}
