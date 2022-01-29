package com.example.intuit.dtos;

import lombok.Data;

@Data
public class ProductSubscriptionDTO {

    private Long subscription_id;

    private String subscription_type;

    private String business_profile_id;
}
