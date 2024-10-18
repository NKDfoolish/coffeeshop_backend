package com.coffeeshop.mycoffee.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class) // Lắng nghe sự kiện tạo và cập nhật
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String type;
    String status;

    @OneToOne(
            // indicates that this is the child side of a
            // relationship and refers to the field in the Driver
            // class that defines the relationship there
            mappedBy = "payment"
    )
    Order order;

    // Tự động cập nhật khi tạo bản ghi
    @CreatedDate
    @Column(updatable = false) // Không cho phép cập nhật sau khi tạo
            LocalDateTime createdAt;
}
