package univap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import univap.model.Departamento;
import univap.model.Impressora;

public interface ImpressoraRepo extends JpaRepository<Impressora, Long> {
	public Impressora findByPatrimonio(Long patrimonio);

	public Impressora findBySerial(String serial);

	public List<Impressora> findByDepartamento(Departamento departamento);

}
