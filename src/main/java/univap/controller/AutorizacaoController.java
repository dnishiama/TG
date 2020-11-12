package univap.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import univap.model.Autorizacao;
import univap.repository.AutorizacaoRepo;


@RestController 
@RequestMapping("/autorizacao") 
@CrossOrigin 
/**@CrossOrigin Permite que os serviços dessa classe possam ser acessados por aplicações JavaScript hospedadas em outros servidores*/

public class AutorizacaoController {
	
	@Autowired
	private AutorizacaoRepo autorizacaoRepo;	
	
	/**GET TODOS OS AUTORIZAÇÕES*/
	@GetMapping
	@JsonView(View.ViewCompleto.class)
	public List<Autorizacao> listar() {
		return autorizacaoRepo.findAll();
	}
}