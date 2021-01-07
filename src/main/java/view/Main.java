package view;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import univap.agent.Agente;
import univap.model.Impressora;
import univap.service.ImpressoraServiceImpl;


public class Main {
	
	@Autowired
	public static ImpressoraServiceImpl impressoraService;
	
	public static void main(String[] args) throws Exception {
			
		for (Impressora impressora : impressoraService.listar()) {
			String ip = impressora.getIp();
			try{
				if (Agente.disponivel(ip))
				{
					Long mono = Agente.getContadorMono(ip);
					Long color = Agente.getContadorColor(ip);
					System.out.println("Mono: "+mono);
					System.out.println("Color: "+color);
				}
				System.out.println("-------------------------------------------------------------");
			}
			catch(Exception e) {
				System.out.println("nã nã");
				System.out.println("-------------------------------------------------------------");
			}

		}
	}

}