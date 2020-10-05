package univap.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import univap.model.Usuario;

public interface UsuarioRepo extends JpaRepository<Usuario, Long>{
	public Usuario findTop1ByNomeOrEmail(String nome, String email);
	
	Usuario findByEmail(String email);	
}