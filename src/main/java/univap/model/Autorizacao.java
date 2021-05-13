package univap.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import univap.controller.View;

@Entity
@Table(name = "aut_autorizacao")
public class Autorizacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "aut_id")
	@JsonView(View.ViewResumo.class)
	private Long id;

	@Column(name = "aut_nome", unique = true, length = 20, nullable = false)
	@JsonView(View.ViewResumo.class)
	private String nome;

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
