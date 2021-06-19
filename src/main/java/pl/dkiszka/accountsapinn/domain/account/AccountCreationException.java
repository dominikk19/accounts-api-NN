package pl.dkiszka.accountsapinn.domain.account;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 19.06.2021
 */
public class AccountCreationException extends RuntimeException {
    public AccountCreationException() {
        super(String.format("Opening balance is to low"));
    }
}
