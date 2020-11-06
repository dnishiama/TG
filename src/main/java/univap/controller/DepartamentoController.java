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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import univap.model.Departamento;
import univap.model.Gestor;
import univap.repository.DepartamentoRepo;
import univap.repository.GestorRepo;
import univap.service.DepartamentoServiceImpl;
import univap.service.GestorServiceImpl;


@RestController
@RequestMapping("/departamento")
@CrossOrigin 
/**@CrossOrigin Permite que os serviços dessa classe possam ser acessados por aplicações JavaScript hospedadas em outros servidores*/

public class DepartamentoController {

	@Autowired
	private DepartamentoRepo departamentoRepo;
	@Autowired
	private DepartamentoServiceImpl departamentoService;
	
	@Autowired
	private GestorRepo gestorRepo;
	@Autowired
	private GestorServiceImpl gestorService;
	
	/**GET TODOS OS DEPARTAMENTOS*/
	@GetMapping
	@JsonView(View.ViewCompleto.class)
	public List<Departamento> listar() {
		return departamentoRepo.findAll();
	}
	
	/**GET DEPARTAMENTO: PARAMETRO ID*/
	@GetMapping("/{departamentoId}") 
	@JsonView(View.ViewCompleto.class)
	public ResponseEntity<Departamento> buscar(@PathVariable Long departamentoId) { 		
		Optional <Departamento> departamento = departamentoRepo.findById(departamentoId);
		if (departamento.isPresent()) {
			return ResponseEntity.ok(departamento.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	/**GET DEPARTAMENTO: PARAMETRO GESTOR ID*/
	@GetMapping("/gestor/{gestorId}") 
	@JsonView(View.ViewCompleto.class)
	public ResponseEntity <List<Departamento>> buscarPorGestor(@PathVariable Long gestorId) { 		
		Optional<Gestor> gestor = gestorService.buscaPorId(gestorId);
		if (gestor.isPresent()) {
			Gestor gestorEncontrado = gestor.get();
			return ResponseEntity.ok(departamentoRepo.findByGestor(gestorEncontrado));
		}
		return ResponseEntity.notFound().build();
	}
	
	/**GET DEPARTAMENTO: PARAMETRO GESTOR ID*/
	@GetMapping("/gestor/bloco") 
	@JsonView(View.ViewCompleto.class)
	public ResponseEntity <List<Departamento>> buscarPorBlocoEGestor(@RequestParam Long gestorId, String departamentoBloco) { 		
		Optional<Gestor> gestor = gestorService.buscaPorId(gestorId);
		if (gestor.isPresent()) {
			Gestor gestorEncontrado = gestor.get();
			List<Departamento> blocos = departamentoService.buscaPorBlocoEGestor(departamentoBloco, gestorEncontrado);
			if (!blocos.isEmpty()){
				return ResponseEntity.ok(departamentoService.buscaPorBlocoEGestor(departamentoBloco, gestorEncontrado));
			}
			else {
				return ResponseEntity.notFound().build();
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	/**POST DE UM NOVO DEPARTAMENTO*/
	@PostMapping(value = "/cadastrar")
	@JsonView(View.ViewCompleto.class)
	public Departamento cadastrarDepartamento(@Valid @RequestBody DepartamentoDTO departamento) { 
		return departamentoService.novoDepartamento(departamento.getCampus(),
				departamento.getBloco(),
				departamento.getDepartamento(),
				departamento.getCcusto(),
				departamento.getGestor());				
	}

	/**PUT DE UM DEPARTAMENTO: PARAMETRO ID*/
	@PutMapping("/atualizar/{departamentoId}")
	@JsonView(View.ViewCompleto.class)
	public ResponseEntity<Departamento> atualizar(@PathVariable Long departamentoId, @Valid @RequestBody Departamento departamento){
		Optional <Departamento> optionalDepartamento = departamentoRepo.findById(departamentoId);
		if(!optionalDepartamento.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		else {
			Departamento departamentoAtualizado = new Departamento();
			departamentoAtualizado.setId(departamentoId);
			departamentoAtualizado.setCampus(departamento.getCampus());
			departamentoAtualizado.setBloco(departamento.getBloco());
			departamentoAtualizado.setDepartamento(departamento.getDepartamento());
			departamentoAtualizado.setCcusto(departamento.getCcusto());
			departamentoAtualizado.setGestor(departamento.getGestor());
			
			departamento = departamentoRepo.save(departamentoAtualizado);
		}
		return ResponseEntity.ok(departamento);
	}
	
	/**DELETE DE UM DEPARTAMENTO: PARAMETRO ID*/
	@DeleteMapping("/deletar/{departamentoId}")
	public ResponseEntity<Void> remover(@PathVariable Long departamentoId) {
		if (!departamentoRepo.existsById(departamentoId)) {
			return ResponseEntity.notFound().build();
		}
		else {
			departamentoService.excluir(departamentoId);
			return ResponseEntity.noContent().build();
		}
	}
}