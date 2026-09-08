package org.example.pixdesfio.transfer;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.pixdesfio.transfer.dto.CreateTransferDto;
import org.example.pixdesfio.transfer.dto.TransferDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/transfers")
@AllArgsConstructor
public class TransferController {
    private final TransferService transferService;


    @PostMapping
    public TransferDto createTransfer(@RequestBody @Valid CreateTransferDto dto) {
        return transferService.create(dto);
    }

    @GetMapping(path = "/{id}")
    public TransferDto findById(@PathVariable(name = "id") String id){
        return transferService.find(id);
    }

}
