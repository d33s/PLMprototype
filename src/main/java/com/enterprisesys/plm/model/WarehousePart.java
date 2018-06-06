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
public
class WarehousePart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    @JsonProperty("idPart")
    @Column(name="idPart")
    private Integer idPart;

    @NotNull
    @Getter @Setter
    @JsonProperty("partName")
    @Column(name="partName")
    private String partName;

    @Getter @Setter
    @JsonProperty("availablePieces")
    @Column(name="availablePieces")
    private String availablePieces;

    @Getter @Setter
    @Column(name="description")
    @JsonProperty("description")
    String description;

    @Getter @Setter
    @Column(name="pdfPath")
    @JsonProperty("pdfPath")
    private String pdfPath;

    @Getter @Setter
    @Column(name="pdf")
    @JsonProperty("pdf")
    @Lob
    private byte[] pdf;

    public WarehousePart() { }

}