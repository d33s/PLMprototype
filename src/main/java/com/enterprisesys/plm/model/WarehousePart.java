package com.enterprisesys.plm.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "warehouse")
@JsonIgnoreProperties(ignoreUnknown = true)
public class WarehousePart extends AbstractPart {

    @Getter @Setter
    @JsonProperty("availablePieces")
    @Column(name="availablePieces")
    private String availablePieces;

    @Getter @Setter
    @Column(name="description")
    @JsonProperty("description")
    String description;

    public WarehousePart() { }

}