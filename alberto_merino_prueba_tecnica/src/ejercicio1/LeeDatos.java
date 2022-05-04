package ejercicio1;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONArray;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

public class LeeDatos extends Conexion{
	
	// lista para guardar los contactos
	static List<Contacto> listaContactos = new ArrayList<Contacto>();
	static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) {
		datosServidor();
		
		
	} // fin metodo main()
	
	public static void datosServidor() {
		try {
			HttpURLConnection conexion = Conexion.conectarServicio();
			// Se comprueba si la conexion es correcta y se procede a acceder a los datos
			if(conexion != null) {		
				// Ahora creo un flujo de datos del que extraigo la informacion de la web
				String salida = "";
				BufferedReader buffreader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
				// Almaceno todos los datos en una variable StringBuilder
				StringBuilder sBuilder = new StringBuilder();
				while ((salida = buffreader.readLine()) != null) {
					sBuilder.append(salida);
					
					
				} // fin bucle while()
				guardaObjetos(sBuilder, listaContactos);
				
				//System.out.println(sBuilder.toString());
				conexion.disconnect();
			} // fin if conexion no es nula	
		} catch (IOException e) {
			e.printStackTrace();
		} // fin bloque try/catch
				
	} // fin metodo datosServidor()
	
	public static void guardaObjetos(StringBuilder sBuilder, List<Contacto> listaContactos) {
		// tras comprobar que la conexion es correcta se guarda la informacion de todos los contactos para poder trabajar con ella despues
		// se transforma el StringBuilder en un array de JSON desde el que sacar los datos a un objeto
		
		// ESTE METODO HACE USO DE UNA LIBRERIA IMPORTADA (json-20200518.jar) SE PUEDE ENCONTRAR EN LA CARPETA LIB DEL PROYECTO
		JSONArray jsonContactos = new JSONArray(sBuilder.toString());
		for(int i = 0; i < jsonContactos.length(); i++) {
			Contacto cont = new Contacto();
			cont.setNombre(jsonContactos.getJSONObject(i).get("name").toString());		
			cont.setUsuario(jsonContactos.getJSONObject(i).get("username").toString());
			cont.setEmail(jsonContactos.getJSONObject(i).get("email").toString());
			listaContactos.add(cont);
		} // fin bucle de todos los contactos
		
		// se muesta el menu principal
		menu();
		
		
		
	} // fin metodo guardaObjetos()
	
	private static void muestraTodosContactos() {
		for(Contacto contacto : listaContactos) {
			System.out.println("[" + contacto.getNombre() + ", " + contacto.getUsuario() + ", " + contacto.getEmail() + "]");
		}		
		volverMenu();		
	} // fin metodo muestraTodosContactos()
	
	// ESTE METODO HACE USO DE UNA LIBRERIA IMPORTADA (snakeyaml-1.29.jar) SE PUEDE ENCONTRAR EN LA CARPETA LIB DEL PROYECTO
	private static void exportarYAML() {
		try {
			// creacion hashmap padre
			Map<String, Object> datosContacto = new HashMap<String, Object>();
			// inicializacion de objeto yaml personalizado para que saque la informacion segun la estructura que sigue yaml
			DumperOptions options = new DumperOptions();
			options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
			options.setPrettyFlow(true);
			Yaml yaml = new Yaml(options);
			// por defecto se crea el archivo yaml en la raíz del proyecto
			FileWriter writer = new FileWriter("archivoYAML.yaml");
			
			for(int i = 0; i < listaContactos.size(); i++ ) {
				// hashmap auxiliar para poder anidar la informacion
				Map <String, Object> mapAux = new HashMap<>();
				Contacto contacto = listaContactos.get(i);
				mapAux.put("name", contacto.getNombre());
				mapAux.put("username", contacto.getUsuario());
				mapAux.put("email", contacto.getEmail());
				datosContacto.put("contacto", mapAux);
				yaml.dump(datosContacto, writer);
			} // fin bucle añadir contactos al parser de yaml
			System.out.println("El archivo yaml se ha creado con éxito. Puedes localizarlo en la raíz del proyecto.");
			writer.close();
			volverMenu();	
		} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Ha ocurrido un error a la hora de crear el archivo. Por favor, vuelve a intentarlo.");
		} // fin bloque try/catch				
	} // fin metodo exportarYAML() 
	

	private static void muestraUsuario() {
		System.out.println("Por favor, indica el usuario del que quieres extraer los datos: ");
		String usuario = scan.next();
		boolean encuentra = false;
		for(Contacto contacto : listaContactos) {
			if(contacto.getUsuario().equalsIgnoreCase(usuario)) { 
				System.out.println("[" + contacto.getNombre() + ", " + contacto.getUsuario() + ", " + contacto.getEmail() + "]");
				encuentra = true;
				volverMenu();
				break;
			} // fin if comprobacion nombre usuario
		} // fin bucle paso por los contactos
		if(!encuentra) {
			System.out.println("El usuario introducido no existe. Por favor, inténtelo de nuevo.");
			volverMenu();
		} // fin comprobacion no existe el usuario
		
	} // fin metodo muestraUsuario()

	// metodo para mostrar menu
	public static void menu(){
		System.out.println("=========================================================================");
		System.out.println("Bienvenido a la prueba técnica de Alberto Merino. ¿Qué desea hacer?");
		System.out.println("1. Mostrar todos los contactos.");
		System.out.println("2. Exportar todos los contactos a un fichero YAML.");
		System.out.println("3. Buscar un contacto por nombre de usuario");
		System.out.println("4. Salir");
		System.out.println("=========================================================================");
		int opcion = scan.nextInt();
		// switch opciones menu
				switch (opcion) {
				case 1: {
					muestraTodosContactos();
					break;
				}
				case 2: {
					exportarYAML();
					break;
				}
				case 3: {
					muestraUsuario();
					break;
				}
				default:
					System.out.println("La opción elegida no es correcta. Por favor elija una opción del menú.");
					menu();
				} // fin bloque switch/case		
				
	} // fin metodo menu()
	

	// metodo reutilizable para saber si el ususario quiere seguir usando la aplicacion
	public static void volverMenu() {
		String volver = "";
		do {
			System.out.println("=========================================================================");
			System.out.println("¿Desea volver al menu? (S/N)");
			volver = scan.next();
			
		} while(!volver.equalsIgnoreCase("S") && !volver.equalsIgnoreCase("N"));
		
		if(volver.equalsIgnoreCase("S")) {
			menu();
		} else {
			 System.out.println("Gracias por usar el programa. Que tengas buen día.");
		} // fin comprobacion pregunta
	}

} // fin clase LeeDatos()
