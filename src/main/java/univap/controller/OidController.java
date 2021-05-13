package univap.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import univap.model.Oid;
import univap.repository.OidRepo;
import univap.service.OidServiceImpl;

@RestController
@RequestMapping("/oid")
@CrossOrigin
public class OidController {

	@Autowired
	private OidRepo repository;

	@Autowired
	private OidServiceImpl service;

	@GetMapping
	@JsonView(View.ViewResumo.class)
	public List<Oid> listar() {
		return repository.findAll();
	}

	@GetMapping("/{descricao}")
	@JsonView(View.ViewResumo.class)
	public Oid buscar(@PathVariable String descricao) {
		return service.findByDescricao(descricao);

	}
}
