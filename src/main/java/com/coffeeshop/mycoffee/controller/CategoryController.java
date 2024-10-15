package com.coffeeshop.mycoffee.controller;

import com.coffeeshop.mycoffee.dto.ApiResponse;
import com.coffeeshop.mycoffee.dto.categorydto.request.CategoryCreationRequest;
import com.coffeeshop.mycoffee.dto.categorydto.request.CategoryUpdateRequest;
import com.coffeeshop.mycoffee.dto.categorydto.response.CategoryResponse;
import com.coffeeshop.mycoffee.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryController {
    CategoryService categoryService;

    @PostMapping
    ApiResponse<CategoryResponse> createCategory(@RequestBody @Valid CategoryCreationRequest request){
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.createCategory(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<CategoryResponse>> getCategories(){
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryService.getCategories())
                .build();
    }

    @PutMapping("/{categoryId}")
    ApiResponse<CategoryResponse> updateCategory(@PathVariable("categoryId") String categoryId, @RequestBody CategoryUpdateRequest request){
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.updateCategory(categoryId, request))
                .build();
    }

    @DeleteMapping("/{categoryId}")
    ApiResponse<String> deleteCategory(@PathVariable("categoryId") String categoryId){

        categoryService.deleteCategory(categoryId);
        return ApiResponse.<String>builder()
                .result("Category has been deleted")
                .build();
    }

}
