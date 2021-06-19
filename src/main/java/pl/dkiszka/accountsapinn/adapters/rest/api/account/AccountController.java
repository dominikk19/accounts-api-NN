package pl.dkiszka.accountsapinn.adapters.rest.api.account;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.dkiszka.accountsapinn.app.account.AccountService;

import javax.validation.Valid;

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
}
