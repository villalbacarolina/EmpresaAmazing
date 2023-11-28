package Amazing;

public class Cliente {
	
	private String nombre, direccion;
	private int dni;
	
	public Cliente(String nombre, String direccion, int dni) {
		if(nombre.equals("") || direccion.equals("") 
		   || dni<10000000)
			throw new RuntimeException("ERROR: datos inválidos");
		this.nombre    = nombre;
		this.direccion = direccion;
		this.dni       = dni;
	}
	
	//datos inmutables como int o String pueden ser retornados
	public String nombre() {
		return nombre;
	}
	public String direccion() {
		return direccion;
	}
	public int dni() {
		return dni;
	}

}
