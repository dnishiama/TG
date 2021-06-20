package univap.service;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exception.NegocioException;
import univap.dto.HistoricoDTO;
import univap.dto.RateioDTO;
import univap.dto.RateioGestorDTO;
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
			listarRateioGestorDTOAndSendEmail(mes, ano);
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
	
	public List<RateioDTO> listarRateioDTO(Long mes, Long ano) throws IOException, Exception {
		List<RateioDTO> lista = historicoRepo.rateio(mes, ano);
		return lista;
	}

	public List<RateioGestorDTO> listarRateioGestorDTO(Long mes, Long ano) {
		List<RateioGestorDTO> lista = historicoRepo.rateioByGestor(mes, ano);
		return lista;
	}

	public List<RateioGestorDTO> listarRateioGestorDTOAndSendEmail(Long mes, Long ano) throws IOException, Exception {
		List<RateioGestorDTO> lista = historicoRepo.rateioByGestor(mes, ano);
		sendEmail(lista);
		return lista;
	}

	public void sendEmail(List<RateioGestorDTO> lista) throws Exception, IOException{

		Properties login = new Properties();
		try (FileReader in = new FileReader("C:\\Temp\\login.properties")) {
			login.load(in);
		}
		catch (Exception e) {
			System.out.println(e);
		}

		// Sender's email ID needs to be mentioned
		String from = login.getProperty("username");

		// Sender's email ID needs to be mentioned
		String pass = login.getProperty("password");

		// Assuming you are sending email from localhost
		String host = "smtp.univap.br";

		// Get system properties
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.port", "587");
		properties.setProperty("mail.smtp.auth", "true");

		for (RateioGestorDTO item : lista) {
			// Recipient's email ID needs to be mentioned.
			String to = item.getEmail();

			// Get the default Session object.
			// Session session = Session.getDefaultInstance(properties);
			Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(from, pass);
				}
			});

			try {
				// Create a default MimeMessage object.
				MimeMessage message = new MimeMessage(session);

				// Set From: header field of the header.
				message.setFrom(new InternetAddress(from));

				// Set To: header field of the header.
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

				// Set Subject: header field
				message.setSubject("Consumo Mensal Outsourcing de Impressão.");

				
				if (item.getContadorColor() == 0){
					// Now set the actual message
					message.setText(item.getNome() + "," 
							+ "\n\n" 
							+ "Segue abaixo o consumo mensal das impressoras sob seu domínio."
							+ "\n\n"
							+ "Impressoras Mono: " + item.getContadorMono()
							+ "\n\n" 
							+ "Qualquer dúvida estou a disposição!,"
							+ "\n\n" 
							+ "Atenciosamente,"
							+ "\n" 
							+ "Douglas Nishiama");
				}
				else {
					message.setText(item.getNome() + "," 
							+ "\n\n" 
							+ "Segue abaixo o consumo mensal das impressoras sob seu domínio."
							+ "\n\n"
							+ "Impressoras Color: " + item.getContadorColor() + "\n"
							+ "Impressoras Mono: " + item.getContadorMono()
							+ "\n\n" 
							+ "Qualquer dúvida estou a disposição!,"
							+ "\n\n" 
							+ "Atenciosamente,"
							+ "\n" 
							+ "Douglas Nishiama");
				}


				// Send message
				Transport.send(message);
				System.out.println("Sent message successfully....");
			} catch (MessagingException mex) {
				mex.printStackTrace();
			}
		}

	}
}
