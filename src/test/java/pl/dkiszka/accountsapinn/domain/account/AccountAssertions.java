package pl.dkiszka.accountsapinn.domain.account;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 19.06.2021
 */
@RequiredArgsConstructor
public class AccountAssertions {

    public static AccountAssertions assertThat(Account actual) {
        return new AccountAssertions(actual);
    }

    private final Account actual;


    public AccountAssertions hasContainUuidCompatibleWithPattern() {
        Assertions.assertThat(actual)
                .extracting("uuid")
                .satisfies(uuid -> Assertions.assertThat(((UUID) uuid).toString()).matches(Pattern.compile("[0-9a-z\\-]{36}")));
        return this;
    }

    public AccountAssertions hasBalanceInPlnEqualsTo(BigDecimal expected) {
        Assertions.assertThat(actual)
                .extracting("balance")
                .satisfies(balance -> {
                    var actualMoney = (Money) balance;
                    Assertions.assertThat(actualMoney).hasFieldOrPropertyWithValue("balanceInPln", expected);
                });
        return this;
    }

    public AccountAssertions hasBalanceInUsdEqualsTo(BigDecimal expected) {
        Assertions.assertThat(actual)
                .extracting("balance")
                .satisfies(balance -> {
                    var actualMoney = (Money) balance;
                    Assertions.assertThat(actualMoney).hasFieldOrPropertyWithValue("balanceInUsd", expected);
                });
        return this;
    }

    public AccountAssertions hasFirstnameEqualsTo(String expected) {
        Assertions.assertThat(actual)
                .extracting("client")
                .satisfies(client -> {
                    var actualClient = (Client) client;
                    Assertions.assertThat(actualClient).hasFieldOrPropertyWithValue("firstname", expected);
                });
        return this;
    }

    public AccountAssertions hasSurnameEqualsTo(String expected) {
        Assertions.assertThat(actual)
                .extracting("client")
                .satisfies(client -> {
                    var actualClient = (Client) client;
                    Assertions.assertThat(actualClient).hasFieldOrPropertyWithValue("surname", expected);
                });
        return this;
    }

}
