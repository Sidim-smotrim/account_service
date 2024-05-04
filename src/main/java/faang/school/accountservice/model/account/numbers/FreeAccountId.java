package faang.school.accountservice.model.account.numbers;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.data.relational.core.mapping.Embedded;

@Embeddable
public class FreeAccountId {

    @Column(name="type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AccountType type;

    @Column(name = "account_namber", nullable = false)
    private long accountNumber;
}
