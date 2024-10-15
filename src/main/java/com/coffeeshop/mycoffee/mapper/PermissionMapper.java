package com.coffeeshop.mycoffee.mapper;

import com.coffeeshop.mycoffee.dto.userdto.request.PermissionRequest;
import com.coffeeshop.mycoffee.dto.userdto.response.PermissionResponse;
import com.coffeeshop.mycoffee.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
