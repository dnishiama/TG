package univap.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonView;

import univap.ValidationGroups;
import univap.controller.View;


/**
	@Entity Anotação de indicação de Entidade
	@Table Anotação da tabela do Banco de dados
	@NotNull anotação de indicação que o campo não pode ser nulo
	@NotBlank anotação de indicação para validação(@valid) que o campo não pode ficar em branco
	@Email anotação de indicação para validação(@valid) que o campo é um email
	@Size anotação de indicação do tamanho maximo do campo
	@Id anotação de chave primaria
	@GeneratedValue(strategy = GenerationType.IDENTITY) anotação de indicação de auto-increment.
	@Column(name = "ges_id") anotação do nome da coluna da tabela do Banco de Dados.
	@ManyToMany e @OneToMany anotações indicativas de relação entre tabelas.
*/

@Entity
@Table(name="ges_gestor")
public class Gestor {
	
	@NotNull(groups = ValidationGroups.GestorId.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ges_id")
	@JsonView(View.ViewResumo.class)
	private Long id;
		
	@NotBlank
	@Size(max = 100)
	@Column(name = "ges_nome")
	@JsonView(View.ViewResumo.class)
	private String nome;
		
	@NotBlank
	@Email
	@Size(max = 255)
	@Column(name = "ges_email")
	@JsonView(View.ViewCompleto.class)
	private String email;
		
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "departamento")
	private Set<Departamento> departamentos;

	//Getters and Setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
