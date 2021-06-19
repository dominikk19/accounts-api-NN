package pl.dkiszka.accountsapinn.adapters.rest.api.nbp;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 19.06.2021
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@Getter
@ToString
class NbpExchangeRateResponse {
    private String code;
    private List<Rate> rates;

    BigDecimal getRate() {
        return rates.stream()
                .findFirst()
                .map(Rate::getMid)
                .map(BigDecimal::new)
                .get();
        //todo
    }

    @AllArgsConstructor(access = AccessLevel.PACKAGE)
    @NoArgsConstructor
    @Getter
    static class Rate {
        private String mid;
    }

}
