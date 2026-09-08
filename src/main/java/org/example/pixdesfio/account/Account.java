package org.example.pixdesfio.account;

import jakarta.persistence.*;
import lombok.*;
import org.example.pixdesfio.transfer.Transfer;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @Column(nullable = false, unique = true)
    private String id;

    /**
     * Amount represented in cents.
     */
    @Column(nullable = false)
    private BigInteger balance;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
