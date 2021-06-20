package pl.dkiszka.accountsapinn.adapters.rest.api.nbp;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import pl.dkiszka.accountsapinn.app.nbp.NbpService;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 20.06.2021
 */
@Tag("SystemTest")
@RestClientTest(NbpServiceRestTemplateApi.class)
@Import(RestTemplateConfiguration.class)
class NbpServiceRestTemplateApiSystemTestNegativeProcess {

    private static final String NBP_URL = "http://api.nbp.pl/api/exchangerates/rates/a/usd";


    @Autowired
    private NbpService nbpService;

    @Autowired
    private MockRestServiceServer mockRestServiceServer;


    @Test
    void when_connection_error_then_should_be_throw_NbpServiceException() {
        mockRestServiceServer.expect(requestTo(NBP_URL))
                .andRespond(MockRestResponseCreators.withServerError());

        Assertions.assertThatThrownBy(() -> nbpService.getUsdExchangeRate())
                .isInstanceOf(NbpServiceException.class);
    }

    @Test
    void when_send_bad_request_then_should_be_throw_NbpServiceException() {
        mockRestServiceServer.expect(requestTo(NBP_URL))
                .andRespond(MockRestResponseCreators.withBadRequest());

        Assertions.assertThatThrownBy(() -> nbpService.getUsdExchangeRate())
                .isInstanceOf(NbpServiceException.class);
    }


}
