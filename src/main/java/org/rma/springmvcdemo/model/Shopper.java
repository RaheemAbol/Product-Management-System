package org.rma.springmvcdemo.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@DiscriminatorValue("SHOPPER")
public class Shopper extends BaseUser {

    @OneToOne(mappedBy = "shopper", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private Cart cart;

    public Shopper() {
        super();
        setRole(Role.SHOPPER); // Set the role to SHOPPER by default
    }


}