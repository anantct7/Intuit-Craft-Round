package com.example.intuit.entities.subscription;

import com.example.intuit.clients.ValidationClient;
import com.example.intuit.entities.profile.BusinessProfile;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.concurrent.CompletableFuture;

@Data
@Entity
@Component
@Table(name ="SUBSCRIPTIONS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@ToString(exclude = "businessProfile")
@EqualsAndHashCode(exclude = "businessProfile")
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "subscription_type")
public abstract class ProductSubscription {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subscription_id;

    @Column(name = "subscription_type", insertable = false, updatable = false)
    private String subscription_type;

    @ManyToOne
    @JoinColumn(name="business_profile_id")
    private BusinessProfile businessProfile;

    public abstract CompletableFuture<Boolean> validate(ValidationClient validationClient);
}
