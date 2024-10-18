package com.coffeeshop.mycoffee.dto.orderdto.response;

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
