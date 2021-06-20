package univap.dto;

import java.util.Date;

import univap.model.Departamento;

public class ImpressoraOfflineDTO {
	
	private Long id;
	private Long patrimonio; 
	private String ip;
	private String fabricante;
	private String modelo;
	private String serial;
	private Long contadorMono;
	private Long contadorColor;
	private Departamento departamento;
	
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
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
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
	public Departamento getDepartamento() {
		return departamento;
	}
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	
	public ImpressoraOfflineDTO(Long id, Long patrimonio, String ip, String fabricante, String modelo, String serial,
			Long contadorMono, Long contadorColor, Departamento departamento) {
		super();
		this.id = id;
		this.patrimonio = patrimonio;
		this.ip = ip;
		this.fabricante = fabricante;
		this.modelo = modelo;
		this.serial = serial;
		this.contadorMono = contadorMono;
		this.contadorColor = contadorColor;
		this.departamento = departamento;
	}

	

}
