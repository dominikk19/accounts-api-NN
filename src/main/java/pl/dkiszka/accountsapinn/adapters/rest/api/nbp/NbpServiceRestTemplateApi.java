package pl.dkiszka.accountsapinn.adapters.rest.api.nbp;

import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.dkiszka.accountsapinn.app.nbp.NbpService;

import java.math.BigDecimal;
import java.net.URI;
import java.util.function.Predicate;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 19.06.2021
 */
@Service
@Slf4j
class NbpServiceRestTemplateApi implements NbpService {

    private static final Predicate<ResponseEntity<NbpExchangeRateResponse>> is200 = response -> response.getStatusCode().value() == 200;

    private final String nbpApiUrl;
    private final RestTemplate restTemplate;


    NbpServiceRestTemplateApi(@Value("${nbp-api.url}") String nbpApiUrl, RestTemplate restTemplate) {
        this.nbpApiUrl = nbpApiUrl;
        this.restTemplate = restTemplate;
    }

    @Override
    public BigDecimal getUsdExchangeRate() {
        return getResponse()
                .getOrElseThrow(exc -> {
                    var message = String.format("Status code %s message %2s", exc.getStatusCode(), exc.getStatusCode().getReasonPhrase());
                    log.warn(message);
                    return new NbpServiceException(message);
                })
                .getRate();
    }

    private Either<ResponseEntity<NbpExchangeRateResponse>, NbpExchangeRateResponse> getResponse() {
        var uri = URI.create(nbpApiUrl);
        return Try.of(() -> restTemplate.getForEntity(uri, NbpExchangeRateResponse.class))
                .map(this::extractBody)
                .getOrElseThrow(ex -> {
                    log.error("NbpServiceException : {}", ex.getMessage());
                    return new NbpServiceException(ex.getMessage());
                });
    }

    private Either<ResponseEntity<NbpExchangeRateResponse>, NbpExchangeRateResponse> extractBody(ResponseEntity<NbpExchangeRateResponse> responseEntity) {
        return Option.of(responseEntity)
                .filter(is200)
                .map(ResponseEntity::getBody)
                .toEither(responseEntity);
    }

}
