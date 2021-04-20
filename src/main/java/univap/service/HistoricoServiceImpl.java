package univap.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import exception.NegocioException;
import univap.model.Impressora;
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
	public Historico novoHistorico(Long patrimonio, Long contadorMono, Long contadorColor, Long producaoMono, Long producaoColor, Long mes_referencia, Long ano_referencia) throws Exception {
		
		Historico historicoExistente = historicoRepo.findByPatrimonioAndMesAndAno(patrimonio, mes_referencia, ano_referencia);
		Impressora impressoraExistente = impressoraRepo.findByPatrimonio(patrimonio);
		if (historicoExistente != null) {
			throw new NegocioException("Historico já cadastrado");
		}
		else if (impressoraExistente == null) {
			throw new NegocioException("Impressora inexistente");
		}
		else {		
			if (impressoraExistente.getContadorMono()>=contadorMono) {
				Historico historicoNovo = new Historico();

				historicoNovo.setPatrimonio(patrimonio);
				historicoNovo.setContadorMono(contadorMono);
				historicoNovo.setContadorColor(contadorColor);
				historicoNovo.setProducaoMono(producaoMono);
				historicoNovo.setProducaoColor(producaoColor);
				historicoNovo.setMes(mes_referencia);
				historicoNovo.setAno(ano_referencia);
								
				Date hoje = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String check = dateFormat.format(hoje);
				historicoNovo.setData(check);
					
				historicoNovo.setImpressora(impressoraExistente);
				
				historicoRepo.save(historicoNovo);
				return historicoNovo;
			}
			else {
				throw new NegocioException("Contador não bate");
			}			
		}
	}
	
	public List<Historico> listar() {
		return historicoRepo.findAll();
	}
}
