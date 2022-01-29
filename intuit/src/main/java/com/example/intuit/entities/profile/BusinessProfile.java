package com.example.intuit.entities.profile;

import com.example.intuit.entities.subscription.ProductSubscription;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Set;

@Data
@Entity
@Table(name ="PROFILES")
@ToString(exclude = "productSubscriptions")
@EqualsAndHashCode(exclude = "productSubscriptions")
public class BusinessProfile {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long business_profile_id;

    private String company_name;

    private String legal_name;

    @Embedded
    private BusinessAddress business_address;

    @Embedded
    private LegalAddress legal_address;

    @Embedded
    private TaxIdentifiers tax_identifiers;

    @Email
    private String email;

    @URL
    private String website;

    @OneToMany(mappedBy = "businessProfile", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ProductSubscription> productSubscriptions;

}
