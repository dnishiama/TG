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

public class AgenteSnmp {

	Snmp snmp = null;
	String address = null;

	/** Constructor * @param add */
	public AgenteSnmp(String add) 
	{
		address = add;
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

	public static void Coleta(String ips) throws IOException 
	{
		AgenteSnmp client = new AgenteSnmp(ips + "/161");
		client.start();
		
		String selectMIB = client.getAsString(new OID(".1.3.6.1.2.1.1.1.0")).toString();
		
		if (selectMIB.contains("Lexmark"))
		{		
			String printerModel = client.getAsString(new OID(".1.3.6.1.4.1.641.6.2.3.1.4.1"));
			String printerSerial = client.getAsString(new OID("..1.3.6.1.4.1.641.2.1.2.1.6.1"));
			String printerMac = client.getAsString(new OID(".1.3.6.1.2.1.1.5.0")).substring(2,14);
			String printerStatus = client.getAsString(new OID(".1.3.6.1.2.1.43.16.5.1.2.1.1"));
			String printerCounter;
			int printerTonerLevel;				
			if (printerModel.contentEquals("Lexmark MX410de"))
			{
				printerCounter = client.getAsString(new OID(".1.3.6.1.2.1.43.10.2.1.4.1.1"));
				printerTonerLevel = Integer.parseInt(client.getAsString(new OID(".1.3.6.1.2.1.43.11.1.1.9.1.1"))) / 100;
			}
			else 
			{
				printerCounter = client.getAsString(new OID(".1.3.6.1.4.1.641.2.1.5.1.0"));
				printerTonerLevel = Integer.parseInt(client.getAsString(new OID(".1.3.6.1.2.1.43.11.1.1.9.1.1"))) / 360;
			}
			System.out.println("Modelo: " + printerModel);
			System.out.println("Serial: " + printerSerial);
			System.out.println("MAC Address: " + printerMac);
			System.out.println("Estado Atual: " + printerStatus);			
			System.out.println("Contador Atual: " + printerCounter);
			System.out.println("Toner Level: " + printerTonerLevel + " %");
		}
		else if (selectMIB.contains("Oki Data Corporation"))
		{
			String printerModel = client.getAsString(new OID(".1.3.6.1.4.1.1129.2.3.50.1.2.3.1.3.1.1"));
			String printerSerial = client.getAsString(new OID(".1.3.6.1.2.1.43.5.1.1.17.1"));
			String printerMac = client.getAsString(new OID(".1.3.6.1.2.1.2.2.1.6.2"));
			String printerStatus = client.getAsString(new OID(".1.3.6.1.2.1.43.16.5.1.2.1.2"));			
			int printerCounterColor = Integer.parseInt(client.getAsString(new OID(".1.3.6.1.4.1.1129.2.3.50.1.3.21.6.1.2.1.1")));
			int printerCounterColorMono = Integer.parseInt(client.getAsString(new OID(".1.3.6.1.4.1.1129.2.3.50.1.3.21.6.1.2.1.2")));
			int printerCounterMono = Integer.parseInt(client.getAsString(new OID(".1.3.6.1.4.1.1129.2.3.50.1.3.21.6.1.2.1.3")));
			String printerTonerBLevel = client.getAsString(new OID(".1.3.6.1.2.1.43.11.1.1.9.1.1"));
			String printerTonerCLevel = client.getAsString(new OID(".1.3.6.1.2.1.43.11.1.1.9.1.2"));
			String printerTonerMLevel = client.getAsString(new OID(".1.3.6.1.2.1.43.11.1.1.9.1.3"));
			String printerTonerYLevel = client.getAsString(new OID(".1.3.6.1.2.1.43.11.1.1.9.1.4"));	
			System.out.println("Modelo: " + printerModel);
			System.out.println("Serial: " + printerSerial);
			System.out.println("MAC Address: " + printerMac);
			System.out.println("Estado Atual: " + printerStatus);			
			System.out.println("Contador Color Atual: " + (printerCounterColor + printerCounterColorMono));
			System.out.println("Contador Mono Atual: " + printerCounterMono);
			System.out.println("Toner Black Level: " + printerTonerBLevel + " %");
			System.out.println("Toner Ciano Level: " + printerTonerCLevel + " %");
			System.out.println("Toner Magenta Level: " + printerTonerMLevel + " %");
			System.out.println("Toner Yellow Level: " + printerTonerYLevel + " %");
		}
		else if  (selectMIB.contains("EPSON"))
		{
			String printerModel = client.getAsString(new OID(".1.3.6.1.4.1.1248.1.2.2.1.1.1.2.1"));
			String printerSerial = client.getAsString(new OID(".1.3.6.1.4.1.1248.1.2.2.2.1.1.2.1.2"));
			String printerCounterColor = client.getAsString(new OID(".1.3.6.1.4.1.1248.1.2.2.27.1.1.3.1.1"));
			String printerCounterMono = client.getAsString(new OID(".1.3.6.1.4.1.1248.1.2.2.27.1.1.4.1.1"));
			String printerTonerBLevel = client.getAsString(new OID(".1.3.6.1.2.1.43.11.1.1.9.1.1"));
			String printerTonerCLevel = client.getAsString(new OID(".1.3.6.1.2.1.43.11.1.1.9.1.2"));
			String printerTonerMLevel = client.getAsString(new OID(".1.3.6.1.2.1.43.11.1.1.9.1.3"));
			String printerTonerYLevel = client.getAsString(new OID(".1.3.6.1.2.1.43.11.1.1.9.1.4"));
			
			/**Verificar*/
			String printerStatus = client.getAsString(new OID(".1.3.6.1.2.1.43.16.5.1.2.1.1"));
			
			System.out.println("Modelo: " + printerModel);
			System.out.println("Serial: " + printerSerial);
			System.out.println("Contador Colorido Atual: " + printerCounterColor);
			System.out.println("Contador Monocromático Atual: " + printerCounterMono);			
			System.out.println("Estado Atual: " + printerStatus);
			System.out.println("Toner Black Level: " + printerTonerBLevel + " %");
			System.out.println("Toner Ciano Level: " + printerTonerCLevel + " %");
			System.out.println("Toner Magenta Level: " + printerTonerMLevel + " %");
			System.out.println("Toner Yellow Level: " + printerTonerYLevel + " %");
		}				
	}
	
	public static void GetContador(String ips) throws IOException 
	{
		AgenteSnmp client = new AgenteSnmp(ips + "/161");
		client.start();
		
		String selectMIB = client.getAsString(new OID(".1.3.6.1.2.1.1.1.0")).toString();
		
		if (selectMIB.contains("Lexmark"))
		{		
			String printerModel = client.getAsString(new OID(".1.3.6.1.4.1.641.6.2.3.1.4.1"));
			String printerSerial = client.getAsString(new OID("..1.3.6.1.4.1.641.2.1.2.1.6.1"));
			String printerCounter;
			if (printerModel.contentEquals("Lexmark MX410de"))
			{
				printerCounter = client.getAsString(new OID(".1.3.6.1.2.1.43.10.2.1.4.1.1"));
			}
			else 
			{
				printerCounter = client.getAsString(new OID(".1.3.6.1.4.1.641.2.1.5.1.0"));
			}
			System.out.println("Modelo: " + printerModel);
			System.out.println("Serial: " + printerSerial);
			System.out.println("Contador Atual: " + printerCounter);			
		}
		else if (selectMIB.contains("Oki Data Corporation"))
		{
			String printerModel = client.getAsString(new OID(".1.3.6.1.4.1.1129.2.3.50.1.2.3.1.3.1.1"));
			String printerSerial = client.getAsString(new OID(".1.3.6.1.2.1.43.5.1.1.17.1"));
			int printerCounterColor = Integer.parseInt(client.getAsString(new OID(".1.3.6.1.4.1.1129.2.3.50.1.3.21.6.1.2.1.1")));
			int printerCounterColorMono = Integer.parseInt(client.getAsString(new OID(".1.3.6.1.4.1.1129.2.3.50.1.3.21.6.1.2.1.2")));
			int printerCounterMono = Integer.parseInt(client.getAsString(new OID(".1.3.6.1.4.1.1129.2.3.50.1.3.21.6.1.2.1.3")));
			System.out.println("Modelo: " + printerModel);
			System.out.println("Serial: " + printerSerial);
			System.out.println("Contador Color Atual: " + (printerCounterColor + printerCounterColorMono));
			System.out.println("Contador Mono Atual: " + printerCounterMono);
		}
		else if  (selectMIB.contains("EPSON"))
		{
			String printerModel = client.getAsString(new OID(".1.3.6.1.4.1.1248.1.2.2.1.1.1.2.1"));
			String printerSerial = client.getAsString(new OID(".1.3.6.1.4.1.1248.1.2.2.2.1.1.2.1.2"));
			int printerCounterColor = 0;
			int printerCounterMono = 0;
			if (printerModel.contains("WF-M5799"))
			{
				printerCounterMono = Integer.parseInt(client.getAsString(new OID(".1.3.6.1.4.1.1248.1.2.2.27.1.1.3.1.1")));
			}
			else if (printerModel.contains("WF-5690"))
			{
				printerCounterColor = Integer.parseInt(client.getAsString(new OID(".1.3.6.1.4.1.1248.1.2.2.27.1.1.3.1.1")));
				printerCounterMono = Integer.parseInt(client.getAsString(new OID(".1.3.6.1.4.1.1248.1.2.2.27.1.1.4.1.1")));
			}
			
			if (printerModel.contains("WF-M5799"))
			{
				System.out.println("Modelo: EPSON " + printerModel);
				System.out.println("Serial: " + printerSerial);
				System.out.println("Contador Monocromático Atual: " + printerCounterMono);
			}
			else if (printerModel.contains("WF-5690"))
			{
				System.out.println("Modelo: EPSON " + printerModel);
				System.out.println("Serial: " + printerSerial);
				System.out.println("Contador Colorido Atual: " + printerCounterColor);
				System.out.println("Contador Monocromático Atual: " + printerCounterMono);
			}						
		}
		
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

}