package univap.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

import com.fasterxml.jackson.annotation.JsonView;

import univap.model.Gestor;
import univap.repository.GestorRepo;
import univap.service.GestorServiceImpl;


@RestController 
@RequestMapping("/gestor") 
@CrossOrigin 
/**@CrossOrigin Permite que os serviços dessa classe possam ser acessados por aplicações JavaScript hospedadas em outros servidores*/

public class GestorController {
	
	@Autowired
	private GestorRepo gestorRepo;	
	
	@Autowired
	private GestorServiceImpl gestorService;
	
	/**GET TODOS OS GESTORES*/
	@GetMapping
	@JsonView(View.ViewCompleto.class)
	public List<Gestor> listar() {
		return gestorRepo.findAll(Sort.by(Sort.Direction.ASC, "nome"));
	}
	
	/**GET GESTOR: PARAMETRO ID*/
	@GetMapping("/{gestorId}") 
	@JsonView(View.ViewCompleto.class)
	public ResponseEntity<Gestor> buscar(@PathVariable Long gestorId) { 
		Optional <Gestor> gestor = gestorRepo.findById(gestorId);
		if (gestor.isPresent()) {
			return ResponseEntity.ok(gestor.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	/**GET GESTOR: PARAMETRO EMAIL*/
	@GetMapping("/email/{gestorEmail}") 
	@JsonView(View.ViewCompleto.class)
	public ResponseEntity<Gestor> buscar(@PathVariable String gestorEmail) { 
		Gestor gestor = gestorRepo.findByEmail(gestorEmail);
		if (!gestor.getEmail().isEmpty()) {
			return ResponseEntity.ok(gestor);
		}
		return ResponseEntity.notFound().build();
	}
	
	
	/**POST DE UM NOVO GESTOR*/
	@PostMapping(value = "/cadastrar")
	public Gestor cadastrarGestor(@Valid @RequestBody GestorDTO gestor) {
		return gestorService.novoGestor(gestor.getNome(),
				gestor.getEmail());
	}	
	
	/**PUT DE UPDATE DE UM GESTOR*/
	@PutMapping("/atualizar/{gestorId}")
	public ResponseEntity<Gestor> atualizar(@PathVariable Long gestorId, @Valid @RequestBody Gestor gestor) {
		Optional <Gestor> optionalGestor = gestorRepo.findById(gestorId);
		if (!optionalGestor.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		else {
			Gestor gestorAtualizado = new Gestor();
			gestorAtualizado = optionalGestor.get();			
			gestorAtualizado.setNome(gestor.getNome());
			gestorAtualizado.setEmail(gestor.getEmail());			
			gestor = gestorService.atualizar(gestorAtualizado);
		}
		return ResponseEntity.ok(gestor);
	}
	
	/**DELETE DE UM GESTOR: PARAMETRO ID*/
	@DeleteMapping("/deletar/{gestorId}")
	public ResponseEntity<Void> remover(@PathVariable Long gestorId) {
		if (!gestorRepo.existsById(gestorId)) {
			System.out.println("Não encontrado!");
			return ResponseEntity.notFound().build();
		}
		else {
			gestorService.excluir(gestorId);
			return ResponseEntity.noContent().build();
		}
	}
}