package univap.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

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
	@ManyToOne anotação indicativa de relação entre tabelas.
*/

@Entity
@Table(name="aut_autorizacao")
public class Autorizacao {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "aut_id")
	@JsonView(View.ViewResumo.class)
	private Long id;
	
	@Column(name = "aut_nome", unique=true, length = 20, nullable = false)
	@JsonView(View.ViewResumo.class)
	private String nome;

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
}
