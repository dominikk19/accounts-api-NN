package pl.dkiszka.accountsapinn.domain.account;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 19.06.2021
 */
class AccountTest {

    @Test
    void given_account_in_pln_when_exchange_balance_then_account_should_be_convert_to_usd() {
        var actualAccount = AccountFactory.openNewAccountForClient(TestData.FIRSTNAME, TestData.SURNAME, TestData.OPENING_BALANCE_TEN);
        actualAccount.exchangeAmount(ExchangeType.TO_USD, BigDecimal.valueOf(6), BigDecimal.valueOf(3));

        AccountAssertions.assertThat(actualAccount)
                .hasBalanceInPlnEqualsTo(BigDecimal.valueOf(4))
                .hasBalanceInUsdEqualsTo(BigDecimal.valueOf(2));
    }

    @Test
    void given_account_in_usd_when_exchange_balance_then_account_should_be_convert_to_pln() {
        var actualAccount = AccountFactory.openNewAccountForClient(TestData.FIRSTNAME, TestData.SURNAME, TestData.OPENING_BALANCE_TEN);
        actualAccount.exchangeAmount(ExchangeType.TO_USD, BigDecimal.valueOf(10), BigDecimal.valueOf(1));
        actualAccount.exchangeAmount(ExchangeType.TO_PLN, BigDecimal.valueOf(5), BigDecimal.valueOf(3));

        AccountAssertions.assertThat(actualAccount)
                .hasBalanceInPlnEqualsTo(BigDecimal.valueOf(15))
                .hasBalanceInUsdEqualsTo(BigDecimal.valueOf(5));
    }

    @Test
    void given_zero_on_account_in_usd_when_exchange_amount_to_pln_then_should_be_throw_AccountExchangeAmountException() {
        var actualAccount = AccountFactory.openNewAccountForClient(TestData.FIRSTNAME, TestData.SURNAME, TestData.OPENING_BALANCE_TEN);

        Assertions.assertThatThrownBy(() -> actualAccount.exchangeAmount(ExchangeType.TO_PLN, BigDecimal.valueOf(11), BigDecimal.valueOf(1)))
                .isInstanceOf(AccountExchangeAmountException.class);
    }

    @Test
    void given_one_on_account_in_pln_when_exchange_amount_to_usd_then_should_be_throw_AccountExchangeAmountException() {
        var actualAccount = AccountFactory.openNewAccountForClient(TestData.FIRSTNAME, TestData.SURNAME, TestData.OPENING_BALANCE_TEN);

        Assertions.assertThatThrownBy(() -> actualAccount.exchangeAmount(ExchangeType.TO_USD, BigDecimal.valueOf(11), BigDecimal.valueOf(1)))
                .isInstanceOf(AccountExchangeAmountException.class);
    }
}
