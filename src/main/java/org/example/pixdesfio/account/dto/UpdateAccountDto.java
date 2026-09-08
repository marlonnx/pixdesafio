package org.example.pixdesfio.account.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;

public record UpdateAccountDto(
        @NotNull
        @NotBlank
        @Min(value = 0)
        BigInteger balance
) {
}
