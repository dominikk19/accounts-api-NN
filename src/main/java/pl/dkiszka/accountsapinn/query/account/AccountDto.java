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
import java.math.RoundingMode;
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

    static AccountDto create(UUID uuid, String firstname, String surname, BigDecimal balanceInPln, BigDecimal balanceInUsd) {
        return new AccountDto(uuid, firstname, surname, balanceInPln, balanceInUsd);
    }

    private final UUID uuid;
    private final String firstname;
    private final String surname;

    @JsonSerialize(using = MoneySerializer.class)
    private final BigDecimal balanceInPln;

    @JsonSerialize(using = MoneySerializer.class)
    private final BigDecimal balanceInUsd;


    private static class MoneySerializer extends JsonSerializer<BigDecimal> {
        @Override
        public void serialize(BigDecimal bigDecimal, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(bigDecimal.setScale(2, RoundingMode.HALF_UP).toString());
        }
    }
}
