package pl.dkiszka.accountsapinn.query.account;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 20.06.2021
 */
class AccountDtoTest {

    private final UUID ACCOUNT_UUID = UUID.randomUUID();
    private final String FIRSTNAME = "Jan";
    private final String SURNAME = "Nowak";
    private final BigDecimal BALANCE_IN_PLN = BigDecimal.TEN;
    private final BigDecimal BALANCE_IN_USD = new BigDecimal("15");

    @Test
    void when_create_accountDto_then_should_be_create_dto_with_multi_currency() {
        var actual = AccountDto.create(ACCOUNT_UUID, FIRSTNAME, SURNAME, BALANCE_IN_PLN, BALANCE_IN_USD);

        AccountDtoAssertions.assertThat(actual)
                .hasUuidEqualsTo(ACCOUNT_UUID)
                .hasBalanceInPlnEqualsTo(BALANCE_IN_PLN)
                .hasBalanceInUsdEqualsTo(BALANCE_IN_USD);
    }
}
