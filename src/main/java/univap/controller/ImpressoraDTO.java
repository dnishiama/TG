package univap.controller;


import java.util.Date;

import univap.model.Departamento;

public class ImpressoraDTO {
	//Classe Data Transfer Object 

	private Long patrimonio;	
	private String fabricante;	
	private String modelo;
	private String serial;
	private String ip;
	private Long contadorMono;
	private Long contadorColor;
	private Date ultimoUpdate;
	private Departamento departamento;
	
	public Long getPatrimonio() {
		return patrimonio;
	}
	public void setPatrimonio(Long patrimonio) {
		this.patrimonio = patrimonio;
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
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
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
	public Date getUltimoUpdate() {
		return ultimoUpdate;
	}
	public void setUltimoUpdate(Date ultimoUpdate) {
		this.ultimoUpdate = ultimoUpdate;
	}
	public Departamento getDepartamento() {
		return departamento;
	}
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	
	
}
