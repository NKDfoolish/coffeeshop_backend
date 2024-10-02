package com.coffeeshop.mycoffee.repository;

import com.coffeeshop.mycoffee.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {}
