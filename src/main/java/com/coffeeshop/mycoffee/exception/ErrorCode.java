package com.coffeeshop.mycoffee.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZE_EXCEPTION(9999, "Uncategorize error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorize error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "You are must be at least {min}", HttpStatus.BAD_REQUEST),
    CATEGORY_EXISTED(1009, "Category existed", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_EXISTED(1010, "Category not existed", HttpStatus.NOT_FOUND),
    PRODUCT_EXISTED(1011, "Product existed", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_EXISTED(1012, "Product not existed", HttpStatus.NOT_FOUND),
    PAYMENT_EXISTED(1013, "Payment existed", HttpStatus.BAD_REQUEST),
    PAYMENT_NOT_EXISTED(1014, "Payment not existed", HttpStatus.NOT_FOUND),
    ORDER_EXISTED(1015, "Order existed", HttpStatus.BAD_REQUEST),
    ORDER_NOT_EXISTED(1016, "Order not existed", HttpStatus.NOT_FOUND),
    ORDER_DETAIL_EXISTED(1017, "Order detail existed", HttpStatus.BAD_REQUEST),
    ORDER_DETAIL_NOT_EXISTED(1018, "Order detail not existed", HttpStatus.NOT_FOUND),
    INVALID_IMAGE(1019, "Image is empty", HttpStatus.BAD_REQUEST),
    IMAGE_SAVE_FAILED(1020, "Image save failed",HttpStatus.INTERNAL_SERVER_ERROR);


    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
