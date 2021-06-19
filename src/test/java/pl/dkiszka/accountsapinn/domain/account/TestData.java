package pl.dkiszka.accountsapinn.domain.account;

import java.math.BigDecimal;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 19.06.2021
 */
public interface TestData {
    String FIRSTNAME = "Janek";
    String SURNAME = "Kowal";
    BigDecimal OPENING_BALANCE_TEN = BigDecimal.TEN;
    Account account = AccountFactory.openNewAccountForClient(FIRSTNAME, SURNAME, OPENING_BALANCE_TEN);

}
