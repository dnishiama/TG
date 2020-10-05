package univap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import univap.model.Departamento;
import univap.model.Gestor;

@Repository
public interface DepartamentoRepo extends JpaRepository<Departamento, Long> {
	public Departamento findTop1ByCampusAndBlocoAndDepartamento(String campus, String bloco, String departamento);
	
	List<Departamento> findByCampus(String campus);
	List<Departamento> findByDepartamento(String departamento);	
	List<Departamento> findByBlocoAndGestor(String bloco, Gestor gestor);	
	List<Departamento> findByGestor(Gestor gestor);
}