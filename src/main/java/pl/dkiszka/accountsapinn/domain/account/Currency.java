package pl.dkiszka.accountsapinn.domain.account;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.function.BiFunction;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 19.06.2021
 */
public enum Currency {
    PLN(ExchangeType.TO_USD, (pln, rate) -> pln.divide(rate, MathContext.DECIMAL32)),
    USD(ExchangeType.TO_PLN, (usd, rate) -> usd.multiply(rate,MathContext.DECIMAL32));

    ExchangeType convertDirection;
    BiFunction<BigDecimal, BigDecimal, BigDecimal> function;

    Currency(ExchangeType convertDirection, BiFunction<BigDecimal, BigDecimal, BigDecimal> function) {
        this.convertDirection = convertDirection;
        this.function = function;
    }

    Currency newCurrency() {
        return this == PLN ? USD : PLN;
    }

}
