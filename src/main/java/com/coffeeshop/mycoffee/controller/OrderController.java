package com.coffeeshop.mycoffee.controller;

import com.coffeeshop.mycoffee.dto.ApiResponse;
import com.coffeeshop.mycoffee.dto.orderdto.request.OrderCreationRequest;
import com.coffeeshop.mycoffee.dto.orderdto.request.OrderUpdateRequest;
import com.coffeeshop.mycoffee.dto.orderdto.response.OrderResponse;
import com.coffeeshop.mycoffee.dto.paymentdto.request.PaymentUpdateRequest;
import com.coffeeshop.mycoffee.dto.paymentdto.response.PaymentResponse;
import com.coffeeshop.mycoffee.service.OrderService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderController {

    OrderService orderService;

    @PostMapping
    ApiResponse<OrderResponse> createOrder(@RequestBody @Valid OrderCreationRequest request){
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.createOrder(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<OrderResponse>> getOrders(){
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderService.getOrders())
                .build();
    }

    @PutMapping("/{orderId}")
    ApiResponse<OrderResponse> updateOrder(@PathVariable("orderId") String orderId, @RequestBody @Valid OrderUpdateRequest request){
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.updateOrder(orderId, request))
                .build();
    }

    @DeleteMapping("/{orderId}")
    ApiResponse<String> deletePayment(@PathVariable("orderId") String orderId){

        orderService.deleteOrder(orderId);
        return ApiResponse.<String>builder()
                .result("Order has been deleted")
                .build();
    }
}
