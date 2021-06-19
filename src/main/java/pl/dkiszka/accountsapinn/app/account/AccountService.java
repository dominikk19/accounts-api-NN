package pl.dkiszka.accountsapinn.app.account;

import pl.dkiszka.accountsapinn.domain.account.Account;

import java.math.BigDecimal;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 19.06.2021
 */
public interface AccountService {

    Account openAccount(String firstname, String surname, BigDecimal openingBalance);
}
