package pl.dkiszka.accountsapinn.app.account;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dkiszka.accountsapinn.domain.account.Account;
import pl.dkiszka.accountsapinn.domain.account.AccountFactory;
import pl.dkiszka.accountsapinn.domain.account.AccountRepository;

import java.math.BigDecimal;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 19.06.2021
 */
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Transactional
class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public Account openAccount(String firstname, String surname, BigDecimal openingBalance) {
        var newClient = AccountFactory.openNewAccountForClient(firstname, surname, openingBalance);
        return accountRepository.save(newClient);
    }
}
