package com.coffeeshop.mycoffee.service;

import com.coffeeshop.mycoffee.dto.paymentdto.request.PaymentCreationRequest;
import com.coffeeshop.mycoffee.dto.paymentdto.request.PaymentUpdateRequest;
import com.coffeeshop.mycoffee.dto.paymentdto.response.PaymentResponse;
import com.coffeeshop.mycoffee.entity.Payment;
import com.coffeeshop.mycoffee.exception.AppException;
import com.coffeeshop.mycoffee.exception.ErrorCode;
import com.coffeeshop.mycoffee.mapper.PaymentMapper;
import com.coffeeshop.mycoffee.repository.PaymentRepository;
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
public class PaymentService {

    PaymentRepository paymentRepository;
    PaymentMapper paymentMapper;

//    @PreAuthorize("hasRole('ADMIN')")
    public PaymentResponse createPayment(PaymentCreationRequest request){
        Payment payment = paymentMapper.toPayment(request);

        try {
            payment = paymentRepository.save(payment);
        }catch (DataIntegrityViolationException e){
            throw new AppException(ErrorCode.PAYMENT_EXISTED);
        }

        return paymentMapper.toPaymentResponse(payment);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    public List<PaymentResponse> getPayments(){
        return paymentRepository.findAll().stream().map(paymentMapper::toPaymentResponse).toList();
    }

//    @PreAuthorize("hasRole('ADMIN')")
    public PaymentResponse updatePayment(String paymentId, PaymentUpdateRequest request){
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_EXISTED));

        // Cập nhật `name` nếu `request.name` không phải là null
        if (request.getType() != null) {
            payment.setType(request.getType());
        }

        return paymentMapper.toPaymentResponse(paymentRepository.save(payment));
    }

//    @PreAuthorize("hasRole('ADMIN')")
    public void deletePayment(String paymentId){
        paymentRepository.deleteById(paymentId);
    }
}
