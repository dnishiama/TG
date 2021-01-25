package univap.agent;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exception.NegocioException;
import univap.model.Oid;
import univap.repository.OidRepo;
import univap.service.OidServiceImpl;

public class Agente2 {
	
	@Autowired
	private OidRepo oidRepo;
	
	//Declaração de OID's padrões 
	
	//Fabricante é um OID comum para todas as impressoras.
	static String fabricanteOid = ".1.3.6.1.2.1.1.1.0";
		
	//Modelo é um OID comum para todas as impressoras da mesma fabricante.	
	static String lexmarkModel = ".1.3.6.1.4.1.641.6.2.3.1.4.1";
	static String okiModel = ".1.3.6.1.2.1.1.5.0";
	static String epsonModel = ".1.3.6.1.4.1.1248.1.2.2.1.1.1.2.1";
	static String sharpModel = ".1.3.6.1.2.1.25.3.2.1.3.1";

	//Serial é um OID comum para todas as impressoras da mesma fabricante.
	static String lexmarkSerial = ".1.3.6.1.4.1.641.2.1.2.1.6.1";
	static String okiSerial = ".1.3.6.1.2.1.43.5.1.1.17.1";
	static String epsonSerial = ".1.3.6.1.4.1.1248.1.2.2.2.1.1.2.1.2";
	static String sharpSerial = ".1.3.6.1.2.1.43.5.1.1.17.1";

	Snmp snmp = null;
	String address = null;

	public Agente2(String add) {
		address = add;
	}
	
	public Agente2() {
	}

	private void start() throws IOException {
		TransportMapping transport = new DefaultUdpTransportMapping();
		snmp = new Snmp(transport);
		transport.listen();
	}

	public String getAsString(OID oid) throws IOException {
		ResponseEvent event = get(new OID[] { oid });
		return event.getResponse().get(0).getVariable().toString();
	}
	
	public ResponseEvent get(OID oids[]) throws IOException {
		PDU pdu = new PDU();
		for (OID oid : oids) {
			pdu.add(new VariableBinding(oid));
		}
		pdu.setType(PDU.GET);
		ResponseEvent event = snmp.send(pdu, getTarget(), null);
		if (event != null) {
			return event;
		}
		throw new RuntimeException("GET timed out");
	}

	private Target getTarget() {
		Address targetAddress = GenericAddress.parse(address);
		CommunityTarget target = new CommunityTarget();
		target.setCommunity(new OctetString("public"));
		target.setAddress(targetAddress);
		target.setRetries(2);
		target.setTimeout(1500);
		target.setVersion(SnmpConstants.version2c);
		return target;
	}
	
	public boolean disponivel (String ip) throws UnknownHostException, IOException {
		InetAddress inet = InetAddress.getByName(ip);
	    if (inet.isReachable(5000)) {
	    	return true;
	    }
	    else {
	    	System.out.println("Impressora offline!");
	    	return false;
	    }
	}
	
	public String getFabricante(String ips) throws Exception 
	{
		if (disponivel(ips)) {
			try {
				Agente2 client = new Agente2(ips + "/161");
				client.start();		
				String fabricante = null;
				if(!client.getAsString(new OID(fabricanteOid)).toString().isBlank()) {
					fabricante = client.getAsString(new OID(fabricanteOid)).toString();
					if (fabricante.contains("Lexmark")) {
						fabricante = "Lexmark";
					}
					else if (fabricante.contains("Oki Data Corporation")) {
						fabricante = "Oki Data";
					}
					else if  (fabricante.contains("EPSON")) {
						fabricante = "Epson";
					}
					else if  (fabricante.contains("SHARP")) {
						fabricante = "Sharp";
					}
				}
				System.out.println("Fabricante: " + fabricante);
				return fabricante;				
			}
			catch(Exception e) {
				throw new Exception (e); 
			}
		}
		else {
			throw new NegocioException("Impressora Offline");
		}
	}
	
	public String getModelo(String ips) throws Exception {	
		if (disponivel(ips)) {
			try {
				Agente2 client = new Agente2(ips + "/161");
				client.start();	
				String fabricante = client.getAsString(new OID(fabricanteOid)).toString();
				String printerModel = null;
				
				if (fabricante.contains("Lexmark")) {		
					printerModel = client.getAsString(new OID(lexmarkModel)).substring(8);
				}
				else if (fabricante.contains("Oki Data Corporation")) {
					printerModel = client.getAsString(new OID(okiModel));
					if(printerModel.contains("MC780")){
						printerModel = "MC780";
					}
					else if(printerModel.contains("ES8473")) {
						printerModel = "ES8473";
					}
				}
				else if  (fabricante.contains("EPSON")) {
					printerModel = client.getAsString(new OID(epsonModel));
				}
				else if  (fabricante.contains("SHARP")) {
					printerModel = client.getAsString(new OID(sharpModel)).substring(6);
				}
				return printerModel;
			}
			catch(Exception e) {
				throw new Exception (e);
			}
		}
		else {
			throw new NegocioException("Impressora Offline");
		}						
	}
	
	public String serial(String ips) throws Exception {
		if (disponivel(ips))
		{
			Agente2 client = new Agente2(ips + "/161");
			client.start();		
			try {
				String fabricante = client.getAsString(new OID(fabricanteOid)).toString();
				String printerSerial = null;		
				if (fabricante.contains("Lexmark")) {		
					printerSerial = client.getAsString(new OID(lexmarkSerial));
				}
				else if (fabricante.contains("Oki Data Corporation")) {
					printerSerial = client.getAsString(new OID(okiSerial));
				}
				else if  (fabricante.contains("EPSON")) {
					printerSerial = client.getAsString(new OID(epsonSerial));
				}
				else if  (fabricante.contains("SHARP")) {
					printerSerial = client.getAsString(new OID(sharpSerial));
				}
				return printerSerial;
			}
			catch(Exception e) {
				throw new Exception (e);
			}
		}
		else {
			throw new NegocioException("Impressora Offline");
		}	
	}
	
