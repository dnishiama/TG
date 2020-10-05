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

public class Agente {

	Snmp snmp = null;
	String address = null;

	public Agente(String add) {
		address = add;
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
	
	public static boolean disponivel (String ip) throws UnknownHostException, IOException {
		InetAddress inet = InetAddress.getByName(ip);
	    if (inet.isReachable(5000)) {
	    	return true;
	    }
	    else {
	    	System.out.println("Impressora offline!");
	    	return false;
	    }
	}
	
	public static String fabricante(String ips) throws IOException 
	{
		Agente client = new Agente(ips + "/161");
		client.start();		
		String fabricante = null;
		if(!client.getAsString(new OID(".1.3.6.1.2.1.1.1.0")).toString().isBlank()) {
			if (client.getAsString(new OID(".1.3.6.1.2.1.1.1.0")).toString().contains("Lexmark")) {
				fabricante = "Lexmark";
			}
			else if (client.getAsString(new OID(".1.3.6.1.2.1.1.1.0")).toString().contains("Oki Data Corporation")) {
				fabricante = "Oki Data";
			}
			else if  (client.getAsString(new OID(".1.3.6.1.2.1.1.1.0")).toString().contains("EPSON")) {
				fabricante = "Epson";
			}
		}
		System.out.println("Fabricante: " + fabricante);
		return fabricante;			
	}
	
	public static String modelo(String ips) throws IOException {
		Agente client = new Agente(ips + "/161");
		client.start();		
		String selectMIB = client.getAsString(new OID(".1.3.6.1.2.1.1.1.0")).toString();
		String printerModel = null;
		
		if (selectMIB.contains("Lexmark")) {		
			printerModel = client.getAsString(new OID(".1.3.6.1.4.1.641.6.2.3.1.4.1"));
		}
		else if (selectMIB.contains("Oki Data Corporation")) {
			printerModel = client.getAsString(new OID(".1.3.6.1.4.1.1129.2.3.50.1.2.3.1.3.1.1"));
		}
		else if  (selectMIB.contains("EPSON")) {
			printerModel = client.getAsString(new OID(".1.3.6.1.4.1.1248.1.2.2.1.1.1.2.1"));
		}
		return printerModel;				
	}
	
	public static String serial(String ips) throws IOException {
		Agente client = new Agente(ips + "/161");
		client.start();		
		String selectMIB = client.getAsString(new OID(".1.3.6.1.2.1.1.1.0")).toString();
		String printerSerial = null;		
		if (selectMIB.contains("Lexmark")) {		
			printerSerial = client.getAsString(new OID("..1.3.6.1.4.1.641.2.1.2.1.6.1"));
		}
		else if (selectMIB.contains("Oki Data Corporation")) {
			printerSerial = client.getAsString(new OID(".1.3.6.1.2.1.43.5.1.1.17.1"));
		}
		else if  (selectMIB.contains("EPSON")) {
			printerSerial = client.getAsString(new OID(".1.3.6.1.4.1.1248.1.2.2.2.1.1.2.1.2"));
		}
		return printerSerial;				
	}
	
	public static Long getContadorMono(String ips) throws IOException {
		Agente client = new Agente(ips + "/161");
		client.start();
		
		String selectMIB = client.getAsString(new OID(".1.3.6.1.2.1.1.1.0")).toString();
		String printerModel;
		String printerSerial;
		Long printerCounterMono = (long) 0;
		
		if (selectMIB.contains("Lexmark")) {		
			printerModel = client.getAsString(new OID(".1.3.6.1.4.1.641.6.2.3.1.4.1"));
			printerSerial = client.getAsString(new OID("..1.3.6.1.4.1.641.2.1.2.1.6.1"));
			
			if (printerModel.contentEquals("Lexmark MX410de")) {
				printerCounterMono = Long.parseLong(client.getAsString(new OID(".1.3.6.1.2.1.43.10.2.1.4.1.1")));
			}
			else {
				printerCounterMono = Long.parseLong(client.getAsString(new OID(".1.3.6.1.4.1.641.2.1.5.1.0")));
			}	
		}
		else if (selectMIB.contains("Oki Data Corporation")) {
			printerModel = client.getAsString(new OID(".1.3.6.1.4.1.1129.2.3.50.1.2.3.1.3.1.1"));
			printerSerial = client.getAsString(new OID(".1.3.6.1.2.1.43.5.1.1.17.1"));
			printerCounterMono = Long.parseLong(client.getAsString(new OID(".1.3.6.1.4.1.1129.2.3.50.1.3.21.6.1.2.1.3")));
		}
		else if  (selectMIB.contains("EPSON")) {
			printerModel = client.getAsString(new OID(".1.3.6.1.4.1.1248.1.2.2.1.1.1.2.1"));
			printerSerial = client.getAsString(new OID(".1.3.6.1.4.1.1248.1.2.2.2.1.1.2.1.2"));

			if (printerModel.contains("WF-M5799")) {
				printerCounterMono = Long.parseLong(client.getAsString(new OID(".1.3.6.1.4.1.1248.1.2.2.27.1.1.3.1.1")));
			}
			else if (printerModel.contains("WF-5690")) {
				printerCounterMono = Long.parseLong(client.getAsString(new OID(".1.3.6.1.4.1.1248.1.2.2.27.1.1.4.1.1")));
			}						
		}
		return printerCounterMono;		
	}

	public static Long getContadorColor(String ips) throws IOException 
	{
		Agente client = new Agente(ips + "/161");
		client.start();
		
		String selectMIB = client.getAsString(new OID(".1.3.6.1.2.1.1.1.0")).toString();
		Long printerCounterColor = (long) -1;
		String printerModel, printerSerial; 
		
		if (selectMIB.contains("Oki Data Corporation"))
		{
			printerModel = client.getAsString(new OID(".1.3.6.1.4.1.1129.2.3.50.1.2.3.1.3.1.1"));
			printerSerial = client.getAsString(new OID(".1.3.6.1.2.1.43.5.1.1.17.1"));
			printerCounterColor = Long.parseLong(client.getAsString(new OID(".1.3.6.1.4.1.1129.2.3.50.1.3.21.6.1.2.1.1")));
			Long printerCounterColorMono = Long.parseLong(client.getAsString(new OID(".1.3.6.1.4.1.1129.2.3.50.1.3.21.6.1.2.1.2")));
			printerCounterColor += printerCounterColorMono; 
		}
		else if  (selectMIB.contains("EPSON"))
		{
			printerModel = client.getAsString(new OID(".1.3.6.1.4.1.1129.2.3.50.1.2.3.1.3.1.1"));
			printerSerial = client.getAsString(new OID(".1.3.6.1.2.1.43.5.1.1.17.1"));
			if (printerModel.contains("WF-5690"))
			{
				printerCounterColor = Long.parseLong(client.getAsString(new OID(".1.3.6.1.4.1.1248.1.2.2.27.1.1.3.1.1")));
			}
		}
		return printerCounterColor;		
	}
	
}