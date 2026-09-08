package org.example.pixdesfio.account;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.pixdesfio.account.dto.AccountDto;
import org.example.pixdesfio.account.dto.AccountWithTransfersDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(path = "/accounts")
@AllArgsConstructor
public class AccountController {
    final private AccountService accountService;


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto creatAccount(@RequestBody @Valid AccountDto body) {
        return accountService.createAccount(body);
    }

    @GetMapping(path = "/{id}/statement")
    public AccountWithTransfersDto getAccountWithTransfers(@PathVariable(name = "id") String id) {
        return accountService.findWithTransfers(id);
    }

}
