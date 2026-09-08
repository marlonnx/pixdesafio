package org.example.pixdesfio.transfer;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.pixdesfio.account.Account;
import org.example.pixdesfio.account.AccountRepository;
import org.example.pixdesfio.shared.exception.ValidationException;
import org.example.pixdesfio.transfer.dto.CreateTransferDto;
import org.example.pixdesfio.transfer.dto.TransferDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TransferService {
    final TransferRepository transferRepository;
    final AccountRepository accountRepository;


    public TransferDto create(CreateTransferDto dto) {
        Optional<Transfer> existingTransfer = transferRepository.findByIdempotencyKey(dto.idempotencyKey());
        if (existingTransfer.isPresent()) {
            return TransferDto.fromEntity(existingTransfer.get());
        }
        if (dto.payeeId().equals(dto.payerId())) {
            throw new ValidationException("Payer and Payee cant be equals");
        }
        Account payer = accountRepository.findByIdForUpdate(dto.payerId()).orElseThrow(EntityNotFoundException::new);
        Account payee = accountRepository.findByIdForUpdate(dto.payeeId()).orElseThrow(EntityNotFoundException::new);
        Transfer transfer = Transfer.builder()
                .payer(payer)
                .payee(payee)
                .amount(dto.amount())
                .idempotencyKey(dto.idempotencyKey())
                .status(TransferStatus.pending).build();
        return TransferDto.fromEntity(transferRepository.save(transfer));
    }

    @Transactional
    public void processTransfer(Transfer transfer) {
        if (!transferRepository.existsById(transfer.getId())) {
            throw new EntityNotFoundException();
        }
        if (transfer.getStatus() != TransferStatus.pending) {
            return;
        }
        Account payer = accountRepository.findByIdForUpdate(transfer.getPayer().getId()).orElseThrow(EntityNotFoundException::new);
        Account payee = accountRepository.findByIdForUpdate(transfer.getPayee().getId()).orElseThrow(EntityNotFoundException::new);
        BigInteger newAmount = payer.getBalance().subtract(transfer.getAmount());

        if (newAmount.signum() < 0) {
            transfer.setFailureReason("insufficient_funds");
            transfer.setStatus(TransferStatus.failed);
            transferRepository.save(transfer);
            return;
        }
        payer.setBalance(newAmount);
        payee.setBalance(payee.getBalance().add(transfer.getAmount()));
        transfer.setStatus(TransferStatus.completed);

        accountRepository.saveAll(List.of(payer, payee));
        transferRepository.save(transfer);
    }

    public void processPendingTransfers() {
        List<Transfer> pendingTransfers = transferRepository
                .findByStatusOrderByCreatedAtAsc(TransferStatus.pending, PageRequest.of(0, 50));
        for (Transfer transfer : pendingTransfers) {
            processTransfer(transfer);
        }
    }

    public Page<TransferDto> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").ascending());
        Page<Transfer> transfers = transferRepository.findAll(pageable);
        return transfers.map(TransferDto::fromEntity);
    }

    public TransferDto find(String id) {
        return transferRepository.findById(id).map(TransferDto::fromEntity).orElseThrow(EntityNotFoundException::new);
    }


}
