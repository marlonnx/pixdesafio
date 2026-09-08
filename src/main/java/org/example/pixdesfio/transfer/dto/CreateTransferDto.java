package org.example.pixdesfio.transfer.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigInteger;

public record CreateTransferDto(
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
        String idempotencyKey
) {
}
