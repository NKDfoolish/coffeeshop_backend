package com.coffeeshop.mycoffee.service;

import com.coffeeshop.mycoffee.dto.categorydto.request.CategoryCreationRequest;
import com.coffeeshop.mycoffee.dto.categorydto.request.CategoryUpdateRequest;
import com.coffeeshop.mycoffee.dto.categorydto.response.CategoryResponse;
import com.coffeeshop.mycoffee.entity.Category;
import com.coffeeshop.mycoffee.exception.AppException;
import com.coffeeshop.mycoffee.exception.ErrorCode;
import com.coffeeshop.mycoffee.mapper.CategoryMapper;
import com.coffeeshop.mycoffee.repository.CategoryRepository;
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
public class CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponse createCategory(CategoryCreationRequest request){

        if (categoryRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }

        Category category = categoryMapper.toCategory(request);

        try {
            category = categoryRepository.save(category);
        }catch (DataIntegrityViolationException e){
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }

        return categoryMapper.toCategoryResponse(category);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<CategoryResponse> getCategories(){
        return categoryRepository.findAll().stream().map(categoryMapper::toCategoryResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponse updateCategory(String categoryId, CategoryUpdateRequest request){
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));

        categoryMapper.updateCategory(category, request);

        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCategory(String categoryId){
        categoryRepository.deleteById(categoryId);
    }

}
