package com.coffeeshop.mycoffee.dto.orderdetaildto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailCreationRequest {
    @NotBlank(message = "Order ID is required")
    String orderId;

    @NotNull(message = "Quantity is required")
    int quantity;

    @NotBlank(message = "Product ID is required")
    String productId;
}
