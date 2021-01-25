package univap.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import univap.model.Oid;

public interface OidRepo extends JpaRepository<Oid, Long>  {
	public Oid findByDescricao(String descricao);	
	
}
