package com.coffeeshop.mycoffee.dto.categorydto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryResponse {

    String id;
    String name;
    String createdAt;
    String updatedAt;
    String deletedAt;
}
