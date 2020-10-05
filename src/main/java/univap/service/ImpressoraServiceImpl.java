package univap.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import exception.NegocioException;
import univap.model.Impressora;
import univap.agent.Agente;
import univap.model.Departamento;
import univap.repository.ImpressoraRepo;

@Service
public class ImpressoraServiceImpl {
	@Autowired
	private ImpressoraRepo impressoraRepo;
	
	@Transactional /**Garantir a atomicidade*/
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Impressora novaImpressora(Long patrimonio, String ip, Departamento departamento) throws IOException {
		Impressora impressoraExistente = impressoraRepo.findBySerial(Agente.serial(ip));
		if (impressoraExistente != null) {
			throw new NegocioException("Impressora já cadastrado");
		}
		else {			
			Date hoje = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String check = dateFormat.format(hoje);
			Long mono,color; 
			String fabricante, modelo, serial;
			fabricante = modelo = serial = "";
			
			if (Agente.getContadorMono(ip)>0) {
				mono = (long) Agente.getContadorMono(ip);
			}
			else {
				mono =(long) -1;
			}
			if (Agente.getContadorColor(ip)>0) {
				color= (long)Agente.getContadorColor(ip);
			}
			else {
				color =(long) -1;
			}
			if(!Agente.fabricante(ip).isBlank()) {
				fabricante = Agente.fabricante(ip);
				modelo = Agente.modelo(ip);
				serial = Agente.serial(ip);
			}
			Impressora impressoraNova = new Impressora();
			
			//Parametros Recebidos
			impressoraNova.setPatrimonio(patrimonio);
			impressoraNova.setIp(ip);
			impressoraNova.setDepartamento(departamento);
			
			//Parametros do agente
			impressoraNova.setFabricante(fabricante);
			impressoraNova.setModelo(modelo);
			impressoraNova.setSerial(serial);			
			impressoraNova.setContadorMono(mono);
			if (color >= 0) {
				impressoraNova.setContadorColor(color);
			}
			else{
				impressoraNova.setContadorColor(null);
			}			
			impressoraNova.setUltimoUpdate(check);
			impressoraRepo.save(impressoraNova);
			System.out.println("Contador: "+ mono);
			return impressoraNova;
		}
	}
	
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Impressora atualizar(Impressora impressora){	
		return impressoraRepo.save(impressora);
	}
	
	public List <Impressora> buscaPorDepartamento(Departamento departamento){	
		return impressoraRepo.findByDepartamento(departamento);
	}
	
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void excluir(Long impressoraId) {
		impressoraRepo.deleteById(impressoraId);
	}
		
}


	
	
	
	
