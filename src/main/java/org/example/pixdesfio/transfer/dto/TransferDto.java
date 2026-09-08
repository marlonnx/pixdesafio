package org.example.pixdesfio.transfer.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.pixdesfio.transfer.Transfer;
import org.example.pixdesfio.transfer.TransferStatus;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransferDto(
        @NotNull
        @NotBlank
        String id,
        @NotNull
        @NotBlank
        String payerId,
        @NotNull
        @NotBlank
        String payeeId,
        @NotNull
        @Min(value = 1)
        BigInteger amount,
        @NotNull
        @NotBlank
        String idempotencyKey,
        TransferStatus status,
        String failureReason,
        @NotNull
        LocalDateTime createdAt
) {

    public static TransferDto fromEntity(Transfer transfer) {
        return new TransferDto(
                transfer.getId(),
                transfer.getPayer().getId(),
                transfer.getPayee().getId(),
                transfer.getAmount(),
                transfer.getIdempotencyKey(),
                transfer.getStatus(),
                transfer.getFailureReason(),
                transfer.getCreatedAt()
        );
    }
}
