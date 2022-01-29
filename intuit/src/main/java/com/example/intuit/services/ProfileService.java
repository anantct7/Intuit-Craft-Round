package com.example.intuit.services;

import com.example.intuit.exceptions.EntityNotFoundException;
import com.example.intuit.exceptions.ProfileValidationException;
import com.example.intuit.clients.ValidationClient;
import com.example.intuit.entities.profile.BusinessProfile;
import com.example.intuit.entities.subscription.ProductSubscription;
import com.example.intuit.repos.ProfileRepository;
import com.example.intuit.repos.SubscriptionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private ValidationClient validationClient;

    public List<BusinessProfile> getAllBusinessProfiles() {
        return profileRepository.findAll();
    }

    public BusinessProfile getBusinessProfileById(Long id) {
        Optional<BusinessProfile> profile = profileRepository.findById(id);
        if (profile.isEmpty()) {
            throw new EntityNotFoundException("Business Profile Not Found");
        }
        return profile.get();
    }

    public BusinessProfile createBusinessProfile(BusinessProfile profile) {
        BusinessProfile createdProfile = profileRepository.save(profile);
        if (approveProfileCreateOrUpdate(createdProfile)) {
            return createdProfile;
        } else {
            profileRepository.deleteById(createdProfile.getBusiness_profile_id());
            throw new ProfileValidationException("Profile Create Validation Failed");
        }
    }

    public BusinessProfile updateBusinessProfile(Long id, BusinessProfile profile) {
        if (approveProfileCreateOrUpdate(profile)) {
            Optional<BusinessProfile> existingProfile = profileRepository.findById(id);
            if (existingProfile.isEmpty()) {
                throw new EntityNotFoundException("Business Profile Not Found");
            }
            profile.setBusiness_profile_id(existingProfile.get().getBusiness_profile_id());
            return profileRepository.save(profile);
        } else {
            throw new ProfileValidationException("Profile Update Validation Failed");
        }
    }

    public void deleteBusinessProfileById(Long id) {
        profileRepository.deleteById(id);
    }

    private boolean approveProfileCreateOrUpdate(BusinessProfile profile) {
        List <ProductSubscription> subscriptions = subscriptionRepository.findByBusinessProfile(profile);
        List <CompletableFuture<Boolean>> futures = subscriptions.stream()
                .map(subscription -> subscription.validate(validationClient))
                .collect(Collectors.toList());
        CompletableFuture<List<Boolean>> results = sequence(futures);
        try {
            return !results.get().contains(false);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static <T> CompletableFuture<List<T>> sequence(List<CompletableFuture<T>> futures) {
        CompletableFuture<Void> allDoneFuture =
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
        return allDoneFuture.thenApply(v ->
                futures.stream().
                        map(CompletableFuture::join).
                        collect(Collectors.<T>toList())
        );
    }
}
