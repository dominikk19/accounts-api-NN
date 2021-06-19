package pl.dkiszka.accountsapinn.domain.account;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 19.06.2021
 */
class AccountTest {

    @Test
    void given_account_in_pln_when_exchange_balance_then_account_should_be_convert_to_usd(){
        var actualAccount = AccountFactory.openNewAccountForClient(TestData.FIRSTNAME, TestData.SURNAME, TestData.OPENING_BALANCE_TEN);
        actualAccount.exchangeBalance(ExchangeType.TO_USD, BigDecimal.valueOf(5));

        AccountAssertions.assertThat(actualAccount)
                .hasCurrencyEqualsTo(Currency.USD)
                .hasBalanceEqualsTo(BigDecimal.valueOf(2));
    }

    @Test
    void given_account_in_usd_when_exchange_balance_then_account_should_be_convert_to_pln(){
        var actualAccount = AccountFactory.openNewAccountForClient(TestData.FIRSTNAME, TestData.SURNAME, TestData.OPENING_BALANCE_TEN);
        actualAccount.exchangeBalance(ExchangeType.TO_USD, BigDecimal.valueOf(5));
        actualAccount.exchangeBalance(ExchangeType.TO_PLN, BigDecimal.valueOf(3));

        AccountAssertions.assertThat(actualAccount)
                .hasCurrencyEqualsTo(Currency.PLN)
                .hasBalanceEqualsTo(BigDecimal.valueOf(6));
    }
}
