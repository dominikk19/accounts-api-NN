package pl.dkiszka.accountsapinn.domain.account;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 19.06.2021
 */
@Entity
@Table(name = "Accounts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Account {

    @Id
    @EqualsAndHashCode.Include
    private UUID uuid;

    @Embedded
    private Money balance;

    private Client client;

    public UUID getUuid() {
        return UUID.fromString(uuid.toString());
    }

    public void exchangeBalance(ExchangeType exchangeType, BigDecimal rate){
        balance.convert(exchangeType,rate);
    }
}
