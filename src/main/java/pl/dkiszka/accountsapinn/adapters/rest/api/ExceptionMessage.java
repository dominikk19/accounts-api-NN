package pl.dkiszka.accountsapinn.adapters.rest.api;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 19.06.2021
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class ExceptionMessage {
    static ExceptionMessage of(List<String> messages) {
        return new ExceptionMessage(messages);
    }

    private List<String> messages;
}

