package faang.school.accountservice.controller;

import faang.school.accountservice.dto.AccountDto;
import faang.school.accountservice.dto.BalanceDto;
import faang.school.accountservice.dto.UpdateBalanceDto;
import faang.school.accountservice.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/balance")
public class BalanceController {
    private final BalanceService balanceService;

    @PostMapping("/new-account/{accountNumber}")
    public AccountDto createAccount(@PathVariable Long accountNumber) {
        return balanceService.createAccount(accountNumber);
    }

    @GetMapping("{balanceId}")
    public BalanceDto getBalance(@PathVariable Long balanceId) {
        return balanceService.getBalance(balanceId);
    }

    @PutMapping("{balanceId}")
    public BalanceDto updateBalance(@RequestBody UpdateBalanceDto updateBalanceDto, @PathVariable Long balanceId) {
        return balanceService.updateBalance(updateBalanceDto, balanceId);
    }
}
