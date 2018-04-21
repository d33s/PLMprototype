package com.enterprisesys.plm.orders;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    public Order() {
        this.orderDate=now().toString();
    }

}