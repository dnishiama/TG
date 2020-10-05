package univap.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import exception.NegocioException;
import univap.model.Gestor;
import univap.repository.GestorRepo;


@Service
public class GestorServiceImpl {
		
	@Autowired
	private GestorRepo gestorRepo;
	
	@Transactional //Garantir a atomicidade
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Gestor novoGestor(String nome, String email) {
		Gestor gestorExistente = gestorRepo.findByEmail(email);
		if (gestorExistente != null) {
			throw new NegocioException("E-mail já cadastrado");
		}
		else {
			Gestor gestor = new Gestor();
			gestor.setNome(nome);
			gestor.setEmail(email);
			gestorRepo.save(gestor);
			return gestor;
		}	
	}
		
	@Transactional
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Gestor atualizar(Gestor gestor){	
		return gestorRepo.save(gestor);
	}
	
	public Optional<Gestor> buscaPorId(Long gestorId){	
		return gestorRepo.findById(gestorId);
	}
	

	public void excluir(Long gestorId) {
		gestorRepo.deleteById(gestorId);
	}
}

