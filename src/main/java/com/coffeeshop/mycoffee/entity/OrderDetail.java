package com.coffeeshop.mycoffee.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity // Escape the table name with backticks
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;

    @ManyToOne
    @JoinColumn(name = "produc_id")
    Product product;

    // Tự động cập nhật khi tạo bản ghi
    @CreatedDate
    @Column(updatable = false) // Không cho phép cập nhật sau khi tạo
            LocalDateTime createdAt;

    // Tự động cập nhật khi bản ghi thay đổi
    @LastModifiedDate
    LocalDateTime updatedAt;
}
