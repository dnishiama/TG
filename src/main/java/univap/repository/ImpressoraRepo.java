package univap.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.annotation.JsonView;

import univap.controller.View;
import univap.model.Departamento;
import univap.model.Impressora;

public interface ImpressoraRepo extends JpaRepository<Impressora, Long> {
	public Impressora findByPatrimonio(Long patrimonio);
	public Impressora findBySerial(String serial);	
	List<Impressora> findByDepartamento(Departamento departamento);	
	
}
