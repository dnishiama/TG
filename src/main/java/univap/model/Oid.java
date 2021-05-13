package univap.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonView;

import univap.ValidationGroups;
import univap.controller.View;

@Entity
@Table(name = "oid_oids")
public class Oid {
	@NotNull(groups = ValidationGroups.GestorId.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "oid_id")
	@JsonView(View.ViewResumo.class)
	private Long id;

	@NotBlank
	@Size(max = 120)
	@Column(name = "oid_descricao")
	@JsonView(View.ViewResumo.class)
	private String descricao;

	@NotBlank
	@Size(max = 120)
	@Column(name = "oid_fabricante")
	@JsonView(View.ViewResumo.class)
	private String fabricante;

	@NotBlank
	@Size(max = 120)
	@Column(name = "oid_modelo")
	@JsonView(View.ViewResumo.class)
	private String modelo;

	@NotBlank
	@Size(max = 120)
	@Column(name = "oid_serial")
	@JsonView(View.ViewResumo.class)
	private String serial;

	@NotBlank
	@Size(max = 120)
	@Column(name = "oid_mono")
	@JsonView(View.ViewResumo.class)
	private String mono;

	@NotBlank
	@Size(max = 120)
	@Column(name = "oid_color")
	@JsonView(View.ViewResumo.class)
	private String color;

	@NotBlank
	@Size(max = 120)
	@Column(name = "oid_color_mono")
	@JsonView(View.ViewResumo.class)
	private String colorMono;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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

	public String getMono() {
		return mono;
	}

	public void setMono(String mono) {
		this.mono = mono;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getColorMono() {
		return colorMono;
	}

	public void setColorMono(String colorMono) {
		this.colorMono = colorMono;
	}

}
