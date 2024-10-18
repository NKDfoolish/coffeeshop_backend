package com.coffeeshop.mycoffee.controller;

import com.coffeeshop.mycoffee.dto.ApiResponse;
import com.coffeeshop.mycoffee.dto.paymentdto.request.PaymentCreationRequest;
import com.coffeeshop.mycoffee.dto.paymentdto.request.PaymentUpdateRequest;
import com.coffeeshop.mycoffee.dto.paymentdto.response.PaymentResponse;
import com.coffeeshop.mycoffee.service.PaymentService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PaymentController {

    PaymentService paymentService;

    @PostMapping
    ApiResponse<PaymentResponse> createProduct(@RequestBody @Valid PaymentCreationRequest request){
        return ApiResponse.<PaymentResponse>builder()
                .result(paymentService.createPayment(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<PaymentResponse>> getPayments(){
        return ApiResponse.<List<PaymentResponse>>builder()
                .result(paymentService.getPayments())
                .build();
    }

    @PutMapping("/{paymentId}")
    ApiResponse<PaymentResponse> updatePayment(@PathVariable("paymentId") String paymentId, @RequestBody @Valid PaymentUpdateRequest request){
        return ApiResponse.<PaymentResponse>builder()
                .result(paymentService.updatePayment(paymentId, request))
                .build();
    }

    @DeleteMapping("/{paymentId}")
    ApiResponse<String> deletePayment(@PathVariable("paymentId") String paymentId){

        paymentService.deletePayment(paymentId);
        return ApiResponse.<String>builder()
                .result("Payment has been deleted")
                .build();
    }
}
