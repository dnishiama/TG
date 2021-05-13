package univap.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import univap.model.Autorizacao;
import univap.repository.AutorizacaoRepo;


@RestController 
@RequestMapping("/autorizacao") 
@CrossOrigin 

public class AutorizacaoController {
	
	@Autowired
	private AutorizacaoRepo autorizacaoRepo;	
	
	@GetMapping
	public List<Autorizacao> listar() {
		return autorizacaoRepo.findAll();
	}
}