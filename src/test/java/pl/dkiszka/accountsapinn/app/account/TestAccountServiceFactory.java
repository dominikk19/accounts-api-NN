package pl.dkiszka.accountsapinn.app.account;

import pl.dkiszka.accountsapinn.domain.account.AccountRepository;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 19.06.2021
 */
public class TestAccountServiceFactory {

    public static AccountService creatAccountService(AccountRepository accountRepository) {
        return new AccountServiceImpl(accountRepository, null, null); //todo
    }

}
