package univap.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import univap.model.Usuario;
import univap.repository.UsuarioRepo;
import univap.service.UsuarioService;


@RestController
@RequestMapping(value = "/usuario")
@CrossOrigin 

public class UsuarioController {

	@Autowired
    private UsuarioService usuarioService;	
	@Autowired
    private UsuarioRepo usuarioRepo;
	
	/**GET TODOS OS USUARIOS*/
	@GetMapping 
	public List<Usuario> listar() {
		return usuarioRepo.findAll();
	}
    
	/**POST DE NOVOS USUARIOS*/
	@PostMapping(value = "/cadastro")
    public Usuario cadastrarUsuario(@RequestBody UsuarioDTO usuario) {
        return usuarioService.novoUsuario(usuario.getNome(), 
                usuario.getEmail(), 
                usuario.getSenha(),
                usuario.getAutorizacao());
    }
}
