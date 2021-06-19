package pl.dkiszka.accountsapinn.adapters.rest.api.account;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 19.06.2021
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode
class OpenAccountCommand {

    static final String FIRSTNAME_NOT_EMPTY = "Firstname can not be empty";
    static final String FIRSTNAME_MIN_CHAR = "Min of characters in firstname: 1";
    static final String SURNAME_NOT_EMPTY = "Surname can not be empty";
    static final String SURNAME_MIN_CHAR = "Min of characters in surname : 1";
    static final String OPENING_BALANCE_NOT_EMPTY = "Opening balance can not be empty";
    static final String OPENING_BALANCE_MIN_VALUE = "Opening balance must be at least 1";


    @NotNull(message = FIRSTNAME_NOT_EMPTY)
    @Size(min = 1, message = FIRSTNAME_MIN_CHAR)
    private String firstname;
    @NotNull(message = SURNAME_NOT_EMPTY)
    @Size(min = 1, message = SURNAME_MIN_CHAR)
    private String surname;
    @NotNull(message = OPENING_BALANCE_NOT_EMPTY)
    @Min(value = 1, message = OPENING_BALANCE_MIN_VALUE)
    private BigDecimal openingBalance;
}
