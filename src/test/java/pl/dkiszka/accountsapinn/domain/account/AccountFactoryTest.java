package pl.dkiszka.accountsapinn.domain.account;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 19.06.2021
 */
class AccountFactoryTest {

    @Test
    void when_open_new_account_for_client_then_should_be_return_account_with_balance_in_pln() {
        var actual = AccountFactory.openNewAccountForClient(TestData.FIRSTNAME, TestData.SURNAME, TestData.OPENING_BALANCE_TEN);

        AccountAssertions.assertThat(actual)
                .hasContainUuidCompatibleWithPattern()
                .hasBalanceEqualsTo(TestData.OPENING_BALANCE_TEN)
                .hasCurrencyEqualsTo(Currency.PLN)
                .hasFirstnameEqualsTo(TestData.FIRSTNAME)
                .hasSurnameEqualsTo(TestData.SURNAME);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.9999, 0.1, 0, -0.333, -1})
    void when_opening_balance_is_too_low_then_should_be_throw_AccountCreationException(double openingBalance) {
        Assertions.assertThatThrownBy(() -> AccountFactory.openNewAccountForClient(TestData.FIRSTNAME, TestData.SURNAME,
                BigDecimal.valueOf(openingBalance)))
                .isInstanceOf(AccountCreationException.class);

    }


}
