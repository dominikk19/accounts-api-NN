package pl.dkiszka.accountsapinn.adapters.rest.api.account;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.dkiszka.accountsapinn.app.account.AccountService;
import pl.dkiszka.accountsapinn.domain.account.ExchangeType;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 19.06.2021
 */
@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class AccountController {

    private final AccountService accountService;

    @PostMapping
    ResponseEntity<Void> openAccount(@Valid @RequestBody OpenAccountCommand command) {
        var account = accountService.openAccount(command.getFirstname(), command.getSurname(), command.getOpeningBalance());
        var uriLocation = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{uuid}")
                .buildAndExpand(account.getUuid())
                .toUri();
        return ResponseEntity.created(uriLocation).build();
    }

    @PatchMapping("/{uuid}")
    ResponseEntity<BaseResponseDto> exchangeBalance(@NotNull @PathVariable UUID uuid, @Valid @RequestParam ExchangeType type) {
        var message = accountService.startBalanceExchange(uuid, type);
        return ResponseEntity.ok(new BaseResponseDto(message));
    }

    @PutMapping("/{uuid}/exchange-amount")
    ResponseEntity<BaseResponseDto> exchangeBalance(@NotNull @PathVariable UUID uuid, @Valid @RequestBody ExchangeAmountCommand command) {
        var message = accountService.exchangeAmount(uuid, command.getExchangeType(), command.getAmount());
        return ResponseEntity.ok(new BaseResponseDto(message));
    }

}
