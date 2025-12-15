package teste;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import servico.CalcularMedia;
import servico.DoubleArray;

public class Main {
	public static void main(String[] args) throws MalformedURLException {
		URL url = new URL("http://localhost:8085/servico?wsdl");
		
		QName qName = new QName("http://servico/", "CalcularMediaImplService");
		DoubleArray notas = new DoubleArray();
		notas.getItem().add(2.0);
		notas.getItem().add(2.0);
		
		Service service = Service.create(url, qName);
		
		CalcularMedia calculadorDeMedia = service.getPort(CalcularMedia.class);
		
		double media = calculadorDeMedia.calculaMedia(notas);
		
		System.out.println(media);
	}
}
