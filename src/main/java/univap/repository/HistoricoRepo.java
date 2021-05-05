package univap.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import univap.model.Historico;
import univap.controller.RateioDTO;


public interface HistoricoRepo extends JpaRepository<Historico, Long>{
	public Historico findByPatrimonioAndMesAndAno(Long patrimonio, Long mes, Long ano);
	
	List<Historico> findByMesAndAno(Long mes, Long ano);
	
	@Query("select h.impressora.departamento.ccusto as ccusto, sum(h.producaoMono) as contadorMono, sum(h.producaoColor) as contadorColor  "
			+ "from Historico as h "
			+ "where h.mes = :mes and h.ano = :ano "
			+ "group by ccusto")
	public List<Object> rateio(@Param("mes") Long mes, @Param("ano") Long ano);
	
	
	/**@Query("select new univap.controller.RateioDTO(h.impressora.departamento.ccusto, sum(h.producaoMono), sum(h.producaoColor)) "
			+ "from Historico as h where h.mes = :mes and h.ano = :ano group by h.impressora.departamento.ccusto")	
	public List<RateioDTO> rateiodto(@Param("mes") Long mes, @Param("ano") Long ano);
	**/


}
