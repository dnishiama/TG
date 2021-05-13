package univap.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import univap.dto.HistoricoDTO;
import univap.dto.RateioDTO;
import univap.dto.RateioGestorDTO;
import univap.model.Historico;
import univap.repository.HistoricoRepo;
import univap.service.HistoricoServiceImpl;

@RestController
@RequestMapping("/historico")
@CrossOrigin
public class HistoricoController {

	@Autowired
	private HistoricoRepo repository;

	@Autowired
	private HistoricoServiceImpl service;

	@GetMapping
	@JsonView(View.ViewResumo.class)
	public List<Historico> listAllHistorico() {
		return repository.findAll();
	}

	@GetMapping("/{mes}/{ano}")
	@JsonView(View.ViewResumo.class)
	public List<Historico> listHistoricoByMesandAno(@PathVariable Long mes, @PathVariable Long ano) {
		List<Historico> lista = service.listarRateio(mes, ano);
		return lista;
	}
	
	@GetMapping("/rateio/{mes}/{ano}")
	public ResponseEntity<List<RateioDTO>> rateioByMesAndAnoGroupedByCcusto(@PathVariable Long mes,
			@PathVariable Long ano) throws IOException, Exception {
		List<RateioDTO> lista = service.listarRateioDTO(mes, ano);
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping("/rateioGestor/{mes}/{ano}")
	public ResponseEntity<List<RateioGestorDTO>> rateioByGestor(@PathVariable Long mes,
			@PathVariable Long ano) throws IOException, Exception {
		List<RateioGestorDTO> lista = service.listarRateioGestorDTO(mes, ano);
		return ResponseEntity.ok(lista);
	}	
	@GetMapping("/rateioGestorEmail/{mes}/{ano}")
	public ResponseEntity<List<RateioGestorDTO>> rateioByGestorAndSendEmail(@PathVariable Long mes,
			@PathVariable Long ano) throws IOException, Exception {
		List<RateioGestorDTO> lista = service.listarRateioGestorDTOAndSendEmail(mes, ano);
		return ResponseEntity.ok(lista);
	}

	@PostMapping(value = "/cadastrar/{mes}/{ano}")
	@JsonView(View.ViewResumo.class)
	public List<Historico> postNewHistorico(@Valid @RequestBody HistoricoDTO historicos[], @PathVariable Long mes, @PathVariable Long ano) throws Exception {
		return service.novoHistorico(historicos, mes, ano);
	}
}
