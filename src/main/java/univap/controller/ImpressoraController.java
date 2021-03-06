package univap.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
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

import univap.dto.ImpressoraDTO;
import univap.dto.ImpressoraOfflineDTO;
import univap.model.Departamento;
import univap.model.Impressora;
import univap.repository.ImpressoraRepo;
import univap.service.ImpressoraServiceImpl;

@RestController
@RequestMapping("/impressora")
@CrossOrigin
public class ImpressoraController implements CommandLineRunner {

	@Autowired
	private ImpressoraRepo impressoraRepo;

	@Autowired
	private ImpressoraServiceImpl impressoraService;

	@GetMapping
	@JsonView(View.ViewResumo.class)
	public List<Impressora> listAll() {
		return impressoraRepo.findAll(Sort.by(Sort.Direction.DESC, "ultimoUpdate"));
	}

	@GetMapping("/{impressoraId}")
	@JsonView(View.ViewResumo.class)
	public ResponseEntity<Impressora> searchById(@PathVariable Long impressoraId) {
		Optional<Impressora> impressora = impressoraRepo.findById(impressoraId);
		if (impressora.isPresent()) {
			return ResponseEntity.ok(impressora.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping(value = "/cadastrar")
	@JsonView(View.ViewResumo.class)
	public Impressora postNewImpressoraOnline(@Valid @RequestBody ImpressoraDTO impressora) throws Exception {
		return impressoraService.novaImpressora(impressora.getPatrimonio(), impressora.getIp(),
				impressora.getDepartamento());
	}

	@PostMapping(value = "/cadastraroffline")
	@JsonView(View.ViewResumo.class)
	public Impressora postNewImpressoraOffline(@Valid @RequestBody ImpressoraDTO impressora) throws Exception {
		return impressoraService.novaImpressoraOffline(impressora.getPatrimonio(), impressora.getIp(),
				impressora.getFabricante(), impressora.getModelo(), impressora.getSerial(),
				impressora.getContadorMono(), impressora.getContadorColor(), impressora.getDepartamento());
	}

	@DeleteMapping("/deletar/{impressoraId}")
	public ResponseEntity<Void> deleteById(@PathVariable Long impressoraId) {
		if (!impressoraRepo.existsById(impressoraId)) {
			return ResponseEntity.notFound().build();
		} else {
			impressoraService.excluir(impressoraId);
			return ResponseEntity.noContent().build();
		}
	}

	@PutMapping("/contador/{serial}")
	@JsonView(View.ViewResumo.class)
	public ResponseEntity<Impressora> updatePrinterCounterBySerial(@PathVariable String serial) {
		Impressora atualizaImpressora = impressoraRepo.findBySerial(serial);
		if (atualizaImpressora.getSerial().equals(serial)) {
			impressoraService.atualiza(atualizaImpressora);
			return ResponseEntity.ok(atualizaImpressora);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping(value = "/atualizarOnline/{id}")
	@JsonView(View.ViewResumo.class)
	public ResponseEntity<Impressora> putImpressoraOnline(@Valid @RequestBody ImpressoraDTO impressora, @PathVariable Long id) throws Exception {
		return ResponseEntity.ok(impressoraService.atualizaImpressoraOnline(impressora, id));
	}
	
	
	@PutMapping(value = "/atualizarOffline/{id}")
	@JsonView(View.ViewResumo.class)
	public Impressora putImpressoraOffline(@Valid @RequestBody ImpressoraDTO impressora, @PathVariable Long id) throws Exception {
		return impressoraService.atualizaImpressoraOffline(impressora, id);
	}
	
	@GetMapping("/agente")
	@JsonView(View.ViewResumo.class)
	public void updateAllCounters() {
		List<Impressora> impressoras = impressoraRepo.findAll();
		for (Impressora impressora : impressoras) {
			try {
				impressoraService.atualiza(impressora);
				System.out.println("Pat. " + impressora.getPatrimonio() + " foi atualizada com sucesso!");
			} catch (Exception e) {
				System.out.println("Problema: " + e);
			}
		}
	}

	@Override
	public void run(String... args) throws Exception {
		LocalDateTime date = LocalDateTime.now();
		int hora;
		int minuto;
		while (true) {
			date = LocalDateTime.now();
			hora = date.getHour();
			minuto = date.getMinute();
			if ((hora == 7) && (minuto == 30) || (hora == 12) && (minuto == 00)) {
				System.out.println("Atualizando");
				updateAllCounters();
				Thread.sleep(1000 * 60);
			}
		}
	}
}