package univap.dto;

public class HistoricoDTO {
	private Long patrimonio;
	private Long contadorMono;
	private Long contadorColor;
	private Long producaoMono;
	private Long producaoColor;
	private Long mesReferencia;
	private Long anoReferencia;

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

	public Long getMesReferencia() {
		return mesReferencia;
	}

	public void setMesReferencia(Long mesReferencia) {
		this.mesReferencia = mesReferencia;
	}

	public Long getAnoReferencia() {
		return anoReferencia;
	}

	public void setAnoReferencia(Long anoReferencia) {
		this.anoReferencia = anoReferencia;
	}
}
