package com.example.intuit.services;

import com.example.intuit.entities.subscription.ProductSubscription;
import com.example.intuit.exceptions.EntityNotFoundException;
import com.example.intuit.repos.SubscriptionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public List<ProductSubscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    public ProductSubscription getProductSubscriptionById(Long id) {
        Optional<ProductSubscription> subscription = subscriptionRepository.findById(id);
        if (subscription.isEmpty()) {
            throw new EntityNotFoundException("Product Subscription Not Found");
        }
        return subscription.get();
    }

    public ProductSubscription createProductSubscription(ProductSubscription productSubscription) {
        return subscriptionRepository.save(productSubscription);
    }

    public ProductSubscription updateProductSubscription(Long id, ProductSubscription productSubscription) {
        Optional<ProductSubscription> existingSubscription = subscriptionRepository.findById(id);
        if (existingSubscription.isEmpty()) {
            throw new EntityNotFoundException("Product Subscription Not Found");
        }
        ProductSubscription currentSubscription = existingSubscription.get();
        if (!productSubscription.getSubscription_type().equals(currentSubscription.getSubscription_type())) {
            subscriptionRepository.deleteById(id);
        }
        productSubscription.setSubscription_id(currentSubscription.getSubscription_id());
        return subscriptionRepository.save(productSubscription);
    }

    public void deleteProductSubscriptionById(Long id) {
        subscriptionRepository.deleteById(id);
    }
}
