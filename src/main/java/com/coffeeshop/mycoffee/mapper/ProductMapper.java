package com.coffeeshop.mycoffee.mapper;

import com.coffeeshop.mycoffee.dto.productdto.request.ProductCreationRequest;
import com.coffeeshop.mycoffee.dto.productdto.request.ProductUpdateRequest;
import com.coffeeshop.mycoffee.dto.productdto.response.ProductResponse;
import com.coffeeshop.mycoffee.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toProduct(ProductCreationRequest request);
    ProductResponse toProductResponse(Product product);

    @Mapping(target = "category", ignore = true)
    void updateProduct(@MappingTarget Product product, ProductUpdateRequest request);
}
