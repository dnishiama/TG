package view;
import java.io.IOException;

import univap.agent.Agente;

public class Main2 {

	public static void main(String[] args) throws IOException {

		String[] ips = {
			"172.17.88.241", 
			"172.17.88.241", 
			"172.17.104.243", 
			"172.17.144.242", 
			"172.16.42.65", 
			"10.0.10.241", 
			"172.16.37.242", 
			"172.17.132.99", 
			"172.17.128.242"							 
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