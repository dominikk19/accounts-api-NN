package pl.dkiszka.accountsapinn.query.account;

import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 20.06.2021
 */
interface AccountQueryRepository extends Repository<AccountReadModelEntity, UUID> {
    Optional<AccountReadModelEntity> findByUuid(UUID uuid);
}
