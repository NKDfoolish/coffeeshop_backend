package com.coffeeshop.mycoffee.repository;

import com.coffeeshop.mycoffee.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    boolean existsByName(String name);

    // Tìm tất cả các Category chưa bị xóa mềm
    @Query("SELECT c FROM Product c WHERE c.deletedAt IS NULL")
    List<Product> findAllActiveCategories();
}
