package pl.dkiszka.accountsapinn.query.account;

import pl.dkiszka.accountsapinn.domain.account.Currency;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 20.06.2021
 */
@Entity
@Table(name = "Accounts")
class AccountReadModelEntity {

    @Id
    private UUID uuid;

    private String currency;

    private BigDecimal balance;

    AccountDto toAccountDtoWithMultiCurrency(BigDecimal rate) {
        return AccountDto.create(uuid, currency, balance, rate);
    }

    AccountDto toAccountDtoWithSingleCurrency() {
        return AccountDto.create(uuid, currency, balance);
    }
}
