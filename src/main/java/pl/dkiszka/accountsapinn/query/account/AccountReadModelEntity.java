package pl.dkiszka.accountsapinn.query.account;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
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

    private String firstname;

    private String surname;

    private BigDecimal balanceInPln;

    private BigDecimal balanceInUsd;

    AccountDto toAccountDto() {
        return AccountDto.create(uuid, firstname, surname, balanceInPln, balanceInUsd);
    }
}
