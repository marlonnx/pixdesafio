package org.example.pixdesfio.transfer;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransferRepository extends JpaRepository<Transfer, String> {
    List<Transfer> findByStatusOrderByCreatedAtAsc(TransferStatus status, Pageable pageable);
    Optional<Transfer> findByIdempotencyKey(String key);
}