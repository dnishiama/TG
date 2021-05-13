package univap.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exception.NegocioException;
import univap.dto.HistoricoDTO;
import univap.dto.RateioDTO;
import univap.model.Historico;
import univap.model.Impressora;
import univap.repository.HistoricoRepo;
import univap.repository.ImpressoraRepo;

@Service
public class HistoricoServiceImpl {

	@Autowired
	private HistoricoRepo historicoRepo;
	@Autowired
	private ImpressoraRepo impressoraRepo;

	/**
	 * @Transactional /**Garantir a atomicidade public Historico
	 *                novoHistorico(HistoricoDTO historico, Long mes, Long ano)
	 *                throws Exception {
	 * 
	 *                Historico historicoExistente =
	 *                historicoRepo.findByPatrimonioAndMesAndAno(historico.getPatrimonio(),
	 *                mes, ano); Impressora impressoraExistente =
	 *                impressoraRepo.findByPatrimonio(historico.getPatrimonio());
	 * 
	 *                if (historicoExistente != null) { throw new
	 *                NegocioException("Historico já cadastrado"); }
	 * 
	 *                else if (impressoraExistente == null) { throw new
	 *                NegocioException("Impressora inexistente"); }
	 * 
	 *                else { if (impressoraExistente.getContadorMono() >=
	 *                historico.getContadorMono())
	 * 
	 *                { Historico historicoNovo = new Historico();
	 * 
	 *                historicoNovo.setPatrimonio(historico.getPatrimonio());
	 *                historicoNovo.setContadorMono(historico.getContadorMono());
	 *                historicoNovo.setContadorColor(historico.getContadorColor());
	 *                historicoNovo.setProducaoMono(historico.getProducaoMono());
	 *                historicoNovo.setProducaoColor(historico.getProducaoColor());
	 *                historicoNovo.setMes(mes); historicoNovo.setAno(ano);
	 * 
	 *                Date hoje = new Date(); SimpleDateFormat dateFormat = new
	 *                SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); String check =
	 *                dateFormat.format(hoje); historicoNovo.setData(check);
	 * 
	 *                historicoNovo.setImpressora(impressoraExistente);
	 * 
	 *                historicoRepo.save(historicoNovo); return historicoNovo; }
	 *                else { throw new NegocioException("Contador não confere"); } }
	 *                }
	 */

	@Transactional
	public List<Historico> novoHistorico(HistoricoDTO historico[], Long mes, Long ano) throws Exception {
		try {
			List<Historico> list = new ArrayList<Historico>();
			for (HistoricoDTO historic : historico) {
				Historico historicoExistente = historicoRepo.findByPatrimonioAndMesAndAno(historic.getPatrimonio(), mes,
						ano);
				Impressora impressoraExistente = impressoraRepo.findByPatrimonio(historic.getPatrimonio());

				if (historicoExistente != null) {
					throw new NegocioException("Historico já cadastrado");
				}

				else if (impressoraExistente == null) {
					throw new NegocioException("Impressora inexistente");
				}

				else {
					if (impressoraExistente.getContadorMono() >= historic.getContadorMono())

					{
						Historico historicoNovo = new Historico();

						historicoNovo.setPatrimonio(historic.getPatrimonio());
						historicoNovo.setContadorMono(historic.getContadorMono());
						historicoNovo.setContadorColor(historic.getContadorColor());
						historicoNovo.setProducaoMono(historic.getProducaoMono());
						historicoNovo.setProducaoColor(historic.getProducaoColor());
						historicoNovo.setMes(mes);
						historicoNovo.setAno(ano);

						Date hoje = new Date();
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String check = dateFormat.format(hoje);
						historicoNovo.setData(check);

						historicoNovo.setImpressora(impressoraExistente);

						list.add(historicoNovo);

					} else {
						throw new NegocioException("Contador não confere");
					}
				}
			}
			historicoRepo.saveAll(list);
			return list;
		} catch (Exception e) {
			throw new NegocioException("Algo deu errado. " + e);
		}

	}

	public List<Historico> listar() {
		return historicoRepo.findAll();
	}

	public List<Historico> listarRateio(Long mes, Long ano) {
		List<Historico> lista = historicoRepo.findByMesAndAno(mes, ano);
		return lista;
	}

	public List<RateioDTO> listarRateioDTO(Long mes, Long ano) {
		List<RateioDTO> lista = historicoRepo.rateio(mes, ano);
		return lista;
	}
}
