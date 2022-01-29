package com.example.intuit.controllers;

import com.example.intuit.dtos.ProductSubscriptionDTO;
import com.example.intuit.entities.subscription.ProductSubscription;
import com.example.intuit.services.SubscriptionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired(required = false)
    private Map<String, ProductSubscription> subscriptionMap;

    @PostMapping("/subscription")
    public ResponseEntity<ProductSubscriptionDTO> createSubscription(@RequestBody ProductSubscriptionDTO productSubscriptionDTO) {
        ProductSubscription productSubscription = modelMapper.map(productSubscriptionDTO,
                subscriptionMap.get(productSubscriptionDTO.getSubscription_type()).getClass());
        ProductSubscription createdSubscription = subscriptionService.createProductSubscription(productSubscription);
        return new ResponseEntity<>(modelMapper.map(createdSubscription, ProductSubscriptionDTO.class), HttpStatus.ACCEPTED);
    }

    @GetMapping("/subscriptions")
    public ResponseEntity<List> getAllSubscriptions() {
        List<ProductSubscription> productSubscriptions = subscriptionService.getAllSubscriptions();
        return new ResponseEntity<>(productSubscriptions.stream()
                .map(productSubscription -> modelMapper.map(productSubscription, ProductSubscriptionDTO.class))
                .collect(Collectors.toList()), HttpStatus.ACCEPTED);
    }

    @GetMapping("/subscription/{id}")
    public ResponseEntity<ProductSubscriptionDTO> getSubscriptionById(@PathVariable("id") Long id) {
        ProductSubscription productSubscription = subscriptionService.getProductSubscriptionById(id);
        return new ResponseEntity<>(modelMapper.map(productSubscription, ProductSubscriptionDTO.class), HttpStatus.ACCEPTED);
    }

    @PutMapping("/subscription")
    public ResponseEntity<ProductSubscriptionDTO> updateSubscription(@RequestBody ProductSubscriptionDTO productSubscriptionDTO) {
        Long id = productSubscriptionDTO.getSubscription_id();
        ProductSubscription productSubscription = modelMapper.map(productSubscriptionDTO,
                subscriptionMap.get(productSubscriptionDTO.getSubscription_type()).getClass());
        ProductSubscription updatedSubscription = subscriptionService.updateProductSubscription(id, productSubscription);
        return new ResponseEntity<>(modelMapper.map(updatedSubscription, ProductSubscriptionDTO.class), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/subscription/{id}")
    public void deleteSubscriptionById(@PathVariable("id") Long id) {
        subscriptionService.deleteProductSubscriptionById(id);
    }
}
