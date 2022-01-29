package com.example.intuit.services;

import com.example.intuit.entities.subscription.ProductSubscription;
import com.example.intuit.entities.subscription.QbPaymentsSubscription;
import com.example.intuit.exceptions.EntityNotFoundException;
import com.example.intuit.repos.SubscriptionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class SubscriptionServiceTest {

    private SubscriptionRepository subscriptionRepository;

    private SubscriptionService subscriptionService;

    @BeforeEach
    void init() {
        subscriptionRepository = Mockito.mock(SubscriptionRepository.class);
        subscriptionService = new SubscriptionService(subscriptionRepository);
    }

    @Test
    void testGetAllSubscriptions() {
        Long id = 1L;
        QbPaymentsSubscription paymentsSubscription = new QbPaymentsSubscription();
        paymentsSubscription.setSubscription_id(id);
        Mockito.when(subscriptionRepository.findAll()).thenReturn(List.of(paymentsSubscription));
        List <ProductSubscription> fetchedSubscriptions = subscriptionService.getAllSubscriptions();
        assertThat(fetchedSubscriptions.get(0).getSubscription_id()).isEqualTo(paymentsSubscription.getSubscription_id());
    }

    @Test
    void testGetSubscriptionById() {
        Long id = 1L;
        QbPaymentsSubscription paymentsSubscription = new QbPaymentsSubscription();
        paymentsSubscription.setSubscription_id(id);
        Mockito.when(subscriptionRepository.findById(id)).thenReturn(Optional.of(paymentsSubscription));
        ProductSubscription fetchedSubscription = subscriptionService.getProductSubscriptionById(id);
        assertThat(fetchedSubscription.getSubscription_id()).isEqualTo(paymentsSubscription.getSubscription_id());
    }

    @Test
    void testGetSubscriptionThrowsException() {
        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            ProductSubscription fetchedSubscription = subscriptionService.getProductSubscriptionById(1L);
        });
        Assertions.assertEquals("Product Subscription Not Found", exception.getMessage());
    }

    @Test
    void testCreateSubscription() {
        Long id = 1L;
        QbPaymentsSubscription paymentsSubscription = new QbPaymentsSubscription();
        paymentsSubscription.setSubscription_id(id);
        Mockito.when(subscriptionRepository.save(paymentsSubscription)).thenReturn(paymentsSubscription);
        ProductSubscription createdSubscription = subscriptionService.createProductSubscription(paymentsSubscription);
        assertThat(createdSubscription.getSubscription_id()).isEqualTo(paymentsSubscription.getSubscription_id());
    }

    @Test
    void testUpdateSubscription() {
        Long id = 1L;
        QbPaymentsSubscription paymentsSubscription = new QbPaymentsSubscription();
        paymentsSubscription.setSubscription_id(id);
        paymentsSubscription.setSubscription_type("qbPaymentsSubscription");
        Mockito.when(subscriptionRepository.findById(id)).thenReturn(Optional.of(paymentsSubscription));
        Mockito.when(subscriptionRepository.save(paymentsSubscription)).thenReturn(paymentsSubscription);
        ProductSubscription updatedSubscription = subscriptionService.updateProductSubscription(id, paymentsSubscription);
        assertThat(updatedSubscription.getSubscription_id()).isEqualTo(paymentsSubscription.getSubscription_id());
    }
}
