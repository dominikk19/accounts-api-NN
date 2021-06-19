package pl.dkiszka.accountsapinn.adapters.persistence.jpa.account;

import org.springframework.data.repository.Repository;
import pl.dkiszka.accountsapinn.domain.account.Account;

import java.util.UUID;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 19.06.2021
 */
interface SpringJpaAccountRepository extends Repository<Account, UUID> {
    Account save(Account account);
}
