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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import univap.dto.DepartamentoDTO;
import univap.model.Departamento;
import univap.model.Gestor;
import univap.repository.DepartamentoRepo;
import univap.service.DepartamentoServiceImpl;
import univap.service.GestorServiceImpl;

@RestController
@RequestMapping("/departamento")
@CrossOrigin

public class DepartamentoController {

	@Autowired
	private DepartamentoRepo repository;
	@Autowired
	private DepartamentoServiceImpl service;

	@Autowired
	private GestorServiceImpl gestorService;

	@GetMapping
	@JsonView(View.ViewCompleto.class)
	public List<Departamento> listAllDepartamento() {
		return repository.findAll(Sort.by(Sort.Direction.ASC, "campus", "bloco", "departamento"));
	}

	@GetMapping("/{departamentoId}")
	@JsonView(View.ViewCompleto.class)
	public ResponseEntity<Departamento> searchDepartamentoByDepartmentoId(@PathVariable Long departamentoId) {
		Optional<Departamento> departamento = repository.findById(departamentoId);
		if (departamento.isPresent()) {
			return ResponseEntity.ok(departamento.get());
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/gestor/{gestorId}")
	@JsonView(View.ViewCompleto.class)
	public ResponseEntity<List<Departamento>> searchDepartamentoByGestorId(@PathVariable Long gestorId) {
		Optional<Gestor> gestor = gestorService.buscaPorId(gestorId);
		if (gestor.isPresent()) {
			Gestor gestorEncontrado = gestor.get();
			return ResponseEntity.ok(repository.findByGestor(gestorEncontrado));
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/gestor/bloco")
	@JsonView(View.ViewCompleto.class)
	public ResponseEntity<List<Departamento>> searchDepartamentoByGestorAndBloco(@RequestParam Long gestorId,
			String departamentoBloco) {
		Optional<Gestor> gestor = gestorService.buscaPorId(gestorId);
		if (gestor.isPresent()) {
			Gestor gestorEncontrado = gestor.get();
			List<Departamento> blocos = service.searchDepartamentoByBlocoAndByGestorId(departamentoBloco, gestorEncontrado);
			if (!blocos.isEmpty()) {
				return ResponseEntity.ok(service.searchDepartamentoByBlocoAndByGestorId(departamentoBloco, gestorEncontrado));
			} else {
				return ResponseEntity.notFound().build();
			}
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping(value = "/cadastrar")
	@JsonView(View.ViewCompleto.class)
	public Departamento postNewDepartamento(@Valid @RequestBody DepartamentoDTO departamento) {
		return service.newDepartamento(departamento.getCampus(), departamento.getBloco(),
				departamento.getDepartamento(), departamento.getCcusto(), departamento.getGestor());
	}

	@PutMapping("/atualizar/{departamentoId}")
	@JsonView(View.ViewCompleto.class)
	public ResponseEntity<Departamento> updateDepartmentoById(@PathVariable Long departamentoId,
			@Valid @RequestBody Departamento departamento) {
		Optional<Departamento> optionalDepartamento = repository.findById(departamentoId);
		if (!optionalDepartamento.isPresent()) {
			return ResponseEntity.notFound().build();
		} else {
			Departamento departamentoAtualizado = new Departamento();
			departamentoAtualizado.setId(departamentoId);
			departamentoAtualizado.setCampus(departamento.getCampus());
			departamentoAtualizado.setBloco(departamento.getBloco());
			departamentoAtualizado.setDepartamento(departamento.getDepartamento());
			departamentoAtualizado.setCcusto(departamento.getCcusto());
			departamentoAtualizado.setGestor(departamento.getGestor());

			departamento = repository.save(departamentoAtualizado);
		}
		return ResponseEntity.ok(departamento);
	}

	@DeleteMapping("/deletar/{departamentoId}")
	public ResponseEntity<Void> deleteDepartamentoById(@PathVariable Long departamentoId) {
		if (!repository.existsById(departamentoId)) {
			return ResponseEntity.notFound().build();
		} else {
			service.DeleteDepartamento(departamentoId);
			return ResponseEntity.noContent().build();
		}
	}
}