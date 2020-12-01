package univap.service;

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
	public Impressora novaImpressora(Long patrimonio, String ip, Departamento departamento) throws Exception {
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
				mono =(long) 0;
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
	public Impressora atualiza(Impressora impressora){
		try {
			String ip = impressora.getIp();
			Impressora impressoraExistente = impressoraRepo.findBySerial(Agente.serial(ip));
			if (impressoraExistente != null) {			
				Date hoje = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String check = dateFormat.format(hoje);
				Long mono,color;
				if ((Agente.getContadorMono(ip) > 0) && (Agente.getContadorMono(ip) >= impressoraExistente.getContadorMono())) {
					mono = (long) Agente.getContadorMono(ip);
				}
				else {
					mono = (long) Agente.getContadorMono(ip);
				}
				if ((Agente.getContadorColor(ip)>0) && (impressoraExistente.getContadorColor() <= Agente.getContadorColor(ip))) {
					color= (long) Agente.getContadorColor(ip);
				}
				else {
					color = (long) Agente.getContadorColor(ip);
				}
				//Parametros Recebidos
				impressoraExistente.setPatrimonio(impressora.getPatrimonio());
				impressoraExistente.setIp(ip);
				impressoraExistente.setDepartamento(impressora.getDepartamento());
				impressoraExistente.setFabricante(impressora.getFabricante());
				impressoraExistente.setModelo(impressora.getModelo());
				impressoraExistente.setSerial(impressora.getSerial());
				impressoraExistente.setDepartamento(impressora.getDepartamento());
				//Parametros do agente
				impressoraExistente.setContadorMono(mono);
				if (color >= 0) {
					impressoraExistente.setContadorColor(color);
				}
				else{
					impressoraExistente.setContadorColor(null);
				}			
				impressoraExistente.setUltimoUpdate(check);
				impressoraRepo.save(impressoraExistente);
				System.out.println("Contador: "+ mono);
				return impressoraRepo.save(impressoraExistente);
			}
		}
		catch (Exception e) {
			System.out.println("Impressora Offline: "+ e);
		}
		return null;
	}
	
	public List <Impressora> buscaPorDepartamento(Departamento departamento){	
		return impressoraRepo.findByDepartamento(departamento);
	}
	
	public List<Impressora> listar() {
		return impressoraRepo.findAll();
	}
	
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void excluir(Long impressoraId) {
		impressoraRepo.deleteById(impressoraId);
	}
		
}


	
	
	
	
