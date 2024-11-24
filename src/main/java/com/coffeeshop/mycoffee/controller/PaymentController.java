package com.coffeeshop.mycoffee.controller;

import com.coffeeshop.mycoffee.dto.ApiResponse;
import com.coffeeshop.mycoffee.dto.paymentdto.request.PaymentCreationRequest;
import com.coffeeshop.mycoffee.dto.paymentdto.request.PaymentUpdateRequest;
import com.coffeeshop.mycoffee.dto.paymentdto.response.PaymentResponse;
import com.coffeeshop.mycoffee.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Payment", description = "APIs for payment")
public class PaymentController {

    PaymentService paymentService;

    @Operation(summary = "Create a new payment")
    @PostMapping
    ApiResponse<PaymentResponse> createProduct(@RequestBody @Valid PaymentCreationRequest request){
        return ApiResponse.<PaymentResponse>builder()
                .result(paymentService.createPayment(request))
                .build();
    }

    @Operation(summary = "Get all payments")
    @GetMapping
    ApiResponse<List<PaymentResponse>> getPayments(){
        return ApiResponse.<List<PaymentResponse>>builder()
                .result(paymentService.getPayments())
                .build();
    }

    @Operation(summary = "Update a payment by ID")
    @PutMapping("/{paymentId}")
    ApiResponse<PaymentResponse> updatePayment(@PathVariable("paymentId") String paymentId, @RequestBody @Valid PaymentUpdateRequest request){
        return ApiResponse.<PaymentResponse>builder()
                .result(paymentService.updatePayment(paymentId, request))
                .build();
    }

    @Operation(summary = "Delete a payment by ID")
    @DeleteMapping("/{paymentId}")
    ApiResponse<String> deletePayment(@PathVariable("paymentId") String paymentId){

        paymentService.deletePayment(paymentId);
        return ApiResponse.<String>builder()
                .result("Payment has been deleted")
                .build();
    }
}
