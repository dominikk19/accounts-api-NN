package pl.dkiszka.accountsapinn.adapters.eventchanel.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import pl.dkiszka.accountsapinn.domain.DomainEvent;
import pl.dkiszka.accountsapinn.domain.DomainEventPublisher;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 19.06.2021
 */
@Component
@RequiredArgsConstructor
class SpringEventPublisher implements DomainEventPublisher {
    private final ApplicationEventPublisher publisher;

    @Override
    public void publish(DomainEvent event) {
        publisher.publishEvent(event);
    }
}
