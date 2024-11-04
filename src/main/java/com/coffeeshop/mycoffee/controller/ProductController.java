package com.coffeeshop.mycoffee.controller;

import com.coffeeshop.mycoffee.dto.ApiResponse;
import com.coffeeshop.mycoffee.dto.categorydto.request.CategoryCreationRequest;
import com.coffeeshop.mycoffee.dto.categorydto.request.CategoryUpdateRequest;
import com.coffeeshop.mycoffee.dto.categorydto.response.CategoryResponse;
import com.coffeeshop.mycoffee.dto.productdto.request.ProductCreationRequest;
import com.coffeeshop.mycoffee.dto.productdto.request.ProductUpdateRequest;
import com.coffeeshop.mycoffee.dto.productdto.response.ProductResponse;
import com.coffeeshop.mycoffee.exception.AppException;
import com.coffeeshop.mycoffee.exception.ErrorCode;
import com.coffeeshop.mycoffee.service.CategoryService;
import com.coffeeshop.mycoffee.service.ProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProductController {

    ProductService productService;

    @PostMapping
    ApiResponse<ProductResponse> createProduct(@RequestBody @Valid ProductCreationRequest request) throws IOException {

        ProductResponse productResponse = productService.createProduct(request);

        return ApiResponse.<ProductResponse>builder()
                .result(productResponse)
                .build();
    }

    @GetMapping
    ApiResponse<List<ProductResponse>> getProducts(){
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getProducts())
                .build();
    }

    @PutMapping("/{productId}")
    ApiResponse<ProductResponse> updateProduct(@PathVariable("productId") String productId, @RequestBody @Valid ProductUpdateRequest request) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.updateProduct(productId, request))
                .build();
    }

    @PostMapping("/{productId}/image")
    ApiResponse<String> uploadProductImage(@PathVariable("productId") String productId, @RequestParam(value = "image", required = false) MultipartFile imageFile) {

        if (imageFile == null || imageFile.isEmpty()) {
            throw new AppException(ErrorCode.IMAGE_NOT_FOUND);
        }

        try {
            String imagePath = productService.saveProductImage(imageFile, productId);
            return ApiResponse.<String>builder()
                    .result(imagePath)
                    .build();
        } catch (IOException e) {
            log.error("Error saving product image", e);
            throw new AppException(ErrorCode.IMAGE_SAVE_FAILED);
        }
    }

    @DeleteMapping("/{productId}")
    ApiResponse<String> deleteProduct(@PathVariable("productId") String productId){

        productService.deleteProduct(productId);
        return ApiResponse.<String>builder()
                .result("Product has been deleted")
                .build();
    }
}
