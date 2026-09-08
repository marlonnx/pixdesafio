package org.example.pixdesfio.account.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.pixdesfio.account.Account;
import java.math.BigInteger;

public record AccountDto(
        @NotNull
        @NotBlank
        String id,
        @NotNull
        @Min(value = 0)
        BigInteger balance
) {

    public static AccountDto fromEntity(Account account) {
        return new AccountDto(
                account.getId(),
                account.getBalance()
        );
    }
}
