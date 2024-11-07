package com.coffeeshop.mycoffee.dto.userdto.request;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationByPhoneRequest {

    @NotNull
    String phone;

}
