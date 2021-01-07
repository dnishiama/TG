package view;
import java.io.IOException;

import univap.agent.Agente;

public class Main2 {

	public static void main(String[] args) throws Exception {

		String[] ips = {
			"200.136.181.90", 							 
				};
			for (int i = 0; i < ips.length; i++) 
			{
				if (Agente.disponivel(ips[i]))
				{
					System.out.println(ips[i]);
					System.out.println(Agente.getContadorMono(ips[i]));
					System.out.println(Agente.getContadorColor(ips[i]));
				}
				System.out.println("-------------------------------------------------------------");
		    }


	}

}