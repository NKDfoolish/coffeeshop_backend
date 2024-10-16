package com.coffeeshop.mycoffee.dto.productdto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductCreationRequest {

    @NotBlank(message = "Product name is required")
    String name;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    float price;

    @NotBlank(message = "Category ID is required")
    String categoryId;
}
