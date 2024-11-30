package com.coffeeshop.mycoffee.service;

import com.coffeeshop.mycoffee.dto.orderdetaildto.response.OrderDetailResponse;
import com.coffeeshop.mycoffee.dto.orderdto.request.OrderCreationRequest;
import com.coffeeshop.mycoffee.dto.orderdto.request.OrderUpdateRequest;
import com.coffeeshop.mycoffee.dto.orderdto.response.OrderResponse;
import com.coffeeshop.mycoffee.entity.Order;
import com.coffeeshop.mycoffee.entity.Payment;
import com.coffeeshop.mycoffee.entity.User;
import com.coffeeshop.mycoffee.exception.AppException;
import com.coffeeshop.mycoffee.exception.ErrorCode;
import com.coffeeshop.mycoffee.mapper.OrderMapper;
import com.coffeeshop.mycoffee.repository.OrderRepository;
import com.coffeeshop.mycoffee.repository.PaymentRepository;
import com.coffeeshop.mycoffee.repository.UserRepository;
import com.coffeeshop.mycoffee.util.QRCodeGenerator;
import com.google.zxing.WriterException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderService {

    OrderRepository orderRepository;
    OrderMapper orderMapper;
    UserRepository userRepository;
    PaymentRepository paymentRepository;

//    @PreAuthorize("hasRole('ADMIN')")
    public OrderResponse createOrder(OrderCreationRequest request){
//        var context = SecurityContextHolder.getContext();
//        String name = context.getAuthentication().getName();
//        log.info("name: {}", name);
//
//        User user = userRepository.findByUsername(name)
//                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
//        Payment payment = paymentRepository.findById(request.getPaymentId())
//                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_EXISTED));

        Order order = orderMapper.toOrder(request);
        order.setStatus("NONE");
//        order.setUser(user);
//        order.setPayment(payment);

        try {
            order = orderRepository.save(order);
        } catch (DataIntegrityViolationException e){
            throw new AppException(ErrorCode.ORDER_EXISTED);
        }

        return OrderResponse.builder()
//                .userId(user.getId())
//                .paymentId(payment.getId())
                .orderId(order.getId())
                .table(order.getTable())
                .status(order.getStatus())
                .totalPrice(order.getTotal_price())
                .build();

    }

//    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderResponse> getOrders(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return orderRepository.findAll().stream().map(order ->
            OrderResponse.builder()
                    .totalPrice(order.getTotal_price())
//                    .userId(order.getUser().getId())
                    .table(order.getTable())
//                    .paymentId(order.getPayment().getId())
                    .orderId(order.getId())
                    .status(order.getStatus())
                    .created_at(order.getCreatedAt().format(formatter))
                    .build()
        ).toList();
    }

    public List<OrderResponse> getOrdersWithDetails() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(order -> {
            OrderResponse orderResponse = OrderResponse.builder()
                    .orderId(order.getId())
                    .table(order.getTable())
                    .totalPrice(order.getTotal_price())
                    .status(order.getStatus())
                    .created_at(order.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))
                    .orderDetails(order.getOrderDetails().stream().map(orderDetail -> {
                        return OrderDetailResponse.builder()
                                .id(orderDetail.getId())
                                .orderId(orderDetail.getOrder().getId())
                                .quantity(orderDetail.getQuantity())
                                .productId(orderDetail.getProduct().getId())
                                .image(orderDetail.getProduct().getImageUrl())
                                .price(orderDetail.getProduct().getPrice() * orderDetail.getQuantity())
                                .productName(orderDetail.getProduct().getName())
                                .build();
                    }).collect(Collectors.toList()))
                    .build();
            return orderResponse;
        }).collect(Collectors.toList());
    }

//    @PreAuthorize("hasRole('ADMIN')")
    public OrderResponse updateOrder(String orderId, OrderUpdateRequest request){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXISTED));

        if (request.getPaymentId() != null) {
            Payment payment = paymentRepository.findById(request.getPaymentId())
                    .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_EXISTED));
            order.setPayment(payment);
        }

        if (request.getTable() != null) {
            order.setTable(request.getTable());
        }

        if (request.getTotalPrice() != null) {
            order.setTotal_price(request.getTotalPrice());
        }

        if (request.getStatus() != null) {
            order.setStatus(request.getStatus());
        }

        Order orderResult = orderRepository.save(order);

        return OrderResponse.builder()
//                .paymentId(orderResult.getPayment().getId())
                .table(orderResult.getTable())
//                .userId(orderResult.getUser().getId())
                .totalPrice(order.getTotal_price())
                .status(order.getStatus())
                .build();
    }

//    @PreAuthorize("hasRole('ADMIN')")
    public void deleteOrder(String orderId){
        orderRepository.deleteById(orderId);
    }

    public void generateQRCodesForAllTables(int numberOfTables) {
        for (int i = 1; i <= numberOfTables; i++) {
            generateTableQRCode(i);
        }
    }

    public void generateTableQRCode(int tableNumber) {
        String qrCodeText = "https://yourwebsite.com/menu?table=" + tableNumber;
        String qrCodePath = "images/qrcodes/table_" + tableNumber + ".png";
        try {
            // Create directories if they do not exist
            Files.createDirectories(Paths.get("images/qrcodes"));

            QRCodeGenerator.generateQRCodeImage(qrCodeText, 350, 350, qrCodePath);
        } catch (WriterException | IOException e) {
            log.error("Error generating QR code for table " + tableNumber, e);
            throw new AppException(ErrorCode.QR_CODE_GENERATION_FAILED);
        }
    }
}
