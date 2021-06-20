package pl.dkiszka.accountsapinn.adapters.rest.api.nbp;

import io.vavr.control.Either;

import java.math.BigDecimal;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 20.06.2021
 */
public interface NbpFacade {
    Either<String, BigDecimal> getExchangeRate();
}
