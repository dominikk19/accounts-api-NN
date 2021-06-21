package pl.dkiszka.accountsapinn.adapters.rest.api.account;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.dkiszka.accountsapinn.domain.account.ExchangeType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 21.06.2021
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@Getter
class ExchangeAmountCommand {

    static final String AMOUNT_NOT_EMPTY = "Amount can not be null";
    static final String AMOUNT_MIN_VALUE = "Amount must be at least 1";

    @NotNull(message = "Exchange Type can not be null")
    private ExchangeType exchangeType;
    @NotNull(message = AMOUNT_NOT_EMPTY)
    @Min(value = 1, message = AMOUNT_MIN_VALUE)
    private BigDecimal amount;
}
