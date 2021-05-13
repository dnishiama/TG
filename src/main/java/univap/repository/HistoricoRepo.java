package univap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import univap.dto.RateioDTO;
import univap.dto.RateioGestorDTO;
import univap.model.Historico;

public interface HistoricoRepo extends JpaRepository<Historico, Long> {

	public Historico findByPatrimonioAndMesAndAno(Long patrimonio, Long mes, Long ano);

	public List<Historico> findByMesAndAno(Long mes, Long ano);

	@Query("select NEW univap.dto.RateioDTO(impressora.departamento.ccusto, sum(producaoMono), sum(producaoColor)) "
			+ "from Historico where mes = ?1 and ano = ?2 group by impressora.departamento.ccusto")
	List<RateioDTO> rateio(Long mes, Long ano);
	
	@Query("select NEW univap.dto.RateioGestorDTO(impressora.departamento.gestor.nome, impressora.departamento.gestor.email, sum(producaoMono), sum(producaoColor)) "
			+ "from Historico where mes = ?1 and ano = ?2 group by impressora.departamento.gestor.email")
	List<RateioGestorDTO> rateioByGestor(Long mes, Long ano);

}
