package com.example.intuit.dtos;

import lombok.Data;

@Data
public class BusinessProfileDTO {

    private Long business_profile_id;

    private String company_name;

    private String legal_name;

    private String business_address_line_1;

    private String business_address_line_2;

    private String business_city;

    private String business_state;

    private String business_zip;

    private String business_country;

    private String legal_address_line_1;

    private String legal_address_line_2;

    private String legal_city;

    private String legal_state;

    private String legal_zip;

    private String legal_country;

    private String pan;

    private String ein;

    private String email;

    private String website;
}
