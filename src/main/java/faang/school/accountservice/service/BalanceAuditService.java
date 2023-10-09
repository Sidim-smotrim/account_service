package faang.school.accountservice.service;

import faang.school.accountservice.model.Account;
import faang.school.accountservice.model.Balance;
import faang.school.accountservice.model.BalanceAudit;
import faang.school.accountservice.repository.BalanceAuditRepository;
import faang.school.accountservice.repository.BalanceRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BalanceAuditService {
    private final BalanceAuditRepository balanceAuditRepository;
    private final BalanceRepository balanceRepository;

    @Transactional
    public void createBalanceAudit(Account account) {
        Balance balance = balanceRepository.findBalanceByAccountId(account.getId())
                .orElseThrow(() -> new EntityNotFoundException("Not found balance from account id " + account.getId()));
        BalanceAudit balanceAudit = new BalanceAudit();
        balanceAudit.setAccount(account);
        balanceAudit.setVersion(0);
        balanceAudit.setAuthorizationAmount(balance.getCurrentAuthorizationBalance());
        balanceAudit.setActualAmount(balance.getCurrentActualBalance());
        balanceAudit.setOperationId(1L);
        balanceAudit.setAuditTimestamp(LocalDateTime.now());
        balanceAuditRepository.save(balanceAudit);
        log.info("Balance audit was created successfully by balance id: {}", balance.getId());
    }

    private List<BalanceAudit> getBalanceAudits(Long balanceId) {
        List<BalanceAudit> balanceAudits = balanceAuditRepository.findAllByBalanceId(balanceId);
        return balanceAudits
    }
}