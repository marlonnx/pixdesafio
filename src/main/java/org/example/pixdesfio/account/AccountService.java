package org.example.pixdesfio.account;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.pixdesfio.account.dto.AccountDto;
import org.example.pixdesfio.account.dto.AccountWithTransfersDto;
import org.example.pixdesfio.account.dto.UpdateAccountDto;
import org.example.pixdesfio.shared.exception.ConflictException;
import org.example.pixdesfio.transfer.Transfer;
import org.example.pixdesfio.transfer.TransferRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final TransferRepository transferRepository;

    public AccountDto createAccount(AccountDto dto) {
        if (accountRepository.existsById(dto.id())) {
            throw new ConflictException("Conta já existe");
        }
        Account account = Account.builder()
                .balance(dto.balance())
                .id(dto.id())
                .build();
        return AccountDto.fromEntity(accountRepository.save(account));
    }

    public Page<AccountDto> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Account::getCreateAt).ascending());
        return accountRepository.findAll(pageable).map(AccountDto::fromEntity);
    }

    public AccountDto find(String id) {
        return accountRepository.findByIdForUpdate(id).map(AccountDto::fromEntity).orElseThrow(EntityNotFoundException::new);
    }

    public AccountWithTransfersDto findWithTransfers(String id) {
        Account account = accountRepository.findByIdForUpdate(id).orElseThrow(EntityNotFoundException::new);
        List<Transfer> transfers = transferRepository.findByPayerIdOrPayeeId(id, id);
        return AccountWithTransfersDto.fromEntity(account,transfers);
    }

    public AccountDto update(String id, UpdateAccountDto dto) {
        Account account = accountRepository.findByIdForUpdate(id).orElseThrow(EntityNotFoundException::new);
        account.setBalance(dto.balance());
        return AccountDto.fromEntity(accountRepository.save(account));
    }

    public void delete(String id) {
        if (accountRepository.existsById(id)) {
            accountRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException();
        }
    }
}
