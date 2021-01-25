package univap.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import univap.model.Impressora;
import univap.model.Oid;
import univap.repository.OidRepo;
import univap.service.OidServiceImpl;

@RestController
@RequestMapping("/oid")
@CrossOrigin
public class OidController {
	
	@Autowired
	private OidRepo oidRepo;
	@Autowired
	private OidServiceImpl oidService;
	
	@GetMapping
	@JsonView(View.ViewResumo.class)
	public List<Oid> listar() {
		return oidRepo.findAll();
	}
	
	/**GET Impressora: PARAMETRO ID*/
	@GetMapping("/{descricao}") 
	@JsonView(View.ViewResumo.class)
	public Oid buscar(@PathVariable String descricao) { 		
		return oidService.findByDescricao(descricao);

	}
}
