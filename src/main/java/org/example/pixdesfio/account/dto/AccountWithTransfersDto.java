package org.example.pixdesfio.account.dto;

import org.example.pixdesfio.account.Account;
import org.example.pixdesfio.transfer.Transfer;
import org.example.pixdesfio.transfer.dto.TransferDto;

import java.math.BigInteger;
import java.util.List;

public record AccountWithTransfersDto(
        String id,
        BigInteger balance,
        List<TransferDto> transfers
) {

    public static AccountWithTransfersDto fromEntity(Account account, List<Transfer> transfers) {
        return new AccountWithTransfersDto(
                account.getId(),
                account.getBalance(),
                transfers.stream().map(TransferDto::fromEntity).toList()
        );
    }
}
