package com.enterprisesys.plm.parts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "parts")
@JsonIgnoreProperties(ignoreUnknown = true)
public
class Part {

    // An autogenerated id (unique for each user in the db)
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
    @JsonProperty("assemblyName")
    @Column(name="assemblyName")
    private String assemblyName;

    public Part() { }

    public Part(Integer id) {
        this.idPart = id;
    }

    public Part(String name, String assembly) {
        this.partName = name;
        this.assemblyName = assembly;
    }

    public Part(Integer id, String part, String assembly) {
        super();
        this.idPart = id;
        this.partName = part;
        this.assemblyName = assembly;
    }

}