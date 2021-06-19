package pl.dkiszka.accountsapinn.domain.account;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 19.06.2021
 */
public interface AccountRepository {
    Account save(Account account);
    Optional<Account> findByUuid(UUID uuid);
}
