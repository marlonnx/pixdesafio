package org.example.pixdesfio.transfer;

import jakarta.persistence.*;
import lombok.*;
import org.example.pixdesfio.account.Account;
import org.example.pixdesfio.transfer.dto.TransferDto;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "payer_id", nullable = false)
    private Account payer;

    @ManyToOne
    @JoinColumn(name = "payee_id", nullable = false)
    private Account payee;

    @Column(nullable = false)
    private BigInteger amount;

    @Column(unique = true)
    private String idempotencyKey;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    TransferStatus status;

    @Column
    private String failureReason;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;


}
