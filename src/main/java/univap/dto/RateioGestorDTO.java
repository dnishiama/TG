package univap.dto;

import java.io.Serializable;

public class RateioGestorDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nome;
	private String email;
	private Long contadorMono;
	private Long contadorColor;
	
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

	public RateioGestorDTO(String nome, String email, Long contadorMono, Long contadorColor) {
		this.nome = nome;
		this.email = email;
		this.contadorMono = contadorMono;
		if (contadorColor == null) {
			this.contadorColor = 0L;
		}
		else {
			this.contadorColor = contadorColor;
		}
		
	}
	
	public RateioGestorDTO() {
	}
		
}