package univap.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exception.NegocioException;
import univap.model.Departamento;
import univap.model.Gestor;
import univap.repository.DepartamentoRepo;

@Service
public class DepartamentoServiceImpl {
	@Autowired
	private DepartamentoRepo repository;

	@Transactional
	// @PreAuthorize("hasRole('ROLE_ADMIN')")
	public Departamento newDepartamento(String campus, String bloco, String departamento, String ccusto,
			Gestor gestor) {
		Departamento departamentoExistente = repository.findTop1ByCampusAndBlocoAndDepartamento(campus, bloco,
				departamento);
		if (departamentoExistente != null) {
			throw new NegocioException("Departamento já cadastrado");
		} else {
			Departamento departamentoNovo = new Departamento();
			departamentoNovo.setCampus(campus);
			departamentoNovo.setBloco(bloco);
			departamentoNovo.setDepartamento(departamento);
			departamentoNovo.setCcusto(ccusto);
			departamentoNovo.setGestor(gestor);
			repository.save(departamentoNovo);
			return departamentoNovo;
		}
	}

	// @PreAuthorize("hasRole('ROLE_ADMIN')")
	public Departamento updateDepartamento(Departamento departamento) {
		return repository.save(departamento);
	}

	public List<Departamento> searchDepartamentoByGestorId(Gestor gestor) {
		return repository.findByGestor(gestor);
	}

	public List<Departamento> searchDepartamentoByBlocoAndByGestorId(String bloco, Gestor gestor) {
		return repository.findByBlocoAndGestor(bloco, gestor);
	}

	// @PreAuthorize("hasRole('ROLE_ADMIN')")
	public void DeleteDepartamento(Long departamentoId) {
		repository.deleteById(departamentoId);
	}
}
