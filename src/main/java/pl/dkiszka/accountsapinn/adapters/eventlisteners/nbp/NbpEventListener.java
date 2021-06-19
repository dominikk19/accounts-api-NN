package pl.dkiszka.accountsapinn.adapters.eventlisteners.nbp;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.dkiszka.accountsapinn.app.account.AccountExchangeRateCommand;
import pl.dkiszka.accountsapinn.app.nbp.ExchangeRateCommand;
import pl.dkiszka.accountsapinn.app.nbp.NbpService;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 19.06.2021
 */
@Component
@RequiredArgsConstructor
class NbpEventListener {

    private final NbpService nbpService;
    private final ApplicationEventPublisher publisher;

    @EventListener
    public void consume(ExchangeRateCommand command) {
        var rate = nbpService.getUsdExchangeRate();
        var accountExchangeRateCommand = AccountExchangeRateCommand.create(command, rate);
        publisher.publishEvent(accountExchangeRateCommand);
    }
}
