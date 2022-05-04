package ejercicio1;

import java.util.ArrayList;

public class Contacto {
	String nombre, usuario, email;
	
	public Contacto() {}
	public Contacto(String nombre, String usuario, String email) {
		
		this.nombre = nombre;
		this.usuario = usuario;
		this.email = email;

	} // fin constructores
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "Contacto [nombre=" + nombre + ", usuario=" + usuario + ", email=" + email + "]";
	} // fin metodo toString()
	
	
	
} // fin clase Contacto()
