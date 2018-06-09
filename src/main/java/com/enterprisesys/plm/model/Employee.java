package com.enterprisesys.plm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "employees")
@JsonIgnoreProperties(ignoreUnknown = true)
public
class Employee {

    // An autogenerated id (unique for each user in the db)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    @JsonProperty("idEmployee")
    @Column(name="idEmployee")
    private Integer idEmployee;

    @NotNull
    @Getter @Setter
    @JsonProperty("firstName")
    @Column(name="firstName")
    private String firstName;

    @NotNull
    @Getter @Setter
    @JsonProperty("lastName")
    @Column(name="lastName")
    private String lastName;

    @Getter @Setter
    @JsonProperty("email")
    @Column(name="email")
    private String email;

    @Getter @Setter
//    @JsonProperty("orders")
//    @Column(name="orders")
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "employees")
    private Set<Order> orders;

    public Employee() { }

    public Employee(Integer id, String firstName, String lastName, String email, List<Order> orders) {
        this.idEmployee = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        if (!orders.isEmpty()) this.orders.addAll(orders);
        else this.orders = new HashSet<>();
    }

}