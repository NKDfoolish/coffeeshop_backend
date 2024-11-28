package com.coffeeshop.mycoffee.dto.orderdetaildto.response;

import com.coffeeshop.mycoffee.entity.Product;
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

    String productName;

    String image;

    float price;
}
