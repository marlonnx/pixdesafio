package org.example.pixdesfio.worker;

import lombok.AllArgsConstructor;
import org.example.pixdesfio.transfer.TransferService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class Worker {
    private TransferService transferService;

    @Scheduled(fixedDelay = 10000)
    public void processPendingTransfers() {
        System.out.println("processing transfers");
        transferService.processPendingTransfers();
    }
}
