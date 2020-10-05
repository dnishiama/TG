package univap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import univap.model.Gestor;

@Repository
public interface GestorRepo extends JpaRepository<Gestor, Long> {
	public Gestor findTop1ByNomeOrEmail(String nome, String email);
	
	List<Gestor> findByNome(String nome);
	List<Gestor> findByNomeContaining(String nome);	
	Gestor findByEmail(String email);

}