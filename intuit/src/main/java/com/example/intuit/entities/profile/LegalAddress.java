package com.example.intuit.entities.profile;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class LegalAddress {

    private String legal_address_line_1;

    private String legal_address_line_2;

    private String legal_city;

    private String legal_state;

    private String legal_zip;

    private String legal_country;
}
