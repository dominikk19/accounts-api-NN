package pl.dkiszka.accountsapinn.app.account;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import pl.dkiszka.accountsapinn.domain.account.Account;
import pl.dkiszka.accountsapinn.domain.account.AccountAssertions;
import pl.dkiszka.accountsapinn.domain.account.AccountRepository;
import pl.dkiszka.accountsapinn.domain.account.Currency;
import pl.dkiszka.accountsapinn.domain.account.TestData;

import static org.mockito.Mockito.mock;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 19.06.2021
 */
class AccountServiceImplTest {

    private final AccountRepository accountRepository = mock(AccountRepository.class);
    private AccountService accountService;


    @Test
    void given_all_data_to_create_account_then_should_be_save_correct_account() {
        accountService = new AccountServiceImpl(accountRepository);
        ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);

        accountService.openAccount(TestData.FIRSTNAME, TestData.SURNAME, TestData.OPENING_BALANCE_TEN);

        BDDMockito.then(accountRepository).should().save(captor.capture());

        AccountAssertions.assertThat(captor.getValue())
                .hasContainUuidCompatibleWithPattern()
                .hasBalanceEqualsTo(TestData.OPENING_BALANCE_TEN)
                .hasCurrencyEqualsTo(Currency.PLN)
                .hasFirstnameEqualsTo(TestData.FIRSTNAME)
                .hasSurnameEqualsTo(TestData.SURNAME);
    }

    @Test
    void given_all_data_to_create_account_then_should_return_saved_account() {
        accountService = new AccountServiceImpl(new InMemoryDatabase());

        var actual = accountService.openAccount(TestData.FIRSTNAME, TestData.SURNAME, TestData.OPENING_BALANCE_TEN);

        AccountAssertions.assertThat(actual)
                .hasContainUuidCompatibleWithPattern()
                .hasBalanceEqualsTo(TestData.OPENING_BALANCE_TEN)
                .hasCurrencyEqualsTo(Currency.PLN)
                .hasFirstnameEqualsTo(TestData.FIRSTNAME)
                .hasSurnameEqualsTo(TestData.SURNAME);
    }


}
