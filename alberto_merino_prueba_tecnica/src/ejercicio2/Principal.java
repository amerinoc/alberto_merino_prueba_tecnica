package ejercicio2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Principal {
	
	public static void main(String[] args) {
		// Enunciado e inicializacion de veriables
		System.out.println("================================================================================================================================");
		System.out.println("Este es el segundo ejercicio en la prueba t�cnica. Se trata de un programa para ordenar los n�meros de mayor a menor.");
		System.out.println("Por favor introduce tantos n�meros como quieras. Para terminar de introducir n�meros pulsa Intro sin escribir nada");
		System.out.println("================================================================================================================================");
		List<Double> listaNumeros = new ArrayList<>();
		Scanner scan = new Scanner(System.in);
		String linea = null;
	
		while(!(linea = scan.nextLine()).isBlank()) {
			if(compruebaNumero(linea) != null)
				listaNumeros.add(compruebaNumero(linea));
		} // fin bucle adicion numeros
		
		// Se ordenan los numeros y semuestran en pantalla
		Collections.sort(listaNumeros, Collections.reverseOrder());
		System.out.println("=====================================================================================================");
		System.out.println("Lista ordenada descendentemente: ");
		System.out.println(listaNumeros);
		System.out.println("=====================================================================================================");
		
		
	} // fin metodo main()
	
	// metodo necesario para filtrar contenido que no sean numeros
	public static Double compruebaNumero(String linea) {
		try {	
			Double aux = Double.parseDouble(linea);
			return aux;
		} catch (NumberFormatException e) {
			System.out.println("La l�nea introducida no es un n�mero. Por favor, aseg�rate de que introduces caracteres num�ricos.");
			return null;
		} // fin bloque try/catch
	} // fin metodo compruebaNumero

} // fin clase Principal()
