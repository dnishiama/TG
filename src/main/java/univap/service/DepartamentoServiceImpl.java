package univap.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import exception.NegocioException;
import univap.model.Departamento;
import univap.model.Gestor;
import univap.repository.DepartamentoRepo;



@Service
public class DepartamentoServiceImpl {
	@Autowired
	private DepartamentoRepo departamentoRepo;
	
	@Transactional /**Garantir a atomicidade*/
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Departamento novoDepartamento(String campus, String bloco, String departamento, String ccusto, Gestor gestor) {
		Departamento departamentoExistente = departamentoRepo.findTop1ByCampusAndBlocoAndDepartamento(campus, bloco, departamento);
		if (departamentoExistente != null) {
			throw new NegocioException("Departamento já cadastrado");
		}
		else {
			Departamento departamentoNovo = new Departamento();
			departamentoNovo.setCampus(campus);
			departamentoNovo.setBloco(bloco);
			departamentoNovo.setDepartamento(departamento);
			departamentoNovo.setCcusto(ccusto);
			departamentoNovo.setGestor(gestor);
			departamentoRepo.save(departamentoNovo);
			return departamentoNovo;
		}
	}
	
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Departamento atualizar(Departamento departamento){	
		return departamentoRepo.save(departamento);
	}
	
	public List <Departamento> buscaPorGestorId(Gestor gestor){	
		return departamentoRepo.findByGestor(gestor);
	}
	
	public List <Departamento> buscaPorBlocoEGestor(String bloco, Gestor gestor){	
		return departamentoRepo.findByBlocoAndGestor(bloco, gestor);
	}
	
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void excluir(Long departamentoId) {
		departamentoRepo.deleteById(departamentoId);
	}
}
