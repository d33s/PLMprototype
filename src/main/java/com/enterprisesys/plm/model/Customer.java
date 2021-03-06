package com.enterprisesys.plm.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "customers")
@JsonIgnoreProperties(ignoreUnknown = true)
public
class Customer {

    // An autogenerated id (unique for each user in the db)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    @JsonProperty("idCustomer")
    @Column(name="idCustomer")
    private Integer idCustomer;

    @NotNull
    @Getter @Setter
    @JsonProperty("customerName")
    @Column(name="customerName")
    private String customerName;

    @Getter @Setter
    @JsonProperty("address")
    @Column(name="address")
    private String address;

    public Customer() { }

}