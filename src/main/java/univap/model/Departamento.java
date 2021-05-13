package univap.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.fasterxml.jackson.annotation.JsonView;

import univap.ValidationGroups;
import univap.controller.View;

@Entity
@Table(name = "dep_departamento")
public class Departamento {
	@NotNull(groups = ValidationGroups.GestorId.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dep_id")
	@JsonView(View.ViewResumo.class)
	private Long id;

	@NotBlank
	@Size(max = 30)
	@Column(name = "dep_campus")
	@JsonView(View.ViewResumo.class)
	private String campus;

	@NotBlank
	@Size(max = 30)
	@Column(name = "dep_bloco")
	@JsonView(View.ViewResumo.class)
	private String bloco;

	@NotBlank
	@Size(max = 100)
	@Column(name = "dep_departamento")
	@JsonView(View.ViewResumo.class)
	private String departamento;

	@NotBlank
	@Size(max = 30)
	@Column(name = "dep_ccusto")
	@JsonView(View.ViewResumo.class)
	private String ccusto;

	@Valid
	@ConvertGroup(from = Default.class, to = ValidationGroups.GestorId.class)
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "dep_ges_id")
	@JsonView(View.ViewResumo.class)
	private Gestor gestor;

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCampus() {
		return campus;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}

	public String getBloco() {
		return bloco;
	}

	public void setBloco(String bloco) {
		this.bloco = bloco;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getCcusto() {
		return ccusto;
	}

	public void setCcusto(String ccusto) {
		this.ccusto = ccusto;
	}

	public Gestor getGestor() {
		return gestor;
	}

	public void setGestor(Gestor gestor) {
		this.gestor = gestor;
	}

}
