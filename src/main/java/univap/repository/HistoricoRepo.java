package univap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import univap.model.Departamento;
import univap.model.Gestor;
import univap.model.Historico;
import univap.model.Impressora;

public interface HistoricoRepo extends JpaRepository<Historico, Long>{
	public Historico findByPatrimonioAndMesAndAno(Long patrimonio, Long mes, Long ano);
	
	List<Historico> findByMesAndAno(Long mes, Long ano);
}
