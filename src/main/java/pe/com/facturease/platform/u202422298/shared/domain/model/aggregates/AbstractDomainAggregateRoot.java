package pe.com.facturease.platform.u202422298.shared.domain.model.aggregates;

import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.Collection;

/**
 * Base for domain aggregate roots, with no JPA persistence concerns.
 * Adapted from the provided Learning Center v2620 reference.
 *
 * @param <T> the concrete aggregate root type
 * @author Replace With Your Full Name
 */
public abstract class AbstractDomainAggregateRoot<T extends AbstractDomainAggregateRoot<T>>
        extends AbstractAggregateRoot<T> {

    /**
     * Registers a domain event.
     * @param event event to register
     */
    protected void registerDomainEvent(Object event) {
        super.registerEvent(event);
    }

    /**
     * Returns currently registered domain events.
     * @return domain events
     */
    @Override
    public Collection<Object> domainEvents() {
        return super.domainEvents();
    }

    /**
     * Clears pending domain events.
     */
    @Override
    public void clearDomainEvents() {
        super.clearDomainEvents();
    }
}
