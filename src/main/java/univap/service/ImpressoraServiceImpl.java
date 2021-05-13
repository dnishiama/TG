package univap.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exception.NegocioException;
import univap.agent.Agente;
import univap.model.Departamento;
import univap.model.Impressora;
import univap.repository.ImpressoraRepo;

@Service
public class ImpressoraServiceImpl {

	//------------------------------------------------------ Injeções  ------------------------------------------------------
	@Autowired
	private ImpressoraRepo impressoraRepo;
	@Autowired
	private Agente agente;
	
	//-------------------------------------------------- Métodos de Adição ---------------------------------------------------

	@Transactional /**Garantir a atomicidade*/
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Impressora novaImpressora(Long patrimonio, String ip, Departamento departamento) throws Exception {
		try {
			Impressora impressoraExistente = impressoraRepo.findBySerial(agente.serial(ip));
			if (impressoraExistente != null) {
				throw new NegocioException("Impressora já cadastrada");
			}
			else {			
				Date hoje = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String check = dateFormat.format(hoje);
				Long mono,color; 
				String fabricante, modelo, serial;
				fabricante = modelo = serial = "";
				
				System.out.println("Contador do IP " + ip + " ...");
				System.out.println("Contador mono: "+ agente.getContadorMono(ip));
				if (agente.getContadorMono(ip)>0) {
					mono = (long) agente.getContadorMono(ip);
				}
				else {
					mono =(long) 0;
				}
				System.out.println("Mono: "+ mono);
				
				color = null;
				if(agente.getModelo(ip).equals("MC780") || 
						agente.getModelo(ip).equals("ES8473") || 
						agente.getModelo(ip).equals("WF-5690"))
				{
					if (agente.getContadorColor(ip)>0) {
						color= (long)agente.getContadorColor(ip);
						System.out.println("Color: "+ color);
					}
				}
				
				if(!agente.fabricante(ip).isBlank()) {
					fabricante = agente.fabricante(ip);
					modelo = agente.getModelo(ip);
					serial = agente.serial(ip);
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
				impressoraNova.setContadorColor(color);			
				impressoraNova.setUltimoUpdate(check);
				impressoraRepo.save(impressoraNova);
				
				System.out.println("Contador mono: "+ mono);
				System.out.println("Contador color: "+ color);
				return impressoraNova;
			}
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	public Impressora novaImpressoraOffline(
			Long patrimonio, 
			String ip, 
			String fabricante, 
			String modelo, 
			String serial,
			Long mono,
			Long color,
			Departamento departamento) throws Exception {
		
		Impressora impressoraExistente = impressoraRepo.findBySerial(serial);
		if (impressoraExistente != null) {
			throw new NegocioException("Impressora já cadastrado");
		}
		else {			
			Date hoje = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String check = dateFormat.format(hoje); 

			Impressora impressoraNova = new Impressora();
			
			
			//TESTAR ISSO:
			if ((mono<0)||(color<0))
			{
				throw new NegocioException("Contadores incompativeis.");
			}
			
			//Parametros Recebidos
			impressoraNova.setPatrimonio(patrimonio);
			impressoraNova.setIp(ip);
			impressoraNova.setDepartamento(departamento);
			impressoraNova.setFabricante(fabricante);
			impressoraNova.setModelo(modelo);
			impressoraNova.setSerial(serial);			
			impressoraNova.setContadorMono(mono);
			impressoraNova.setContadorColor(color);
			impressoraNova.setUltimoUpdate(check);
			
			impressoraRepo.save(impressoraNova);
			return impressoraNova;
		}
	}
	
	//----------------------------------------------- Métodos de Atualização -------------------------------------------------
	
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Impressora atualiza(Impressora impressora){
		try {
			String ip = impressora.getIp();
			if(ip.equals("USB")) {
				throw new NegocioException("Impressora USB");
			}
			System.out.println(agente.serial(ip));
			Impressora impressoraExistente = impressoraRepo.findBySerial(agente.serial(ip));
			System.out.println(impressoraExistente.getFabricante() + " " + impressoraExistente.getModelo());
			System.out.println(impressora.getFabricante() + " " + impressora.getModelo());
			if (	(impressoraExistente != null) && 
					(impressoraExistente.getSerial().equals(impressora.getSerial()) ) && 
					(impressoraExistente.getModelo().equals(impressora.getModelo())) ) {				
				Date hoje = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String check = dateFormat.format(hoje);
				Long mono,color;
				
				//Contador mono
				if ((agente.getContadorMono(ip) > 0) && (agente.getContadorMono(ip) >= impressoraExistente.getContadorMono())) {
					mono = (long) agente.getContadorMono(ip);
				}
				else {
					mono = (long) agente.getContadorMono(ip);
				}
				
				//Contador color
				color = null;
				if(agente.getModelo(ip).equals("MC780") || 
						agente.getModelo(ip).equals("ES8473") || 
						agente.getModelo(ip).equals("WF-5690"))
				{
					if ((agente.getContadorColor(ip) >= 0) && 
							(impressoraExistente.getContadorColor() <= agente.getContadorColor(ip)) ) {
						color= (long) agente.getContadorColor(ip);
					}
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
				impressoraExistente.setContadorColor(color);			
		
				impressoraExistente.setUltimoUpdate(check);
				impressoraRepo.save(impressoraExistente);
				
				System.out.println("----------------------------------------------------");
				System.out.println("");
				
				return impressoraExistente;
			}
			else {
				throw new NegocioException("Dados divergentes");
			}
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	//------------------------------------------------ Métodos de Listagem ---------------------------------------------------
	
	public List <Impressora> buscaPorDepartamento(Departamento departamento){	
		return impressoraRepo.findByDepartamento(departamento);
	}
	
	public List<Impressora> listar() {
		return impressoraRepo.findAll();
	}
	
	//------------------------------------------------ Métodos de Exclusão ---------------------------------------------------
	
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void excluir(Long impressoraId) {
		impressoraRepo.deleteById(impressoraId);
	}
		
}


	
	
	
	
