package univap.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import univap.model.Usuario;

public interface UsuarioService extends UserDetailsService {
	
	public Usuario novoUsuario(String nome, String email, String senha, String autorizacao);

}
