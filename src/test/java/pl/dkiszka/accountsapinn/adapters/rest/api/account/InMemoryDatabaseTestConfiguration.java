package pl.dkiszka.accountsapinn.adapters.rest.api.account;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import pl.dkiszka.accountsapinn.app.account.AccountService;
import pl.dkiszka.accountsapinn.app.account.InMemoryDatabase;
import pl.dkiszka.accountsapinn.app.account.TestAccountServiceFactory;
import pl.dkiszka.accountsapinn.domain.account.AccountRepository;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 19.06.2021
 */
@TestConfiguration
class InMemoryDatabaseTestConfiguration {

    @Bean
    AccountService accountService() {
        return TestAccountServiceFactory.creatAccountService(new InMemoryDatabase());
    }
}
