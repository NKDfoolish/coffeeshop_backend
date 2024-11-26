package com.coffeeshop.mycoffee.dto.orderdto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderCreationRequest {

//    @NotBlank(message = "PaymentId is required")
//    String paymentId;

    @NotNull(message = "Table is required")
    int table;
}
