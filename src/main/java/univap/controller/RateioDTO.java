package univap.controller;

public class RateioDTO {
	
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
	
	public RateioDTO(String ccusto, Long contadorMono, Long contadorColor) {
		this.ccusto = ccusto;
		this.contadorMono = contadorMono;
		this.contadorColor = contadorColor;
	}	
}
