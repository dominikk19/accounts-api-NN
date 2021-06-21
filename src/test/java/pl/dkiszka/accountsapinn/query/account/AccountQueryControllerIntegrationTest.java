package pl.dkiszka.accountsapinn.query.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 20.06.2021
 */

@Tag("IntegrationTest")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {H2DatabaseQueryTestConfiguration.class})
@AutoConfigureMockMvc
class AccountQueryControllerIntegrationTest {

    private static final String ACCOUNT_PATH = "/api/v1/accounts/";


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void when_get_for_uuid_then_should_be_return_account_with_real_exchange_rate() throws Exception {
        var accountUuid = "e25750a0-32fc-4c4b-a105-9e8cd1dbbef2";
        var response = requestGet(ACCOUNT_PATH + accountUuid, status().isOk())
                .andReturn()
                .getResponse();
        var actual = objectMapper.readValue(response.getContentAsString(), AccountDto.class);

        AccountDtoAssertions.assertThat(actual)
                .hasUuidEqualsTo(UUID.fromString(accountUuid))
                .hasBalanceInPlnEqualsTo(new BigDecimal("100"))
                .hasBalanceInUsdEqualsTo(new BigDecimal("0"));
    }


    private ResultActions requestGet(String url, ResultMatcher resultMatcher) throws Exception {
        return this.mockMvc.perform(
                get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(resultMatcher);
    }
}
