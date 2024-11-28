package com.coffeeshop.mycoffee.dto.orderdetaildto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailResponse {
    String id;

    String orderId;

    int quantity;

    String productId;
}
