package com.enterprisesys.plm.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@MappedSuperclass
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractPart {

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
	@Column(name="pdfPath")
	@JsonProperty("pdfPath")
	private String pdfPath;

	@Getter @Setter
	@Column(name="pdf")
	@JsonProperty("pdf")
	@Lob
	private byte[] pdf;

}