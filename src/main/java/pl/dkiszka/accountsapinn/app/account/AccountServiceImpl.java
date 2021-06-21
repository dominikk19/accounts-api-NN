package pl.dkiszka.accountsapinn.app.account;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dkiszka.accountsapinn.adapters.rest.api.nbp.NbpFacade;
import pl.dkiszka.accountsapinn.app.nbp.ExchangeRateCommand;
import pl.dkiszka.accountsapinn.domain.DomainEventPublisher;
import pl.dkiszka.accountsapinn.domain.account.Account;
import pl.dkiszka.accountsapinn.domain.account.AccountFactory;
import pl.dkiszka.accountsapinn.domain.account.AccountRepository;
import pl.dkiszka.accountsapinn.domain.account.ExchangeType;
import pl.dkiszka.accountsapinn.query.account.AccountNotFoundException;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 19.06.2021
 */
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Transactional
class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final DomainEventPublisher publisher;
    private final NbpFacade nbpFacade;

    @Override
    public Account openAccount(String firstname, String surname, BigDecimal openingBalance) {
        var newClient = AccountFactory.openNewAccountForClient(firstname, surname, openingBalance);
        return accountRepository.save(newClient);
    }

    @Override
    public String startBalanceExchange(UUID uuid, ExchangeType type) {
        var command = ExchangeRateCommand.create(uuid, type);
        publisher.publish(command);
        return "Currency conversion has started";
    }

    @Override
    public void balanceExchange(AccountExchangeRateCommand command) {
        accountRepository.findByUuid(command.getAccountUuid())
                .ifPresent(account -> account.exchangeBalance(command.getExchangeType(), command.getExchangeRate()));
    }

    @Override
    public String exchangeAmount(UUID uuid, ExchangeType exchangeType, BigDecimal amount) {
        return nbpFacade.getExchangeRate()
                .map(rate -> exchangeAmountByExchangeRate(uuid, exchangeType, amount, rate))
                .getOrElseThrow(() -> new AccountServiceException("Can not exchange amount."));
    }

    private String exchangeAmountByExchangeRate(UUID uuid, ExchangeType exchangeType, BigDecimal amount, BigDecimal rate) {
        var account = accountRepository.findByUuid(uuid)
                .map(ac -> ac.exchangeAmount(exchangeType, amount, rate))
                .orElseThrow(() -> new AccountNotFoundException(String.format("Account by uuid %s not found", uuid)));
        return String.format("Amount for account for uuid %s has been changed", account.getUuid());
    }
}
