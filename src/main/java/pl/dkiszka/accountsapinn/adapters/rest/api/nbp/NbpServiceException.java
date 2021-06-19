package pl.dkiszka.accountsapinn.adapters.rest.api.nbp;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 19.06.2021
 */
public class NbpServiceException extends RuntimeException {
    NbpServiceException(String message) {
        super(message);
    }
}

