package univap.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
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
import univap.model.Historico;
import univap.model.Impressora;
import univap.repository.HistoricoRepo;
import univap.service.HistoricoServiceImpl;


@RestController
@RequestMapping("/historico")
@CrossOrigin
public class HistoricoController {
	
	@Autowired
	private HistoricoRepo historicoRepo;

	@Autowired
	private HistoricoServiceImpl historicoService;
	
	/**GET TODO O HISTORICO*/
	@GetMapping
	@JsonView(View.ViewResumo.class)
	public List<Historico> listar() {
		return historicoRepo.findAll();
	}
		
	/**GET HISTORICO: PARAMETRO MES E ANO*/
	@GetMapping("/{mes}/{ano}") 
	@JsonView(View.ViewResumo.class)
	public List<Object> buscarPorMesEAno(@PathVariable Long mes, @PathVariable Long ano) { 		
		return historicoService.listarRateio(mes, ano);
	}
	
	/**POST DE UM NOVO Historico*/
	@PostMapping(value = "/cadastrar/{mes}/{ano}")
	@JsonView(View.ViewResumo.class)
	public List<Object> cadastrarHistorico(@Valid @RequestBody HistoricoDTO historicos[], @PathVariable Long mes, @PathVariable Long ano ) throws Exception { 
		Historico[] lista = new Historico[historicos.length];
		int i = 0;
		for(HistoricoDTO historico : historicos) {
			try {
				lista[i++] = historicoService.novoHistorico(historico, mes, ano);				
				System.out.println("Inserido");
			}
			catch(Exception e) {
				throw new Exception("Erro ao processar arquivo." + e);
			}
		}	
		
		return historicoService.listarRateio(mes, ano);		
	}

}
