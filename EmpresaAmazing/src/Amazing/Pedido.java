package Amazing;

import java.util.LinkedList;


public class Pedido {
	private int codPedido;
	private boolean abierto;
	private Cliente cliente;
	private LinkedList<Paquete> carrito;
	private double importeTotal;

	public Pedido(int codPedido, Cliente cliente) {
		this.codPedido= codPedido;
		this.abierto  = true;
		this.cliente  = cliente;
		this.carrito  = new LinkedList< Paquete>();
	}

	public int consultarCodPedido() {
		return codPedido;
	}
	
	public String consultarNombre() {
		return cliente.nombre();
	}

	public void agregarPaquete(Paquete p) {
		if (!abierto)
			throw new RuntimeException("ERROR: el pedido ya fue cerrado");
		
		carrito.add(p);
		importeTotal += p.calcularImporte();
	}

	public boolean quitarPaquete(int codPaquete) {
		for (Paquete paq: carrito) 
	        if (paq.codPaquete() == codPaquete) {
	            carrito.remove(codPaquete);
	            return true;
	        }
	    return false;
	}

	public double cerrarPedido() {
		abierto = false;
		return importeTotal;
	}
	
	public boolean estaAbierto() {
		return abierto;
	}
	
	public void entregarPaquetesATransporte(Transporte t) {
		if (abierto)
			return;
		for(Paquete paq: carrito)
			t.agregarPaquete(paq.codPaquete(), paq, codPedido, cliente.direccion());
		
//		Iterator<Map.Entry<Integer, Paquete>> iterator = carrito.entrySet().iterator();
//		while (iterator.hasNext()) {
//		    Map.Entry<Integer, Paquete> entry = iterator.next();
//		    int codPaquete = entry.getKey();
//		    Paquete paquete= entry.getValue();
//		    
//		    t.agregarPaquete(codPaquete, paquete, codPedido, cliente.direccion());
//		}
	}
	
	public boolean tienePaquetesSinEntregar() {
		boolean tienePaquetes=false;
		for (Paquete paq : carrito) 
			tienePaquetes = tienePaquetes || (!paq.cargado() && !abierto);
		return tienePaquetes;
	}

	public boolean tienenMismoDni(int dni) {
		return dni==cliente.dni();
	}
}
