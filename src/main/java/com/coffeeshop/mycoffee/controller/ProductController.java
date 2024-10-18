package com.coffeeshop.mycoffee.controller;

import com.coffeeshop.mycoffee.dto.ApiResponse;
import com.coffeeshop.mycoffee.dto.categorydto.request.CategoryCreationRequest;
import com.coffeeshop.mycoffee.dto.categorydto.request.CategoryUpdateRequest;
import com.coffeeshop.mycoffee.dto.categorydto.response.CategoryResponse;
import com.coffeeshop.mycoffee.dto.productdto.request.ProductCreationRequest;
import com.coffeeshop.mycoffee.dto.productdto.request.ProductUpdateRequest;
import com.coffeeshop.mycoffee.dto.productdto.response.ProductResponse;
import com.coffeeshop.mycoffee.service.CategoryService;
import com.coffeeshop.mycoffee.service.ProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProductController {

    ProductService productService;

    @PostMapping
    ApiResponse<ProductResponse> createProduct(@RequestBody @Valid ProductCreationRequest request){
        return ApiResponse.<ProductResponse>builder()
                .result(productService.createProduct(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<ProductResponse>> getProducts(){
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getProducts())
                .build();
    }

    @PutMapping("/{productId}")
    ApiResponse<ProductResponse> updateProduct(@PathVariable("productId") String productId, @RequestBody @Valid ProductUpdateRequest request){
        return ApiResponse.<ProductResponse>builder()
                .result(productService.updateProduct(productId, request))
                .build();
    }

    @DeleteMapping("/{productId}")
    ApiResponse<String> deleteProduct(@PathVariable("productId") String productId){

        productService.deleteProduct(productId);
        return ApiResponse.<String>builder()
                .result("Product has been deleted")
                .build();
    }
}
