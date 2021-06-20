package pl.dkiszka.accountsapinn.adapters.rest.api.nbp;

import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dkiszka.accountsapinn.app.nbp.NbpService;

import java.math.BigDecimal;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 20.06.2021
 */
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class NbpFacadeImpl implements NbpFacade {
    private final NbpService nbpService;

    @Override
    public Either<String, BigDecimal> getExchangeRate() {
        return Try.of(()->nbpService.getUsdExchangeRate())
                .toEither("Not Found");
    }
}
