package com.coffeeshop.mycoffee.dto.paymentdto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentResponse {

    String type;
    String status;
    String createdAt;
}
