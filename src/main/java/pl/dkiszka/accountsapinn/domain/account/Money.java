package pl.dkiszka.accountsapinn.domain.account;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Predicate;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 19.06.2021
 */

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class Money {

    @Transient
    private final Map<ExchangeType, BiFunction<BigDecimal, BigDecimal, Money>> converter = Map.of(
            ExchangeType.TO_USD, this::convertToUsd,
            ExchangeType.TO_PLN, this::convertToPln
    );

    @Transient
    private final Predicate<ExchangeType> isToPln = exchangeType -> exchangeType.equals(ExchangeType.TO_PLN);

    static Money create(BigDecimal openingBalance) {
        return new Money(openingBalance, BigDecimal.ZERO);
    }

    @Column(nullable = false)
    private BigDecimal balanceInPln;
    @Column(nullable = false)
    private BigDecimal balanceInUsd;

    void convert(ExchangeType exchangeType, BigDecimal amount, BigDecimal rate) {
        Optional.ofNullable(exchangeType)
                .filter(converter::containsKey)
                .ifPresent(type -> converter.get(type).apply(amount, rate));
    }

    void convert(ExchangeType exchangeType, BigDecimal rate) {
        Optional.of(exchangeType)
                .filter(isToPln)
                .ifPresentOrElse(e -> convert(e, balanceInUsd, rate),
                        () -> convert(exchangeType, balanceInPln, rate));
    }

    private Money convertToPln(BigDecimal amount, BigDecimal rate) {
        if (this.balanceInUsd.compareTo(amount) >= 0) {
            this.balanceInUsd = this.balanceInUsd.subtract(amount);
            this.balanceInPln = this.balanceInPln.add(amount.multiply(rate, MathContext.DECIMAL32));
            return this;
        } else {
            throw new AccountExchangeAmountException("Amount in PLN on account is too low");
        }
    }

    private Money convertToUsd(BigDecimal amount, BigDecimal rate) {
        if (this.balanceInPln.compareTo(amount) >= 0) {
            this.balanceInPln = this.balanceInPln.subtract(amount);
            this.balanceInUsd = this.balanceInUsd.add(amount.divide(rate, MathContext.DECIMAL32));
            return this;
        } else {
            throw new AccountExchangeAmountException("Amount in USD on account is too low");
        }
    }

}
