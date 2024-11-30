package com.coffeeshop.mycoffee.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "`order`") // Escape the table name with backticks
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class) // Lắng nghe sự kiện tạo và cập nhật
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "`table`") // Escape the column name using backticks
    int table;

    int used_point = 0;
    float total_price;
    String status = "NONE";

    @OneToOne
    @JoinColumn(name = "payment_id")
    Payment payment;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    List<OrderDetail> orderDetails;

    // Tự động cập nhật khi tạo bản ghi
    @CreatedDate
    @Column(updatable = false) // Không cho phép cập nhật sau khi tạo
    LocalDateTime createdAt;

    // Tự động cập nhật khi bản ghi thay đổi
    @LastModifiedDate
    LocalDateTime updatedAt;
}
