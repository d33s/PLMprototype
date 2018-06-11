package com.enterprisesys.plm.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "parts")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Part extends AbstractPart{

//    The Part model contains the @ManyToOne annotation to declare that it has a many-to-one relationship
//    with the Assembly entity. It also uses the @JoinColumn annotation to declare the foreign key column.

	@Getter @Setter
	@JsonIgnore
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idAssembly", nullable = false, foreignKey = @ForeignKey(name = "ASSEMBLY"))
//    @JsonIgnoreProperties(ignoreUnknown = true, value = {"assemblyName", "object", "path"})
	private Assembly assembly;

	public Part() { }
	public Part(Part another) {
		this.setIdPart(another.getIdPart()) ;
		this.setPartName(another.getPartName());
		this.setPdfPath(another.getPdfPath());
		this.setPdf(another.getPdf());
		this.setAssembly(another.getAssembly());
	}

}
