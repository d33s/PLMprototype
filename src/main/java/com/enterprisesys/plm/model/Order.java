package com.enterprisesys.plm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

import static java.time.LocalTime.now;


@Entity
@Table(name = "orders")
@JsonIgnoreProperties(ignoreUnknown = true)
public
class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    @JsonProperty("idOrder")
    @Column(name="idOrder")
    private Integer idOrder;

    @NotNull
    @Getter @Setter
    @JsonProperty("customerID")
    @Column(name="customerID")
    private String customerID;

    @Getter @Setter
    @JsonProperty("assemblyID")
    @Column(name="assemblyID")
    private String assemblyID;

    @Getter @Setter
    @JsonProperty("orderDate")
    @Column(name="orderDate")
    private String orderDate;

    @Getter @Setter
//    @JsonProperty("employees")
//    @Column(name="employees")
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "orders_employees", joinColumns = { @JoinColumn(name = "idOrder") }, inverseJoinColumns = { @JoinColumn(name = "idEmployee") })
    //Hash set used so that every element of the set is unique and it is not possible do add same idOrder<->idEmployer relation more than once
    private Set<Employee> employees;

    @Getter @Setter
    @JsonProperty("status")
    @Column(name="status")
    private String status;

    public Order() {
        this.orderDate=now().toString();
    }

    public Order(Order another){
        this.idOrder = another.idOrder;
        this.customerID = another.customerID;
        this.assemblyID = another.assemblyID;
        this.orderDate = another.orderDate;
        if (!another.employees.isEmpty()) {
            this.employees = new HashSet<>();
            this.employees.addAll(another.employees);
        }
        else this.employees = new HashSet<>();
        this.status = another.status;
    }

    public void addEmployee(Employee worker) {
        this.employees.add(worker);
    }

}