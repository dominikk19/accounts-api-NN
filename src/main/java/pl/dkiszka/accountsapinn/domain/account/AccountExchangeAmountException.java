package pl.dkiszka.accountsapinn.domain.account;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 21.06.2021
 */
public class AccountExchangeAmountException extends RuntimeException {
    public AccountExchangeAmountException(String message) {
        super(message);
    }
}
