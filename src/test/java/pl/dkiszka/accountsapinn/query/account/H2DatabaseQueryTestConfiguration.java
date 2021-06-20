package pl.dkiszka.accountsapinn.query.account;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 20.06.2021
 */
@TestConfiguration
@EnableJpaRepositories
class H2DatabaseQueryTestConfiguration {


    @Bean
    DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .addScript("classpath:/db/V1.0.0_2021.06.20.09.40__creat_table_accounts.sql")
                .addScript("classpath:/db/V1.0.0_2021.06.20.09.50__insert_into_accounts.sql")
                .build();
    }

}
