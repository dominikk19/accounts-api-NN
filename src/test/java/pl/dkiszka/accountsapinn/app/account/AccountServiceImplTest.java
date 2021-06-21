package pl.dkiszka.accountsapinn.app.account;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import pl.dkiszka.accountsapinn.adapters.rest.api.nbp.NbpFacade;
import pl.dkiszka.accountsapinn.app.nbp.ExchangeRateCommand;
import pl.dkiszka.accountsapinn.domain.DomainEventPublisher;
import pl.dkiszka.accountsapinn.domain.account.Account;
import pl.dkiszka.accountsapinn.domain.account.AccountAssertions;
import pl.dkiszka.accountsapinn.domain.account.AccountRepository;
import pl.dkiszka.accountsapinn.domain.account.ExchangeType;
import pl.dkiszka.accountsapinn.domain.account.TestData;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 19.06.2021
 */
class AccountServiceImplTest {

    private final AccountRepository accountRepository = mock(AccountRepository.class);
    private final DomainEventPublisher publisher = mock(DomainEventPublisher.class);
    private final NbpFacade nbpFacade = mock(NbpFacade.class);
    private AccountService accountService;


    @Test
    void given_all_data_to_create_account_then_should_be_save_correct_account() {
        accountService = new AccountServiceImpl(accountRepository, publisher, nbpFacade);
        ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);

        accountService.openAccount(TestData.FIRSTNAME, TestData.SURNAME, TestData.OPENING_BALANCE_TEN);

        BDDMockito.then(accountRepository).should().save(captor.capture());

        AccountAssertions.assertThat(captor.getValue())
                .hasContainUuidCompatibleWithPattern()
                .hasBalanceInPlnEqualsTo(TestData.OPENING_BALANCE_TEN)
                .hasBalanceInUsdEqualsTo(BigDecimal.ZERO)
                .hasFirstnameEqualsTo(TestData.FIRSTNAME)
                .hasSurnameEqualsTo(TestData.SURNAME);
    }

    @Test
    void given_all_data_to_create_account_then_should_return_saved_account() {
        accountService = new AccountServiceImpl(new InMemoryDatabase(), publisher, nbpFacade);

        var actual = accountService.openAccount(TestData.FIRSTNAME, TestData.SURNAME, TestData.OPENING_BALANCE_TEN);

        AccountAssertions.assertThat(actual)
                .hasContainUuidCompatibleWithPattern()
                .hasBalanceInPlnEqualsTo(TestData.OPENING_BALANCE_TEN)
                .hasFirstnameEqualsTo(TestData.FIRSTNAME)
                .hasSurnameEqualsTo(TestData.SURNAME);
    }

    @Test
    void when_start_balance_exchange_then_publish_ExchangeRateCommand() {
        accountService = new AccountServiceImpl(new InMemoryDatabase(), publisher, nbpFacade);
        ArgumentCaptor<ExchangeRateCommand> captor = ArgumentCaptor.forClass(ExchangeRateCommand.class);
        var accountUuid = UUID.randomUUID();
        var type = ExchangeType.TO_USD;
        accountService.startBalanceExchange(accountUuid, type);

        then(publisher).should().publish(captor.capture());

        Assertions.assertThat(captor.getValue())
                .isNotNull()
                .hasFieldOrPropertyWithValue("accountUuid", accountUuid)
                .hasFieldOrPropertyWithValue("exchangeType", type);
    }

    @Test
    void when_balance_exchange_then_account_should_be_updated() {
        accountService = new AccountServiceImpl(accountRepository, publisher, nbpFacade);
        var accountUuid = UUID.randomUUID();
        var type = ExchangeType.TO_USD;
        accountService.balanceExchange(AccountExchangeRateCommand.create(ExchangeRateCommand.create(accountUuid, type), BigDecimal.ONE));

        verify(accountRepository, times(1)).findByUuid(accountUuid);
    }


}
