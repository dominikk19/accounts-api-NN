package pl.dkiszka.accountsapinn.query.account;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dkiszka.accountsapinn.adapters.rest.api.nbp.NbpFacade;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 20.06.2021
 */

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class AccountQueryController {
    private final AccountQueryRepository accountQueryRepository;
    private final NbpFacade nbpFacade;

    @GetMapping("/{uuid}")
    ResponseEntity<AccountDto> findByUuid(@NotNull @PathVariable UUID uuid) {
        var accountDto = accountQueryRepository.findByUuid(uuid)
                .map(this::convertToDto)
                .orElseThrow(() -> new AccountNotFoundException(String.format("Account by uuid : %s not found", uuid)));
        return ResponseEntity.ok(accountDto);
    }

    private AccountDto convertToDto(AccountReadModelEntity account) {
        return nbpFacade.getExchangeRate()
                .map(account::toAccountDtoWithMultiCurrency)
                .getOrElse(account::toAccountDtoWithSingleCurrency);
    }

}
