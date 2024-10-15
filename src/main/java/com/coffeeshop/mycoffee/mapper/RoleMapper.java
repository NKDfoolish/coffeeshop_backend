package com.coffeeshop.mycoffee.mapper;

import com.coffeeshop.mycoffee.dto.userdto.request.RoleRequest;
import com.coffeeshop.mycoffee.dto.userdto.response.RoleResponse;
import com.coffeeshop.mycoffee.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
