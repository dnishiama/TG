package view;

import univap.agent.Agente;

public class Main2 {
	
	public static void main(String[] args) throws Exception {
		
		Agente agente = new Agente();
		
		
		String[] ips = {
			"172.17.128.242",
			"172.16.31.240"
				};
		
		for (int i = 0; i < ips.length; i++) 
		{
			if (agente.disponivel(ips[i]))
			{
				System.out.println(ips[i]);
				System.out.println(agente.getModelo(ips[i]));	
				System.out.println(agente.getContadorMono(ips[i]));
				System.out.println(agente.getContadorColor(ips[i]));
				
				
			}
			System.out.println("-------------------------------------------------------------");
	    }


	}

}