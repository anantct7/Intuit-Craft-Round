package com.example.intuit.entities.profile;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class TaxIdentifiers {

    private String pan;

    private String ein;
}
