package pl.dkiszka.accountsapinn.adapters.rest.api.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.util.Strings;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import pl.dkiszka.accountsapinn.adapters.rest.api.ExceptionMessage;
import pl.dkiszka.accountsapinn.domain.account.TestData;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 19.06.2021
 */
@Tag("SystemTest")
@ExtendWith(SpringExtension.class)
@Import(AccountServiceWithInMemoryDbTestConfiguration.class)
@WebMvcTest(AccountController.class)
class AccountControllerSystemTest {

    private static final String ACCOUNT_PATH = "/api/v1/accounts";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void when_post_command_then_should_be_return_created_status_and_location() throws Exception {
        var command = new OpenAccountCommand(TestData.FIRSTNAME, TestData.SURNAME, TestData.OPENING_BALANCE_TEN);
        var actualResponse = requestPost(ACCOUNT_PATH, command, status().isCreated())
                .andReturn()
                .getResponse();

        Assertions.assertThat(actualResponse.getHeaderNames()).contains("Location");
        Assertions.assertThat(actualResponse.getHeader("Location")).isNotEmpty();
    }

    @ParameterizedTest
    @MethodSource("openAccountCommandArguments")
    void when_post_command_with_not_correct_arguments_then_should_be_return_bad_request(String firstname, String surname, BigDecimal openingBalance, List<String> exceptionMessage) throws Exception {
        var command = new OpenAccountCommand(firstname, surname, openingBalance);
        var actualResponse = requestPost(ACCOUNT_PATH, command, status().isBadRequest());

        var actualMessage = objectMapper.readValue(actualResponse.andReturn().getResponse().getContentAsString(), ExceptionMessage.class);

        Assertions.assertThat(actualMessage.getMessages()).containsAll(exceptionMessage);
    }

    private static Stream<Arguments> openAccountCommandArguments() {
        return Stream.of(
                Arguments.of(TestData.FIRSTNAME, TestData.SURNAME, BigDecimal.ZERO, List.of(OpenAccountCommand.OPENING_BALANCE_MIN_VALUE)),
                Arguments.of(TestData.FIRSTNAME, TestData.SURNAME, BigDecimal.valueOf(0.1), List.of(OpenAccountCommand.OPENING_BALANCE_MIN_VALUE)),
                Arguments.of(TestData.FIRSTNAME, TestData.SURNAME, null, List.of(OpenAccountCommand.OPENING_BALANCE_NOT_EMPTY)),
                Arguments.of(TestData.FIRSTNAME, Strings.EMPTY, TestData.OPENING_BALANCE_TEN, List.of(OpenAccountCommand.SURNAME_MIN_CHAR)),
                Arguments.of(TestData.FIRSTNAME, null, TestData.OPENING_BALANCE_TEN, List.of(OpenAccountCommand.SURNAME_NOT_EMPTY)),
                Arguments.of(Strings.EMPTY, TestData.SURNAME, TestData.OPENING_BALANCE_TEN, List.of(OpenAccountCommand.FIRSTNAME_MIN_CHAR)),
                Arguments.of(null, TestData.SURNAME, TestData.OPENING_BALANCE_TEN, List.of(OpenAccountCommand.FIRSTNAME_NOT_EMPTY)),
                Arguments.of("", "", BigDecimal.ZERO, List.of(OpenAccountCommand.FIRSTNAME_MIN_CHAR, OpenAccountCommand.SURNAME_MIN_CHAR, OpenAccountCommand.OPENING_BALANCE_MIN_VALUE)),
                Arguments.of(null, null, null, List.of(OpenAccountCommand.FIRSTNAME_NOT_EMPTY, OpenAccountCommand.SURNAME_NOT_EMPTY, OpenAccountCommand.OPENING_BALANCE_NOT_EMPTY))
        );
    }

    private ResultActions requestPost(String url, Object content, ResultMatcher resultMatcher) throws Exception {
        return this.mockMvc.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(content)))
                .andExpect(resultMatcher);
    }

}
