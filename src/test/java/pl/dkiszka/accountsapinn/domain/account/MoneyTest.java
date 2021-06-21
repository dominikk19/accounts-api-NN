package pl.dkiszka.accountsapinn.domain.account;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 21.06.2021
 */
class MoneyTest {

    @Test
    void given_money_with_pln_when_convert_by_amount_then_should_be_changed_balances() {
        var money = Money.create(TestData.OPENING_BALANCE_TEN);
        money.convert(ExchangeType.TO_USD, BigDecimal.valueOf(4), BigDecimal.valueOf(2));

        Assertions.assertThat(money)
                .hasFieldOrPropertyWithValue("balanceInPln", BigDecimal.valueOf(6))
                .hasFieldOrPropertyWithValue("balanceInUsd", BigDecimal.valueOf(2));
    }

    @Test
    void given_money_with_pln_when_convert_all_balance_then_should_be_changed_balances() {
        var money = Money.create(TestData.OPENING_BALANCE_TEN);
        money.convert(ExchangeType.TO_USD, BigDecimal.valueOf(2));

        Assertions.assertThat(money)
                .hasFieldOrPropertyWithValue("balanceInPln", BigDecimal.ZERO)
                .hasFieldOrPropertyWithValue("balanceInUsd", BigDecimal.valueOf(5));
    }

    @Test
    void given_money_with_pln_when_convert_too_highest_amount_then_should_be_throw_AccountExchangeAmountException() {
        var money = Money.create(TestData.OPENING_BALANCE_TEN);
        Assertions.assertThatThrownBy(() -> money.convert(ExchangeType.TO_USD, BigDecimal.valueOf(22), BigDecimal.valueOf(2)))
                .isInstanceOf(AccountExchangeAmountException.class);
    }

}
