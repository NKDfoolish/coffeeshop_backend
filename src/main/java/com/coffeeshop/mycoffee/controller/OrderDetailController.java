package com.coffeeshop.mycoffee.controller;

import com.coffeeshop.mycoffee.dto.ApiResponse;
import com.coffeeshop.mycoffee.dto.orderdetaildto.request.OrderDetailCreationRequest;
import com.coffeeshop.mycoffee.dto.orderdetaildto.request.OrderDetailUpdateRequest;
import com.coffeeshop.mycoffee.dto.orderdetaildto.response.OrderDetailResponse;
import com.coffeeshop.mycoffee.service.OrderDetailService;
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
@RequestMapping("/order-detail")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Order Detail", description = "APIs for order detail")
public class OrderDetailController {
    OrderDetailService orderDetailService;

    @Operation(summary = "Create a new order detail")
    @PostMapping
    ApiResponse<OrderDetailResponse> createOrderDetail(@RequestBody @Valid OrderDetailCreationRequest request){
        return ApiResponse.<OrderDetailResponse>builder()
                .result(orderDetailService.createOrderDetail(request))
                .build();
    }

    @Operation(summary = "Get all order details")
    @GetMapping
    ApiResponse<List<OrderDetailResponse>> getOrderDetails(){
        return ApiResponse.<List<OrderDetailResponse>>builder()
                .result(orderDetailService.getOrderDetails())
                .build();
    }

    @Operation(summary = "Update an order detail by ID")
    @PutMapping("/{orderDetailId}")
    ApiResponse<OrderDetailResponse> updateOrderDetail(@PathVariable("orderDetailId") String orderDetailId, @RequestBody @Valid OrderDetailUpdateRequest request){
        return ApiResponse.<OrderDetailResponse>builder()
                .result(orderDetailService.updateOrderDetail(orderDetailId,request))
                .build();
    }

    @Operation(summary = "Delete an order detail by ID")
    @DeleteMapping("/{orderDetailId}")
    ApiResponse<String> deleteOrderDetail(@PathVariable("orderDetailId") String orderDetailId){
        orderDetailService.deleteOrderDetail(orderDetailId);
        return ApiResponse.<String>builder()
                .result("Order detail has been deleted")
                .build();
    }

}
