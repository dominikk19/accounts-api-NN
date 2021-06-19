package pl.dkiszka.accountsapinn.domain.account;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 19.06.2021
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountFactory {

    private static final Predicate<BigDecimal> isOpeningBalanceGreaterThanMin = val -> val.compareTo(BigDecimal.ONE) >= 0;

    public static Account openNewAccountForClient(String firstname, String surname, BigDecimal openingBalance) {
        return Optional.of(openingBalance)
                .filter(isOpeningBalanceGreaterThanMin)
                .map(balance -> new Account(UUID.randomUUID(), Money.create(balance), new Client(firstname, surname)))
                .orElseThrow((AccountCreationException::new));
    }

}
