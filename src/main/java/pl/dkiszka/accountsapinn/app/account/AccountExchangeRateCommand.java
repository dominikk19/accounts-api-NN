package pl.dkiszka.accountsapinn.app.account;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.dkiszka.accountsapinn.app.nbp.ExchangeRateCommand;
import pl.dkiszka.accountsapinn.domain.DomainEvent;
import pl.dkiszka.accountsapinn.domain.account.ExchangeType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 19.06.2021
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AccountExchangeRateCommand implements DomainEvent {
    public static AccountExchangeRateCommand create(ExchangeRateCommand exchangeRateCommand, BigDecimal exchangeRate) {
        return new AccountExchangeRateCommand(UUID.randomUUID(), LocalDateTime.now(), exchangeRateCommand.getAccountUuid(),
                exchangeRateCommand.getExchangeType(), exchangeRate);
    }

    private final UUID eventUuid;
    private final LocalDateTime eventCreationDateTime;
    private final UUID accountUuid;
    private final ExchangeType exchangeType;
    private final BigDecimal exchangeRate;

}
