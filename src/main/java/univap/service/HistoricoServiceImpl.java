package univap.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import exception.NegocioException;
import univap.model.Impressora;
import univap.controller.HistoricoDTO;
import univap.controller.RateioDTO;
import univap.model.Historico;
import univap.repository.HistoricoRepo;
import univap.repository.ImpressoraRepo;


@Service
public class HistoricoServiceImpl {

	@Autowired
	private HistoricoRepo historicoRepo;
	@Autowired
	private ImpressoraRepo impressoraRepo;
	
	@Transactional /**Garantir a atomicidade*/
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Historico novoHistorico(HistoricoDTO historico, Long mes, Long ano) throws Exception {
		
		Historico historicoExistente = historicoRepo.findByPatrimonioAndMesAndAno(historico.getPatrimonio(), mes, ano);
		
		Impressora impressoraExistente = impressoraRepo.findByPatrimonio(historico.getPatrimonio());
		
		if (historicoExistente != null) {
			throw new NegocioException("Historico já cadastrado");
		}
		
		else if (impressoraExistente == null) {
			throw new NegocioException("Impressora inexistente");
		}
		
		else {	
			if (impressoraExistente.getContadorMono() >= historico.getContadorMono())
			
			{
				Historico historicoNovo = new Historico();
				
				historicoNovo.setPatrimonio(historico.getPatrimonio());
				historicoNovo.setContadorMono(historico.getContadorMono());
				historicoNovo.setContadorColor(historico.getContadorColor());
				historicoNovo.setProducaoMono(historico.getProducaoMono());
				historicoNovo.setProducaoColor(historico.getProducaoColor());
				historicoNovo.setMes(mes);
				historicoNovo.setAno(ano);				
								
				Date hoje = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String check = dateFormat.format(hoje);
				historicoNovo.setData(check);				
					
				historicoNovo.setImpressora(impressoraExistente);
				
				historicoRepo.save(historicoNovo);
				return historicoNovo;
			}
			else {
				throw new NegocioException("Contador não confere");
			}			
		}
	}
	
	public List<Historico> listar() {

		return historicoRepo.findAll();
	}
	
	public List<Object> listarRateio(@PathVariable Long mes, @PathVariable Long ano) {
		
		return historicoRepo.rateio(mes, ano);
	}
}
