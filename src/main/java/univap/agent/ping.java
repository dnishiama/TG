package univap.agent;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ch.qos.logback.classic.Logger;

public class ping {
	public static void main(String[] args) throws UnknownHostException, IOException {
		Date hoje = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String check = dateFormat.format(hoje);
		System.out.println("Data Agora: "+check);
		
		long contador;
		
	}
}
