package univap.dto;

import java.io.Serializable;

public class RateioDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String ccusto;
	private Long contadorMono;
	private Long contadorColor;

	public String getCcusto() {
		return ccusto;
	}

	public void setCcusto(String ccusto) {
		this.ccusto = ccusto;
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

	public RateioDTO(String custo, Long mono, Long color) {
		this.ccusto = custo;
		this.contadorMono = mono;
		this.contadorColor = color;
	}
}