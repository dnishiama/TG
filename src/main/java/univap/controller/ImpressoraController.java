package univap.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
 

import com.fasterxml.jackson.annotation.JsonView;

import univap.agent.Agente;
import univap.model.Impressora;
import univap.repository.ImpressoraRepo;
import univap.service.ImpressoraServiceImpl;

@RestController
@RequestMapping("/impressora")
@CrossOrigin
/**@CrossOrigin Permite que os serviços dessa classe possam ser acessados por aplicações JavaScript hospedadas em outros servidores*/
public class ImpressoraController {

	@Autowired
	private ImpressoraRepo impressoraRepo;
	@Autowired
	private ImpressoraServiceImpl impressoraService;
	
	/**GET TODAS AS IMPRESSORAS*/
	@GetMapping
	@JsonView(View.ViewResumo.class)
	public List<Impressora> listar() {
		return impressoraRepo.findAll();
	}
	
	/**GET Impressora: PARAMETRO ID*/
	@GetMapping("/{impressoraId}") 
	@JsonView(View.ViewResumo.class)
	public ResponseEntity<Impressora> buscar(@PathVariable Long impressoraId) { 		
		Optional <Impressora> impressora = impressoraRepo.findById(impressoraId);
		if (impressora.isPresent()) {
			return ResponseEntity.ok(impressora.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	/**POST DE UM NOVA IMPRESSORA*/
	@PostMapping(value = "/cadastrar")
	@JsonView(View.ViewResumo.class)
	public Impressora cadastrarImpressora(@Valid @RequestBody ImpressoraDTO impressora) throws IOException { 
		return impressoraService.novaImpressora(
				impressora.getPatrimonio(),
				impressora.getIp(),
				impressora.getDepartamento()
				);				
	}
	
	/**PUT DE UPDATE DE UMA IMPRESSORA: PARAMETRO ID*/
	@PutMapping("/atualizar/{impressoraId}")
	@JsonView(View.ViewResumo.class)
	public ResponseEntity<Impressora> atualizar(@PathVariable Long impressoraId) { 		
		Optional <Impressora> optImpressora = impressoraRepo.findByPatrimonio(impressoraId);
		if (optImpressora.isPresent()) {
			Impressora impressora = optImpressora.get();
			impressoraService.atualiza(impressora);
			return ResponseEntity.ok(optImpressora.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	/**DELETE DE UM DEPARTAMENTO: PARAMETRO ID*/
	@DeleteMapping("/deletar/{impressoraId}")
	public ResponseEntity<Void> remover(@PathVariable Long impressoraId) {
		if (!impressoraRepo.existsById(impressoraId)) {
			return ResponseEntity.notFound().build();
		}
		else {
			impressoraService.excluir(impressoraId);
			return ResponseEntity.noContent().build();
		}
	}
	
}