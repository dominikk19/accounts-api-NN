package pl.dkiszka.accountsapinn.app.account;

import pl.dkiszka.accountsapinn.domain.account.Account;
import pl.dkiszka.accountsapinn.domain.account.AccountRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 19.06.2021
 */
public class InMemoryDatabase implements AccountRepository {

    private ConcurrentHashMap<UUID, Account> memoryDb = new ConcurrentHashMap<>();

    @Override
    public Account save(Account account) {
        requireNonNull(account);
        memoryDb.put(account.getUuid(), account);
        return account;
    }

    @Override
    public Optional<Account> findByUuid(UUID uuid) {
        return Optional.of(uuid)
                .filter(memoryDb::containsKey)
                .map(memoryDb::get);
    }
}
