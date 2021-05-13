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
@Table(name = "imp_impressora")
public class Impressora {
	@NotNull(groups = ValidationGroups.GestorId.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "imp_id")
	@JsonView(View.ViewResumo.class)
	private Long id;

	@Column(name = "imp_patrimonio")
	@JsonView(View.ViewResumo.class)
	private Long patrimonio;

	@NotBlank
	@Size(max = 80)
	@Column(name = "imp_fabricante")
	@JsonView(View.ViewResumo.class)
	private String fabricante;

	@NotBlank
	@Size(max = 30)
	@Column(name = "imp_modelo")
	@JsonView(View.ViewResumo.class)
	private String modelo;

	@NotBlank
	@Size(max = 40)
	@Column(name = "imp_serie")
	@JsonView(View.ViewResumo.class)
	private String serial;

	@NotBlank
	@Size(max = 30)
	@Column(name = "imp_conexao")
	@JsonView(View.ViewResumo.class)
	private String ip;

	@Column(name = "imp_ultimo_contador")
	@JsonView(View.ViewResumo.class)
	private Long contadorMono;

	@Column(name = "imp_ultimo_contador_color")
	@JsonView(View.ViewResumo.class)
	private Long contadorColor;

	@Size(max = 30)
	@Column(name = "imp_ultimo_update")
	@JsonView(View.ViewResumo.class)
	private String ultimoUpdate;

	@Valid
	@ConvertGroup(from = Default.class, to = ValidationGroups.GestorId.class)
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "imp_dep_id")
	@JsonView(View.ViewResumo.class)
	private Departamento departamento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPatrimonio() {
		return patrimonio;
	}

	public void setPatrimonio(Long patrimonio) {
		this.patrimonio = patrimonio;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Long getContadorMono() {
		return contadorMono;
	}

	public void setContadorMono(Long contadorMono) {
		this.contadorMono = contadorMono;
	}

	public Long getContadorColor() {
		return contadorColor;
	}

	public void setContadorColor(Long contadorColor) {
		this.contadorColor = contadorColor;
	}

	public String getUltimoUpdate() {
		return ultimoUpdate;
	}

	public void setUltimoUpdate(String ultimoUpdate) {
		this.ultimoUpdate = ultimoUpdate;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

}
