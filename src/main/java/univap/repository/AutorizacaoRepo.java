package univap.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import univap.model.Autorizacao;

public interface AutorizacaoRepo extends JpaRepository<Autorizacao, Long> {
	public Autorizacao findByNome(String nome);
}