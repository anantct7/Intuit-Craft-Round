package com.example.intuit.repos;

import com.example.intuit.entities.profile.BusinessProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<BusinessProfile, Long> {
}
