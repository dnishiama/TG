package univap.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import univap.model.Gestor;
import univap.model.Usuario;
import univap.repository.UsuarioRepo;
import univap.service.UsuarioServiceImpl;


@RestController
@RequestMapping(value = "/usuario")
@CrossOrigin 

public class UsuarioController {

	@Autowired
    private UsuarioServiceImpl usuarioService;	
	@Autowired
    private UsuarioRepo usuarioRepo;
	
	/**GET TODOS OS USUARIOS*/
	@GetMapping 
	public List<Usuario> listar() {
		return usuarioRepo.findAll();
	}
    
	/**POST DE NOVOS USUARIOS*/
	@PostMapping(value = "/cadastrar")
    public Usuario cadastrarUsuario(@RequestBody UsuarioDTO usuario) {
        return usuarioService.novoUsuario(usuario.getNome(), 
                usuario.getEmail(), 
                usuario.getSenha(),
                usuario.getAutorizacao());
    }
	
	/**PUT DE UPDATE DE UM USUARIO*/
	@PutMapping("/atualizar/{usuarioId}")
	public ResponseEntity<Usuario> atualizar(@PathVariable Long usuarioId, @Valid @RequestBody Usuario usuario) {
		Optional <Usuario> optionalGestor = usuarioRepo.findById(usuarioId);
		if (!optionalGestor.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		else {
			Usuario usuarioAtualizado = new Usuario();
			usuarioAtualizado = optionalGestor.get();			
			usuarioAtualizado.setNome(usuario.getNome());
			usuarioAtualizado.setEmail(usuario.getEmail());			
			usuario = usuarioService.atualizar(usuarioAtualizado);
		}
		return ResponseEntity.ok(usuario);
	}
	
	/**DELETE DE UM USUARIO: PARAMETRO ID*/
	@DeleteMapping("/deletar/{usuarioId}")
	public ResponseEntity<Void> remover(@PathVariable Long usuarioId) {
		if (!usuarioRepo.existsById(usuarioId)) {
			System.out.println("Não encontrado!");
			return ResponseEntity.notFound().build();
		}
		else {
			usuarioService.excluir(usuarioId);
			return ResponseEntity.noContent().build();
		}
	}
	
}
