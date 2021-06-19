package pl.dkiszka.accountsapinn.app.account;

import pl.dkiszka.accountsapinn.domain.account.Account;
import pl.dkiszka.accountsapinn.domain.account.ExchangeType;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 19.06.2021
 */
public interface AccountService {

    Account openAccount(String firstname, String surname, BigDecimal openingBalance);

    String startBalanceExchange(UUID uuid, ExchangeType type);

    void balanceExchange(AccountExchangeRateCommand command);
}
