package pl.dkiszka.accountsapinn.app.account;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 19.06.2021
 */
public class AccountServiceException extends RuntimeException {
    AccountServiceException(String message) {
        super(message);
    }
}