	public Long getContadorMono(String ips) throws Exception {
		if (disponivel(ips)) {
			try {
				Agente2 client = new Agente2(ips + "/161");
				client.start();
				String fabricante = client.getAsString(new OID(fabricanteOid)).toString();
				String printerModel;
				Long printerCounterMono = null;
				
				if (fabricante.contains("Lexmark")) {		
					printerModel = client.getAsString(new OID(lexmarkModel));				
					if (printerModel.contains("MX410de")) {
						Oid oid = oidRepo.findByDescricao("MX410de");
						printerCounterMono = Long.parseLong(client.getAsString(new OID(oid.getMono())));
					}
					else if (printerModel.contains("X656de")) {
						Oid oid = oidRepo.findByDescricao("X656de");
						printerCounterMono = Long.parseLong(client.getAsString(new OID(oid.getMono())));
					}
					else if (printerModel.contains("T654dn")) {
						Oid oid = oidRepo.findByDescricao("T654dn");
						printerCounterMono = Long.parseLong(client.getAsString(new OID(oid.getMono())));
					}	
				}
				
				
				else if (fabricante.contains("Oki Data")) {	
					printerModel = client.getAsString(new OID(okiModel));						
					if (printerModel.contains("MC780")) {						
						Oid oid = oidRepo.findByDescricao("MC780");												
						printerCounterMono = Long.parseLong(client.getAsString(new OID(oid.getMono())));
					}
				}
				
				
				else if (fabricante.contains("EPSON")) {
					printerModel = client.getAsString(new OID(epsonModel));		
					if (printerModel.contains("WF-M5799")) {
						Oid oid = oidRepo.findByDescricao("Epson WF-M5799");
						printerCounterMono = Long.parseLong(client.getAsString(new OID(oid.getMono())));
					}
					else if (printerModel.contains("WF-M5299")) {
						Oid oid = oidRepo.findByDescricao("Epson WF-M5299");
						printerCounterMono = Long.parseLong(client.getAsString(new OID(oid.getMono())));
					}
					else if (printerModel.contains("WF-5690")) {
						Oid oid = oidRepo.findByDescricao("Epson WF-5690");
						printerCounterMono = Long.parseLong(client.getAsString(new OID(oid.getMono())));
					}				
				}
				else if (fabricante.contains("SHARP")) {
					printerModel = client.getAsString(new OID(sharpModel)).substring(6);
					Oid oid = oidRepo.findByDescricao("MX-M363N");
					printerCounterMono = Long.parseLong(client.getAsString(new OID(oid.getMono())));
				}
				return printerCounterMono;
			}
			catch(Exception e) {
				throw new Exception (e);
			}
		}
		else {
			throw new NegocioException("Impressora Offline");
		}
	}
	
	public Long GetContadorColor(String ips) throws IOException 
	{
		Agente2 client = new Agente2(ips + "/161");
		try {
			client.start();
			String selectMIB = client.getAsString(new OID(".1.3.6.1.2.1.1.1.0")).toString();
			Long printerCounterColor = null;
			String printerModel, printerSerial; 
			
			if (selectMIB.contains("Oki Data"))
			{
				printerModel = client.getAsString(new OID(".1.3.6.1.2.1.1.5.0"));
				if (printerModel.contains("MC780")) {					
					printerSerial = client.getAsString(new OID(".1.3.6.1.2.1.43.5.1.1.17.1"));
					printerCounterColor = Long.parseLong(client.getAsString(new OID(".1.3.6.1.4.1.1129.2.3.50.1.3.21.6.1.2.1.1")));
					Long printerCounterColorMono = Long.parseLong(client.getAsString(new OID(".1.3.6.1.4.1.1129.2.3.50.1.3.21.6.1.2.1.2")));
					printerCounterColor += printerCounterColorMono; 
				}
				else if (printerModel.contains("ES8473")) {
					printerSerial = client.getAsString(new OID(".1.3.6.1.2.1.43.5.1.1.17.1"));
					printerCounterColor = Long.parseLong(client.getAsString(new OID("1.3.6.1.4.1.2001.1.1.4.2.1.1.21.0")));
					Long printerCounterColorMono = Long.parseLong(client.getAsString(new OID(".1.3.6.1.4.1.2001.1.1.4.2.1.1.27.0")));
					printerCounterColor += printerCounterColorMono; 
					
				}
				
			}
			else if  (selectMIB.contains("EPSON"))
			{
				printerModel = client.getAsString(new OID(".1.3.6.1.4.1.1248.1.2.2.1.1.1.2.1"));
				printerSerial = client.getAsString(new OID(".1.3.6.1.4.1.1248.1.2.2.2.1.1.2.1.2"));
				if (printerModel.contains("WF-5690"))
				{
					printerCounterColor = Long.parseLong(client.getAsString(new OID(".1.3.6.1.4.1.1248.1.2.2.27.1.1.4.1.1")));
				}
			}
			/** Ver impressora do Marketing.
			 else if (selectMIB.contains("SHARP")) {
				printerModel = client.getAsString(new OID(".1.3.6.1.2.1.25.3.2.1.3.1")).substring(7);
				printerSerial = client.getAsString(new OID(".1.3.6.1.2.1.43.5.1.1.17.1"));
				if (printerModel.contains("MX2010U"))
				{
					printerCounterColor = Long.parseLong(client.getAsString(new OID("")));
				}
			}*/
			return printerCounterColor;
		}
		catch(IOException e) {
			System.out.println(e);
		}
		return null;				
	}
}