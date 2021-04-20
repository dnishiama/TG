package univap.model;

import java.util.Date;

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

/**
@Entity Anotação de indicação de Entidade
@Table Anotação da tabela do Banco de dados
@NotNull anotação de indicação que o campo não pode ser nulo
@NotBlank anotação de indicação para validação(@valid) que o campo não pode ficar em branco
@Size anotação de indicação do tamanho maximo do campo
@Id anotação de chave primaria
@GeneratedValue(strategy = GenerationType.IDENTITY) anotação de indicação de auto-increment.
@Column(name = "ges_id") anotação do nome da coluna da tabela do Banco de Dados.
@ManyToOne anotação indicativa de relação entre tabelas.
*/

@Entity
@Table(name="his_historico")
public class Historico {
	@NotNull(groups = ValidationGroups.GestorId.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "his_id")
	@JsonView(View.ViewResumo.class)
	private Long id;
	
	@NotNull
	@Column(name = "his_patrimonio")
	@JsonView(View.ViewResumo.class)
	private Long patrimonio;
	
	@Column(name = "his_contador")
	@JsonView(View.ViewResumo.class)
	private Long contadorMono;
	
	@Column(name = "his_producao")
	@JsonView(View.ViewResumo.class)
	private Long producaoMono;
		
	@Column(name = "his_contador_color")
	@JsonView(View.ViewResumo.class)
	private Long contadorColor;
	
	@Column(name = "his_producao_color")
	@JsonView(View.ViewResumo.class)
	private Long producaoColor;
	
	@NotNull
	@Column(name = "his_mes_referencia")
	@JsonView(View.ViewResumo.class)
	private Long mes;
	
	@NotNull
	@Column(name = "his_ano_referencia")
	@JsonView(View.ViewResumo.class)
	private Long ano;
		
	@NotNull
	@Size(max = 30)
	@Column(name = "his_data_insert")
	@JsonView(View.ViewResumo.class)
	private String data;
	
	
	@Valid
	@ConvertGroup(from = Default.class, to = ValidationGroups.GestorId.class)
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "his_imp_id")
	@JsonView(View.ViewResumo.class)
	private Impressora impressora;


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


	public Long getMes() {
		return mes;
	}


	public void setMes(Long mes) {
		this.mes = mes;
	}


	public Long getAno() {
		return ano;
	}


	public void setAno(Long ano) {
		this.ano = ano;
	}


	public String getData() {
		return data;
	}


	public void setData(String data) {
		this.data = data;
	}
	
	public Long getProducaoMono() {
		return producaoMono;
	}


	public void setProducaoMono(Long producaoMono) {
		this.producaoMono = producaoMono;
	}


	public Long getProducaoColor() {
		return producaoColor;
	}


	public void setProducaoColor(Long producaoColor) {
		this.producaoColor = producaoColor;
	}


	public Impressora getImpressora() {
		return impressora;
	}


	public void setImpressora(Impressora impressora) {
		this.impressora = impressora;
	}
	
	

}
