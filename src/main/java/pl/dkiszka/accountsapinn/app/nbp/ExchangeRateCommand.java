package pl.dkiszka.accountsapinn.app.nbp;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.dkiszka.accountsapinn.domain.DomainEvent;
import pl.dkiszka.accountsapinn.domain.account.ExchangeType;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 19.06.2021
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ExchangeRateCommand implements DomainEvent {

    public static ExchangeRateCommand create(UUID accountUuid, ExchangeType exchangeType) {
        return new ExchangeRateCommand(UUID.randomUUID(), LocalDateTime.now(), accountUuid, exchangeType);
    }

    private final UUID eventUuid;
    private final LocalDateTime eventCreationDateTime;
    private final UUID accountUuid;
    private final ExchangeType exchangeType;
}
