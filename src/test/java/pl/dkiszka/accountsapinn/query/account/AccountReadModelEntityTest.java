package pl.dkiszka.accountsapinn.query.account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 20.06.2021
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(H2DatabaseQueryTestConfiguration.class)
class AccountReadModelEntityTest {

    private static final UUID ACCOUNT_UUID = UUID.fromString("e25750a0-32fc-4c4b-a105-9e8cd1dbbef2");

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void given_account_and_rate_when_convert_dto_then_should_be_created_account_dto_with_multi_currency() {
        var expectedCurrenciesBalance = List.of(new AccountDto.CurrencyBalance("PLN", new BigDecimal("100.00")), new AccountDto.CurrencyBalance("USD", new BigDecimal("25.00")));
        var account = entityManager.find(AccountReadModelEntity.class, ACCOUNT_UUID);
        var actual = account.toAccountDtoWithMultiCurrency(BigDecimal.valueOf(4));

        AccountDtoAssertions.assertThat(actual)
                .hasUuidEqualsTo(ACCOUNT_UUID)
                .hasContainsAll(expectedCurrenciesBalance);
    }

    @Test
    void given_account_when_convert_dto_then_should_be_created_account_dto_with_simple_currency() {
        var expectedCurrenciesBalance = List.of(new AccountDto.CurrencyBalance("PLN", new BigDecimal("100.00")));
        var account = entityManager.find(AccountReadModelEntity.class, ACCOUNT_UUID);
        var actual = account.toAccountDtoWithSingleCurrency();

        AccountDtoAssertions.assertThat(actual)
                .hasUuidEqualsTo(ACCOUNT_UUID)
                .hasContainsAll(expectedCurrenciesBalance);
    }

}
