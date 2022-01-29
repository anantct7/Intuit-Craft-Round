package com.example.intuit.entities.profile;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class BusinessAddress {

    private String business_address_line_1;

    private String business_address_line_2;

    private String business_city;

    private String business_state;

    private String business_zip;

    private String business_country;
}
