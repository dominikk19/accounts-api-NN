package pl.dkiszka.accountsapinn.adapters.rest.api.nbp;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pl.dkiszka.accountsapinn.app.nbp.NbpService;

import java.net.URI;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 19.06.2021
 */
@Tag("SystemTest")
class NbpServiceRestTemplateApiSystemTest {

    private static final String NBP_URL = "http://api.nbp.pl/api/exchangerates/rates/a/usd";
    private static final String EXPECTED_RATE = "3.8198";

    private NbpService nbpService;

    @Test
    void when_connection_ok_then_should_be_return_rate_3_8198() {
        var nbpResponse = NbpExchangeRateResponse.builder()
                .code("USD")
                .rates(Arrays.asList(new NbpExchangeRateResponse.Rate(EXPECTED_RATE)))
                .build();

        nbpService = new NbpServiceRestTemplateApi(NBP_URL, RestTemplateStub.of(ResponseEntity.ok(nbpResponse)));

        var actual = nbpService.getUsdExchangeRate();

        Assertions.assertThat(actual).isEqualTo(EXPECTED_RATE);
    }

    @ParameterizedTest
    @MethodSource("responseEntity")
    void when_rest_return_category_code_4xxClientError_then_should_be_throw_NbpServiceException(ResponseEntity responseEntity) {

        nbpService = new NbpServiceRestTemplateApi(NBP_URL, RestTemplateStub.of(responseEntity));

        Assertions.assertThatThrownBy(() -> nbpService.getUsdExchangeRate())
                .isInstanceOf(NbpServiceException.class);
    }

    private static Stream<Arguments> responseEntity() {
        return Stream.of(
                Arguments.of(ResponseEntity.notFound().build()),
                Arguments.of(ResponseEntity.badRequest().build())
        );
    }


    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class RestTemplateStub extends RestTemplate {

        static RestTemplateStub of(ResponseEntity responseEntity) {
            return new RestTemplateStub(responseEntity);
        }

        private final ResponseEntity responseEntity;

        @Override
        public <T> ResponseEntity<T> getForEntity(URI url, Class<T> responseType) throws RestClientException {
            return (ResponseEntity<T>) responseEntity;
        }
    }


}
