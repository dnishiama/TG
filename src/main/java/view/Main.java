package view;
import java.io.IOException;

import univap.agent.Agente;

public class Main {

	public static void main(String[] args) throws IOException {

		String[] ips = {
				/**"172.17.88.241", //BLOCO B - COORDENA��O (VILMA)
				"172.17.104.243", //FACULDADE DE DIREITO - SECRETARIA
				"172.17.40.240", //BLOCO 03 - SECRETARIA (FEA)
				"172.16.37.243", //CTIC - BACKUP
				"172.16.31.240", //CTIC
				"172.16.37.242", //BLOCO A - DIRE��O
				"172.17.128.248", //ORIENTA��O PEDAG�GICA
				"172.17.132.99", //CTI - EL�TRICA
				"172.17.128.242", //CTI - SECRETARIA
				"172.17.40.241", //Urbanova - Bl.3 Direção */
				"172.17.56.240"

				};
		//while (true) {
			for (int i = 0; i < ips.length; i++) 
			{
				if (Agente.disponivel(ips[i]))
				{
					Agente.getContadorMono(ips[i]);
					Agente.getContadorColor(ips[i]);
				}
				System.out.println("-------------------------------------------------------------");
		    }
		//}

	}

}