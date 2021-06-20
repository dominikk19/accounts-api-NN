package pl.dkiszka.accountsapinn.query.account;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import pl.dkiszka.accountsapinn.query.account.AccountDto.CurrencyBalance;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 20.06.2021
 */
class AccountDtoTest {

    private final UUID ACCOUNT_UUID = UUID.randomUUID();
    private final String CURRENCY_PLN = "PLN";
    private final String CURRENCY_USD = "USD";
    private final BigDecimal BALANCE = BigDecimal.TEN;
    private final BigDecimal RATE = BigDecimal.valueOf(2);


    @Test
    void given_exchange_rate_when_create_accountDto_then_should_be_create_dto_with_multi_currency() {
        var expectedCurrenciesBalance = List.of(new CurrencyBalance(CURRENCY_PLN, BALANCE), new CurrencyBalance(CURRENCY_USD, BigDecimal.valueOf(5)));
        var actual = AccountDto.create(ACCOUNT_UUID, CURRENCY_PLN, BALANCE, RATE);

        AccountDtoAssertions.assertThat(actual)
                .hasUuidEqualsTo(ACCOUNT_UUID)
                .hasContainsAll(expectedCurrenciesBalance);
    }

    @Test
    void when_create_accountDto_without_exchange_rate_then_should_be_create_dto_with_single_currency() {
        var expectedCurrenciesBalance = List.of(new CurrencyBalance(CURRENCY_PLN, BALANCE));
        var actual = AccountDto.create(ACCOUNT_UUID, CURRENCY_PLN, BALANCE);

        AccountDtoAssertions.assertThat(actual)
                .hasUuidEqualsTo(ACCOUNT_UUID)
                .hasContainsAll(expectedCurrenciesBalance);
    }

    @Test
    void when_multiple_balance_then_should_be_create_CurrencyBalance_in_pln() {
        var actual = CurrencyBalance.multiple(BALANCE, RATE);
        var conditions = Assertions.allOf(
                new Condition<AccountDto.CurrencyBalance>(a -> a.getBalance().equals(BigDecimal.valueOf(20)), "balance 20"),
                new Condition<AccountDto.CurrencyBalance>(a -> a.getCurrency().equals(CURRENCY_PLN), "currency pln")
        );
        Assertions.assertThat(actual).is(conditions);
    }

    @Test
    void when_divide_balance_then_should_be_create_CurrencyBalance_in_usd() {
        var actual = CurrencyBalance.divide(BALANCE, RATE);
        var conditions = Assertions.allOf(
                new Condition<AccountDto.CurrencyBalance>(a -> a.getBalance().equals(BigDecimal.valueOf(5)), "balance 5"),
                new Condition<AccountDto.CurrencyBalance>(a -> a.getCurrency().equals(CURRENCY_USD), "currency usd")
        );
        Assertions.assertThat(actual).is(conditions);
    }

    @Test
    void given_two_accountDto_when_equals_then_should_be_return_true() {
        var firstDto = AccountDto.create(ACCOUNT_UUID, CURRENCY_PLN, BALANCE, RATE);
        var secondDto = AccountDto.create(ACCOUNT_UUID, CURRENCY_PLN, BALANCE, RATE);
        Assertions.assertThat(firstDto).isEqualTo(secondDto);
    }

    @Test
    void given_two_currency_nalance_when_equals_then_should_be_return_true() {
        var firstDto = new CurrencyBalance(CURRENCY_PLN, BALANCE);
        var secondDto = new CurrencyBalance(CURRENCY_PLN, BALANCE);
        Assertions.assertThat(firstDto).isEqualTo(secondDto);
    }

}
