package com.coffeeshop.mycoffee.mapper;

import com.coffeeshop.mycoffee.dto.userdto.request.UserByPhoneUpdateRequest;
import com.coffeeshop.mycoffee.dto.userdto.request.UserCreationByPhoneRequest;
import com.coffeeshop.mycoffee.dto.userdto.request.UserCreationRequest;
import com.coffeeshop.mycoffee.dto.userdto.request.UserUpdateRequest;
import com.coffeeshop.mycoffee.dto.userdto.response.UserByPhoneResponse;
import com.coffeeshop.mycoffee.dto.userdto.response.UserResponse;
import com.coffeeshop.mycoffee.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserByPhoneMapper {
    User toUser(UserCreationByPhoneRequest request);

    UserByPhoneResponse toUserByPhoneResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserByPhoneUpdateRequest request);
}
