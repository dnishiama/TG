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

import univap.dto.UsuarioDTO;
import univap.model.Usuario;
import univap.repository.UsuarioRepo;
import univap.service.UsuarioServiceImpl;

@RestController
@RequestMapping(value = "/usuario")
@CrossOrigin
public class UsuarioController {

	@Autowired
	private UsuarioServiceImpl service;

	@Autowired
	private UsuarioRepo repository;

	@GetMapping
	public List<Usuario> listAll() {
		return repository.findAll();
	}

	@PostMapping(value = "/cadastrar")
	public Usuario postNewUsuario(@RequestBody UsuarioDTO usuario) {
		return service.novoUsuario(usuario.getNome(), usuario.getEmail(), usuario.getSenha(), usuario.getAutorizacao());
	}

	@PutMapping("/atualizar/{usuarioId}")
	public ResponseEntity<Usuario> updateById(@PathVariable Long usuarioId, @Valid @RequestBody Usuario usuario) {
		Optional<Usuario> optionalGestor = repository.findById(usuarioId);
		if (!optionalGestor.isPresent()) {
			return ResponseEntity.notFound().build();
		} else {
			Usuario usuarioAtualizado = new Usuario();
			usuarioAtualizado = optionalGestor.get();
			usuarioAtualizado.setNome(usuario.getNome());
			usuarioAtualizado.setEmail(usuario.getEmail());
			usuario = service.atualizar(usuarioAtualizado);
		}
		return ResponseEntity.ok(usuario);
	}

	@DeleteMapping("/deletar/{usuarioId}")
	public ResponseEntity<Void> deleteById(@PathVariable Long usuarioId) {
		if (!repository.existsById(usuarioId)) {
			System.out.println("Não encontrado!");
			return ResponseEntity.notFound().build();
		} else {
			service.excluir(usuarioId);
			return ResponseEntity.noContent().build();
		}
	}

}
