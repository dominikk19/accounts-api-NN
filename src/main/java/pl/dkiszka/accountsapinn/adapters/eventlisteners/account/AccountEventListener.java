package pl.dkiszka.accountsapinn.adapters.eventlisteners.account;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.dkiszka.accountsapinn.app.account.AccountExchangeRateCommand;
import pl.dkiszka.accountsapinn.app.account.AccountService;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 19.06.2021
 */
@Component
@RequiredArgsConstructor
class AccountEventListener {
    private final AccountService accountService;

    @EventListener
    public void consume(AccountExchangeRateCommand command) {
        accountService.balanceExchange(command);
    }
}
