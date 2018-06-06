package com.enterprisesys.plm.assemblies;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "assemblies")
@JsonIgnoreProperties(ignoreUnknown = true)
public
class Assembly {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    @JsonProperty("idAssembly")
    @Column(name="idAssembly")
    private Integer idAssembly;

    @NotNull
    @Getter @Setter
    @JsonProperty("assemblyName")
    @Column(name="assemblyName")
    private String assemblyName;

    @Getter @Setter
    @Column(name="object")
    @JsonProperty("object")
    @Lob
    private byte[] object;

    @Getter @Setter
    @Column(name="path")
    @JsonProperty("path")
    private String path;

    public Assembly() { }

    public Assembly(Integer idAssembly, String name, byte[] obj, String path) {
        this.setIdAssembly(idAssembly);
        this.setAssemblyName(name);
        this.setObject(obj);
        this.setPath(path);
    }

}