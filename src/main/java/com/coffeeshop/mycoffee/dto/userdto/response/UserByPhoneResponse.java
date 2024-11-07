package com.coffeeshop.mycoffee.dto.userdto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserByPhoneResponse {
    String id;
    String phone;
    Set<RoleResponse> roles;
}
