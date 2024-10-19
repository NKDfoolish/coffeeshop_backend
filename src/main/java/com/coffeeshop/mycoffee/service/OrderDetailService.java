package com.coffeeshop.mycoffee.service;

import com.coffeeshop.mycoffee.dto.orderdetaildto.request.OrderDetailCreationRequest;
import com.coffeeshop.mycoffee.dto.orderdetaildto.request.OrderDetailUpdateRequest;
import com.coffeeshop.mycoffee.dto.orderdetaildto.response.OrderDetailResponse;
import com.coffeeshop.mycoffee.entity.Order;
import com.coffeeshop.mycoffee.entity.OrderDetail;
import com.coffeeshop.mycoffee.entity.Product;
import com.coffeeshop.mycoffee.exception.AppException;
import com.coffeeshop.mycoffee.exception.ErrorCode;
import com.coffeeshop.mycoffee.mapper.OrderDetailMapper;
import com.coffeeshop.mycoffee.repository.OrderDetailRepository;
import com.coffeeshop.mycoffee.repository.OrderRepository;
import com.coffeeshop.mycoffee.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderDetailService {

    OrderDetailRepository orderDetailRepository;
    OrderDetailMapper orderDetailMapper;
    OrderRepository orderRepository;
    ProductRepository productRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public OrderDetailResponse createOrderDetail(OrderDetailCreationRequest request){
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXISTED));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .product(product)
                .quantity(request.getQuantity())
                .build();

        try {
            orderDetail = orderDetailRepository.save(orderDetail);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.ORDER_DETAIL_NOT_EXISTED);
        }

        return OrderDetailResponse.builder()
                .orderId(orderDetail.getOrder().getId())
                .quantity(orderDetail.getQuantity())
                .productId(orderDetail.getProduct().getId())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderDetailResponse> getOrderDetails(){
        return orderDetailRepository.findAll().stream().map(orderDetail ->
                OrderDetailResponse.builder()
                        .quantity(orderDetail.getQuantity())
                        .orderId(orderDetail.getOrder().getId())
                        .productId(orderDetail.getProduct().getId())
                        .build()).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public OrderDetailResponse updateOrderDetail(String orderDetailId, OrderDetailUpdateRequest request){
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_DETAIL_NOT_EXISTED));

        if (request.getOrderId() != null){
            Order order = orderRepository.findById(request.getOrderId())
                    .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXISTED));
            orderDetail.setOrder(order);
        }

        if (request.getProductId() != null){
            Product product = productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
            orderDetail.setProduct(product);
        }

        if (request.getQuantity() != null){
            orderDetail.setQuantity(request.getQuantity());
        }

        OrderDetail orderDetailResult = orderDetailRepository.save(orderDetail);

        return OrderDetailResponse.builder()
                .quantity(orderDetailResult.getQuantity())
                .productId(orderDetailResult.getProduct().getId())
                .orderId(orderDetailResult.getOrder().getId())
                .build();

    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteOrderDetail(String orderDetailId){
        orderDetailRepository.deleteById(orderDetailId);
    }
}
