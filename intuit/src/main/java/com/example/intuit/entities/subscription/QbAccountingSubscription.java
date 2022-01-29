package com.example.intuit.entities.subscription;

import com.example.intuit.clients.ValidationClient;
import org.springframework.stereotype.Component;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.concurrent.CompletableFuture;

@Entity
@Component
@DiscriminatorValue("qbAccountingSubscription")
public class QbAccountingSubscription extends ProductSubscription {

    @Override
    public CompletableFuture<Boolean> validate(ValidationClient validationClient) {
        return validationClient.approve(this);
    }
}
