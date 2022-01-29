package com.example.intuit.repos;

import com.example.intuit.entities.profile.BusinessProfile;
import com.example.intuit.entities.subscription.ProductSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<ProductSubscription, Long> {

    List<ProductSubscription> findByBusinessProfile(BusinessProfile businessProfile);
}
