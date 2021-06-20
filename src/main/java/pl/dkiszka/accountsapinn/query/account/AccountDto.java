package pl.dkiszka.accountsapinn.query.account;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 20.06.2021
 */
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
class AccountDto {

    static AccountDto create(UUID uuid, String actualCurrency, BigDecimal actualBalance, BigDecimal rate) {
        var actualCurrencyBalance = new CurrencyBalance(actualCurrency, actualBalance);
        var exchangedBalance = Optional.of(actualCurrency)
                .filter(val -> val.equals("PLN"))
                .map(ac -> CurrencyBalance.divide(actualBalance, rate))
                .orElseGet(() -> CurrencyBalance.multiple(actualBalance, rate));
        return new AccountDto(uuid, List.of(actualCurrencyBalance, exchangedBalance));
    }

    static AccountDto create(UUID uuid, String actualCurrency, BigDecimal actualBalance) {
        return new AccountDto(uuid, List.of(new CurrencyBalance(actualCurrency, actualBalance)));
    }

    private final UUID uuid;

    private final List<CurrencyBalance> currenciesBalance;

    @RequiredArgsConstructor
    @Getter
    @EqualsAndHashCode
    static class CurrencyBalance {

        static CurrencyBalance multiple(BigDecimal actualBalance, BigDecimal rate) {
            return new CurrencyBalance("PLN", actualBalance.multiply(rate, MathContext.DECIMAL32));
        }

        static CurrencyBalance divide(BigDecimal actualBalance, BigDecimal rate) {
            return new CurrencyBalance("USD", actualBalance.divide(rate, MathContext.DECIMAL32));
        }

        private final String currency;
        @JsonSerialize(using = MoneySerializer.class)
        private final BigDecimal balance;
    }


    private static class MoneySerializer extends JsonSerializer<BigDecimal> {
        @Override
        public void serialize(BigDecimal bigDecimal, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(bigDecimal.setScale(2, RoundingMode.HALF_UP).toString());
        }
    }
}
