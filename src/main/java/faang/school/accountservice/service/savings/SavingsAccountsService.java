package faang.school.accountservice.service.savings;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.accountservice.config.context.UserContext;
import faang.school.accountservice.dto.OpenSavingsScoreDto;
import faang.school.accountservice.dto.tariff.TariffDto;
import faang.school.accountservice.model.Account;
import faang.school.accountservice.model.SavingsAccount;
import faang.school.accountservice.model.Tariff;
import faang.school.accountservice.repository.SavingsAccountRepository;
import faang.school.accountservice.repository.TariffRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SavingsAccountsService {
    private final ObjectMapper objectMapper;
    private final UserContext userContext;
    private final TariffRepository tariffRepository;
    private final SavingsAccountRepository savingsAccountRepository;

    public TariffDto getTariffScore(long scoreId) {
        long userId = userContext.getUserId();
        String json = savingsAccountRepository.tariffHistory(scoreId, userId).orElseThrow(() -> new EntityNotFoundException("account not found"));
        List<Long> tariffs = new ArrayList<>();
        try {
            tariffs = objectMapper.readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            log.info(e.toString());
        }
        Tariff tariff = tariffRepository.getReferenceById(tariffs.get(tariffs.size() - 1));
        return new TariffDto(tariff.getTypeTariff(), tariff.getBet(), tariff.getBettingHistory());
    }

    @Transactional
    public OpenSavingsScoreDto openScore() {
        long accountId = userContext.getUserId();
        Tariff tariff = tariffRepository.findByType("Basic");
        SavingsAccount savingsAccount = SavingsAccount.builder().number("00000000000000").account(Account.builder().id(accountId).build())
                .amount(new BigDecimal(0)).tariffHistory("[" + tariff.getId() + "]").build();

        SavingsAccount newSavings = savingsAccountRepository.save(savingsAccount);
        return OpenSavingsScoreDto.builder().number(newSavings.getNumber()).tariffHistory(newSavings.getTariffHistory())
                .amount(newSavings.getAmount()).tariffDto(new TariffDto(tariff.getTypeTariff(), tariff.getBet(), tariff.getBettingHistory())).build();
    }
}
