package com.coffeeshop.mycoffee.mapper;

import com.coffeeshop.mycoffee.dto.paymentdto.request.PaymentCreationRequest;
import com.coffeeshop.mycoffee.dto.paymentdto.response.PaymentResponse;
import com.coffeeshop.mycoffee.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    Payment toPayment(PaymentCreationRequest request);

    PaymentResponse toPaymentResponse(Payment payment);
}
