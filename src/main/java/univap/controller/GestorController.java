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

import univap.dto.GestorDTO;
import univap.model.Gestor;
import univap.repository.GestorRepo;
import univap.service.GestorServiceImpl;

@RestController
@RequestMapping("/gestor")
@CrossOrigin

public class GestorController {

	@Autowired
	private GestorRepo repository;

	@Autowired
	private GestorServiceImpl service;

	@GetMapping
	@JsonView(View.ViewCompleto.class)
	public List<Gestor> listAllGestor() {
		return repository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
	}

	@GetMapping("/{gestorId}")
	@JsonView(View.ViewCompleto.class)
	public ResponseEntity<Gestor> searchGestorById(@PathVariable Long gestorId) {
		Optional<Gestor> gestor = repository.findById(gestorId);
		if (gestor.isPresent()) {
			return ResponseEntity.ok(gestor.get());
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/email/{gestorEmail}")
	@JsonView(View.ViewCompleto.class)
	public ResponseEntity<Gestor> searchGestorByEmail(@PathVariable String gestorEmail) {
		Gestor gestor = repository.findByEmail(gestorEmail);
		if (!gestor.getEmail().isEmpty()) {
			return ResponseEntity.ok(gestor);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping(value = "/cadastrar")
	public Gestor postNewGestor(@Valid @RequestBody GestorDTO gestor) {
		return service.novoGestor(gestor.getNome(), gestor.getEmail());
	}

	/** PUT DE UPDATE DE UM GESTOR */
	@PutMapping("/atualizar/{gestorId}")
	public ResponseEntity<Gestor> updateGestorById(@PathVariable Long gestorId, @Valid @RequestBody Gestor gestor) {
		Optional<Gestor> optionalGestor = repository.findById(gestorId);
		if (!optionalGestor.isPresent()) {
			return ResponseEntity.notFound().build();
		} else {
			Gestor gestorAtualizado = new Gestor();
			gestorAtualizado = optionalGestor.get();
			gestorAtualizado.setNome(gestor.getNome());
			gestorAtualizado.setEmail(gestor.getEmail());
			gestor = service.atualizar(gestorAtualizado);
		}
		return ResponseEntity.ok(gestor);
	}

	/** DELETE DE UM GESTOR: PARAMETRO ID */
	@DeleteMapping("/deletar/{gestorId}")
	public ResponseEntity<Void> deleteGestorById(@PathVariable Long gestorId) {
		if (!repository.existsById(gestorId)) {
			System.out.println("Não encontrado!");
			return ResponseEntity.notFound().build();
		} else {
			service.excluir(gestorId);
			return ResponseEntity.noContent().build();
		}
	}
}