package pl.dkiszka.accountsapinn.adapters.persistence.jpa.account;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.dkiszka.accountsapinn.domain.account.Account;
import pl.dkiszka.accountsapinn.domain.account.AccountRepository;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 19.06.2021
 */
@Repository
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class JpaAccountRepository implements AccountRepository {

    private final SpringJpaAccountRepository repository;

    @Override
    public Account save(Account account) {
        return repository.save(account);
    }
}
