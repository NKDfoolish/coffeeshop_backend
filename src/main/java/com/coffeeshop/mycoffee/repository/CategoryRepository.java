package com.coffeeshop.mycoffee.repository;

import com.coffeeshop.mycoffee.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    // Tìm tất cả các Category chưa bị xóa mềm
    @Query("SELECT c FROM Category c WHERE c.deletedAt IS NULL")
    List<Category> findAllActiveCategories();
}
