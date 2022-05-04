package ejercicio1;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;	

public class Conexion {
	
	public static HttpURLConnection conectarServicio() {
		HttpURLConnection con = null;
		Scanner scanner = new Scanner(System.in);
		// Primero establezco una conexion con el servicio
		try {
			System.out.println("Bienvenido, antes de comenzar, se necesita introducir una URL, por favor indícala: ");
			String url = scanner.next();
		// Creo un objeto URL para alojar la direccion
			URL enlace = new URL(url);
		// Tambien añado el metodo de conexion y compruebo que funcione
			con = (HttpURLConnection) enlace.openConnection();
			con.setRequestMethod("GET");
		// Si el servidor no devuelve un OK se imprime el codigo de error y se para el programa
			if(con.getResponseCode() != 200) {
				System.out.println("La conexión no ha sido posible de realizar. Error: " + con.getResponseCode());
				return null;
			} // fin if codigo de respuesta no es 200
			
		
		
			
		} catch (MalformedURLException e) {
			System.out.println("Ha ocurrido un error al recoger el enlace, comprueba que sea el correcto.");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} // fin bloque try/catch
		
		return con;
		
	} // fin metodo conectarServicio()
	
	

} // fin clase Conexion()
