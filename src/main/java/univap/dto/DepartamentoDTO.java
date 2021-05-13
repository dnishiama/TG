package univap.dto;

import univap.model.Gestor;

public class DepartamentoDTO {
	private String campus;
	private String bloco;
	private String departamento;
	private String ccusto;
	private Gestor gestor;

	public String getCampus() {
		return campus;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}

	public String getBloco() {
		return bloco;
	}

	public void setBloco(String bloco) {
		this.bloco = bloco;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getCcusto() {
		return ccusto;
	}

	public void setCcusto(String ccusto) {
		this.ccusto = ccusto;
	}

	public Gestor getGestor() {
		return gestor;
	}

	public void setGestor(Gestor gestor) {
		this.gestor = gestor;
	}
}