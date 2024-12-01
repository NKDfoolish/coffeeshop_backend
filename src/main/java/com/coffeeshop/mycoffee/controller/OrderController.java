package com.coffeeshop.mycoffee.controller;

import com.coffeeshop.mycoffee.dto.ApiResponse;
import com.coffeeshop.mycoffee.dto.orderdto.request.OrderCreationRequest;
import com.coffeeshop.mycoffee.dto.orderdto.request.OrderUpdateRequest;
import com.coffeeshop.mycoffee.dto.orderdto.response.OrderResponse;
import com.coffeeshop.mycoffee.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Order", description = "APIs for order")
public class OrderController {

    OrderService orderService;

    @Operation(summary = "Create a new order")
    @PostMapping
    ApiResponse<OrderResponse> createOrder(@RequestBody @Valid OrderCreationRequest request){
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.createOrder(request))
                .build();
    }

    @Operation(summary = "Get all orders")
    @GetMapping
    ApiResponse<List<OrderResponse>> getOrders(){
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderService.getOrders())
                .build();
    }

    @Operation(summary = "Update an order by ID")
    @PutMapping("/{orderId}")
    ApiResponse<OrderResponse> updateOrder(@PathVariable("orderId") String orderId, @RequestBody @Valid OrderUpdateRequest request){
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.updateOrder(orderId, request))
                .build();
    }

    @Operation(summary = "Delete an order by ID")
    @DeleteMapping("/{orderId}")
    ApiResponse<String> deletePayment(@PathVariable("orderId") String orderId){

        orderService.deleteOrder(orderId);
        return ApiResponse.<String>builder()
                .result("Order has been deleted")
                .build();
    }

    @Operation(summary = "Generate QR codes for all tables")
    @PostMapping("/generate-qrcodes")
    public ApiResponse<String> generateQRCodesForAllTables(@RequestParam int numberOfTables) {
        orderService.generateQRCodesForAllTables(numberOfTables);
        return ApiResponse.<String>builder()
                .result("QR Codes have been generated")
                .build();
    }

    @Operation(summary = "Get all orders with details")
    @GetMapping("/with-details")
    public ApiResponse<List<OrderResponse>> getOrdersWithDetails() {
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderService.getOrdersWithDetails())
                .build();
    }

}
