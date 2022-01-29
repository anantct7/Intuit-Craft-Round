package com.example.intuit.controllers;

import com.example.intuit.dtos.BusinessProfileDTO;
import com.example.intuit.entities.profile.BusinessProfile;
import com.example.intuit.services.ProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/profile")
    public ResponseEntity<BusinessProfileDTO> createProfile(@RequestBody BusinessProfileDTO businessProfileDTO) {
        BusinessProfile businessProfile = modelMapper.map(businessProfileDTO, BusinessProfile.class);
        BusinessProfile createdProfile = profileService.createBusinessProfile(businessProfile);
        return new ResponseEntity<>(modelMapper.map(createdProfile, BusinessProfileDTO.class), HttpStatus.ACCEPTED);
    }

    @GetMapping("/profiles")
    public ResponseEntity<List> getAllProfiles() {
        List<BusinessProfile> businessProfiles = profileService.getAllBusinessProfiles();
        return new ResponseEntity<>(businessProfiles.stream()
                .map(profile -> modelMapper.map(profile, BusinessProfileDTO.class))
                .collect(Collectors.toList()), HttpStatus.ACCEPTED);
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<BusinessProfileDTO> getProfileById(@PathVariable("id") Long id) {
        BusinessProfile businessProfile = profileService.getBusinessProfileById(id);
        return new ResponseEntity<>(modelMapper.map(businessProfile, BusinessProfileDTO.class), HttpStatus.ACCEPTED);
    }

    @PutMapping("/profile")
    public ResponseEntity<BusinessProfileDTO> updateProfile(@RequestBody BusinessProfileDTO businessProfileDTO) {
        Long id = businessProfileDTO.getBusiness_profile_id();
        BusinessProfile businessProfile = modelMapper.map(businessProfileDTO, BusinessProfile.class);
        BusinessProfile updatedProfile = profileService.updateBusinessProfile(id, businessProfile);
        return new ResponseEntity<>(modelMapper.map(updatedProfile, BusinessProfileDTO.class), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/profile/{id}")
    public void deleteProfileById(@PathVariable("id") Long id) {
        profileService.deleteBusinessProfileById(id);
    }

}
