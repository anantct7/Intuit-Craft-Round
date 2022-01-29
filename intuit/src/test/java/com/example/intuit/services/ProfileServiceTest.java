package com.example.intuit.services;

import com.example.intuit.clients.ValidationClient;
import com.example.intuit.clients.ValidationClientImpl;
import com.example.intuit.entities.profile.BusinessProfile;
import com.example.intuit.entities.subscription.QbAccountingSubscription;
import com.example.intuit.exceptions.EntityNotFoundException;
import com.example.intuit.exceptions.ProfileValidationException;
import com.example.intuit.repos.ProfileRepository;
import com.example.intuit.repos.SubscriptionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;

public class ProfileServiceTest {

    private ProfileService profileService;

    private ProfileRepository profileRepository;

    private SubscriptionRepository subscriptionRepository;

    private ValidationClient validationClient;

    @BeforeEach
    void init() {
        profileRepository = Mockito.mock(ProfileRepository.class);
        validationClient = Mockito.mock(ValidationClientImpl.class);
        subscriptionRepository = Mockito.mock(SubscriptionRepository.class);
        profileService = new ProfileService(profileRepository, subscriptionRepository, validationClient);
    }

    @Test
    void testGetAllBusinessProfiles() {
        Long id = 1L;
        BusinessProfile profile = new BusinessProfile();
        profile.setBusiness_profile_id(id);
        profile.setCompany_name("TEST");
        Mockito.when(profileRepository.findAll()).thenReturn(List.of(profile));
        List <BusinessProfile> fetchedProfiles = profileService.getAllBusinessProfiles();
        assertThat(fetchedProfiles.get(0).getCompany_name()).isEqualTo(profile.getCompany_name());
    }

    @Test
    void testGetBusinessProfileById() {
        Long id = 1L;
        BusinessProfile profile = new BusinessProfile();
        profile.setBusiness_profile_id(id);
        profile.setCompany_name("TEST");
        Mockito.when(profileRepository.findById(id)).thenReturn(Optional.of(profile));
        BusinessProfile fetchedProfile = profileService.getBusinessProfileById(id);
        assertThat(fetchedProfile.getCompany_name()).isEqualTo(profile.getCompany_name());
    }

    @Test
    void testGetBusinessProfileThrowsException() {
        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            BusinessProfile fetchedProfile = profileService.getBusinessProfileById(1L);
        });
        Assertions.assertEquals("Business Profile Not Found", exception.getMessage());
    }

    @Test
    void testCreateValidationFail() {
        BusinessProfile profile = new BusinessProfile();
        QbAccountingSubscription accountingSubscription = new QbAccountingSubscription();
        Mockito.when(subscriptionRepository.findByBusinessProfile(profile)).thenReturn(List.of(accountingSubscription));
        Mockito.when(validationClient.approve(accountingSubscription)).thenReturn(CompletableFuture.completedFuture(false));

        ProfileValidationException exception = Assertions.assertThrows(ProfileValidationException.class, () -> {
            BusinessProfile createdProfile = profileService.createBusinessProfile(profile);
        });
        Assertions.assertEquals("Profile Create Validation Failed", exception.getMessage());
    }

    @Test
    void testUpdateValidationFail() {
        Long id = 1L;
        BusinessProfile profile = new BusinessProfile();
        profile.setBusiness_profile_id(id);
        QbAccountingSubscription accountingSubscription = new QbAccountingSubscription();
        Mockito.when(subscriptionRepository.findByBusinessProfile(profile)).thenReturn(List.of(accountingSubscription));
        Mockito.when(validationClient.approve(accountingSubscription)).thenReturn(CompletableFuture.completedFuture(false));

        ProfileValidationException exception = Assertions.assertThrows(ProfileValidationException.class, () -> {
            BusinessProfile createdProfile = profileService.updateBusinessProfile(id, profile);
        });
        Assertions.assertEquals("Profile Update Validation Failed", exception.getMessage());
    }

    @Test
    void testCreateBusinessProfile() {
        Long id = 1L;
        BusinessProfile profile = new BusinessProfile();
        profile.setBusiness_profile_id(id);
        QbAccountingSubscription accountingSubscription = new QbAccountingSubscription();
        Mockito.when(subscriptionRepository.findByBusinessProfile(profile)).thenReturn(List.of(accountingSubscription));
        Mockito.when(validationClient.approve(accountingSubscription)).thenReturn(CompletableFuture.completedFuture(true));
        Mockito.when(profileRepository.save(profile)).thenReturn(profile);
        BusinessProfile createdProfile = profileService.createBusinessProfile(profile);
        assertThat(createdProfile.getCompany_name()).isEqualTo(profile.getCompany_name());
    }

    @Test
    void testUpdateBusinessProfile() {
        Long id = 1L;
        BusinessProfile profile = new BusinessProfile();
        profile.setBusiness_profile_id(id);
        QbAccountingSubscription accountingSubscription = new QbAccountingSubscription();
        Mockito.when(subscriptionRepository.findByBusinessProfile(profile)).thenReturn(List.of(accountingSubscription));
        Mockito.when(validationClient.approve(accountingSubscription)).thenReturn(CompletableFuture.completedFuture(true));
        Mockito.when(profileRepository.save(profile)).thenReturn(profile);
        Mockito.when(profileRepository.findById(id)).thenReturn(Optional.of(profile));
        BusinessProfile updatedProfile = profileService.updateBusinessProfile(id, profile);
        assertThat(updatedProfile.getCompany_name()).isEqualTo(profile.getCompany_name());
    }
}
