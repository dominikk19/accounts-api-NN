package pl.dkiszka.accountsapinn.query.account;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 20.06.2021
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
class AccountDtoAssertions {

    static AccountDtoAssertions assertThat(AccountDto actual) {
        return new AccountDtoAssertions(actual);
    }

    private final AccountDto actual;

    public AccountDtoAssertions hasUuidEqualsTo(UUID expected) {
        Assertions.assertThat(actual.getUuid()).isEqualTo(expected);
        return this;
    }

    public AccountDtoAssertions hasBalanceInPlnEqualsTo(BigDecimal expected) {
        Assertions.assertThat(actual.getBalanceInPln()).isEqualTo(expected);
        return this;
    }

    public AccountDtoAssertions hasBalanceInUsdEqualsTo(BigDecimal expected) {
        Assertions.assertThat(actual.getBalanceInUsd()).isEqualTo(expected);
        return this;
    }

}
