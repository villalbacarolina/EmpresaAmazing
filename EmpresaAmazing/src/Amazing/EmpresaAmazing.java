package Amazing;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class EmpresaAmazing implements IEmpresa {
	private String cuit;
	private HashMap<String, Transporte> transportes;
	private HashMap<Integer, Pedido> pedidos;
	private int codPaquete;
	private double facturacion;
	private HashSet<Integer> clientes;
	
	public EmpresaAmazing(String cuit) {
		if (cuit.equals(""))
			throw new RuntimeException("Error: cuit invalido.");
		
		this.cuit = cuit;
		this.transportes = new HashMap<String, Transporte>();
		this.pedidos = new HashMap<Integer, Pedido>();
		clientes = new HashSet<Integer>();
	}

	
	/**
	 * Registra un nuevo transporte tipo Automovil en el sistema con los siguientes 
	 * datos correspondiente a todo transporte:
	 *  - patente, 
	 *  - volumen maximo de carga
	 *  - valor del viaje (que cobrara a la empresa)
	 *  
	 * Adem�s por ser Automovil se proporciona el dato:
	 *  - cantidad maxima de paquetes que transporta
	 *  
	 * Si esa patente ya esta en el sistema se debe generar una  excepcion.
	 */
	//agregar verificación en algún 
	public void registrarAutomovil(String patente, int volMax, int valorViaje, int maxPaq) {
		if (existeTransporte(patente))
			throw new RuntimeException("ERROR: la patente ya esta registrada en el sistema");

		Automovil automovil = new Automovil(patente, volMax, valorViaje, maxPaq);
		transportes.put(patente, automovil);
	}

	
	/**
	 * Registra un nuevo transporte tipo Utilitario en el sistema con los  
	 * datos correspondiente a todo transporte y ademas:
	 * 
	 *  - un valor extra que cobra a la empresa si superan los 10 paquetes.
	 * 
	 * Si esa patente ya esta en el sistema se debe generar una  excepcion.
	 */
	public void registrarUtilitario(String patente, int volMax, int valorViaje, int valorExtra) {
		if (existeTransporte(patente))
			throw new RuntimeException("ERROR: la patente ya esta registrada en el sistema");
		
		Utilitario utilitario = new Utilitario(patente, volMax, valorViaje, valorExtra);
		transportes.put(patente, utilitario);
	}

	
	/**
	 * Registra un nuevo transporte tipo Camion en el sistema con los  
	 * datos correspondiente a todo transporte y ademas:
	 * 
	 *  - un valor adicional que se multiplica por la cantidad de paquetes que le carguen.
	 * 
	 * Si esa patente ya esta en el sistema se debe generar una  excepcion.
	 */
	public void registrarCamion(String patente, int volMax, int valorViaje, int adicXPaq) {
		if (existeTransporte(patente))
			throw new RuntimeException("ERROR: la patente ya esta registrada en el sistema");
		
		Camion camion = new Camion(patente, volMax, valorViaje, adicXPaq);
		transportes.put(patente, camion);
	}

	
	/**
	 * Se registra un nuevo pedido en el sistema proporcionando los siguientes datos:
	 * - el nombre del cliente que lo solicita 
	 * - su direccion 
	 * - su dni
	 * 
	 * El sistema le asigna un numero de pedido unico y crea un carrito de ventas vacio.
	 * Devuelve el numero de pedido asignado.
	 * 
	 */
	public int registrarPedido(String cliente, String direccion, int dni) {
		validarCliente(dni);
		
		int codPedido = pedidos.size()+1;
		String nombre = cliente;
		
		clientes.add(dni);
		Cliente cli = new Cliente(nombre,direccion,dni);
		Pedido pedido = new Pedido(codPedido, cli);
		
		pedidos.put(codPedido, pedido);
		return codPedido;
	}
	

	/**
	 * Se registra la compra de un producto, el cual se agregara al carrito del pedido dado 
	 * como un paquete de tipo ordinario. 
	 * 
	 * Se ingresan los datos necesario para agregarlo:
	 *  - pedido al que corresponde agregarlo
	 *  - volumen del paquete a agregar
	 *  - precio del producto que contiene el paquete.
	 *  
	 *  Ademas por ser un paquete de tipo ordinario:
	 *  - costo del envio
	 * 
	 *  Si ese pedido no esta registrado en el sistema o ya está finalizado
	 *  se debe generar una  excepcion.
	 * 
	 * devuelve el codigo de paquete unico.
	 * 
	 */
	public int agregarPaquete(int codPedido, int volumen, int precio, int costoEnvio) {
		if(!existePedido(codPedido))
			throw new RuntimeException("ERROR: el pedido ingresado no esta registrado");
		
		Pedido pedido = buscarPedido(codPedido);
		codPaquete++;
		Ordinario paqOrd = new Ordinario(codPaquete, volumen, precio, costoEnvio);
		pedido.agregarPaquete((Paquete) paqOrd);
		return codPaquete;
	}

	
	/**
	 * Se registra la compra de un producto que se agregara al carrito del pedido dado 
	 * como paquete de tipo especial. 
	 * 
	 * Se ingresan los datos necesario para agregarlo:
	 *  - pedido al que corresponde agregarlo
	 *  - volumen del paquete a agregar
	 *  - precio del producto que contiene el paquete.
	 *  
	 *  Ademas por ser un paquete de tipo especial:
	 *  - porcentaje adicional (que se calcula y suma a su precio)
	 *  - adicional (se suma si el paquete tiene volumen>3000)
	 * 
	 *  Si ese pedido no esta registrado en el sistema o ya está finalizado
	 *  se debe generar una  excepcion.
	 * 
	 * devuelve el codigo de paquete unico.
	 * 
	 */
	public int agregarPaquete(int codPedido, int volumen, int precio, int porcentaje, int adicional) {
		if(!existePedido(codPedido))
			throw new RuntimeException("ERROR: el pedido ingresado no esta registrado");

		codPaquete++;
		Pedido pedido = buscarPedido(codPedido);
		Especial paqEsp = new Especial(codPaquete, volumen, precio,  porcentaje, adicional);
		pedido.agregarPaquete((Paquete) paqEsp);
		return codPaquete;
	}
	
	
	/**
	 * quita un paquete del pedido dado su codigo unico de paquete.
	 * 
	 * Demostrar la complejidad en terminos de O grande.
	 */
	public boolean quitarPaquete(int codPaquete) {
		for (Pedido p : pedidos.values())
			if (p.quitarPaquete(codPaquete))
				return true;
		throw new RuntimeException("ERROR: el paquete no está registrado.");
	}

	
	/**
	 * Se registra la finalizacion de un pedido registrado en la empresa, 
	 * dado su codigo.
	 * 
	 * Si ese codigo no esta en el sistema se debe generar una  excepcion.
	 *
	 */
	public double cerrarPedido(int codPedido) {
		if(!existePedido(codPedido))
			throw new RuntimeException("ERROR: el pedido ingresado no esta registrado");

		Pedido pedido = buscarPedido(codPedido);
		facturacion  += pedido.cerrarPedido();
		return pedido.cerrarPedido();
	}

	
	/**
	 * Se registra la carga de un transporte registrado en la empresa, dada su patente.
	 * 
	 * Devuelve un String con forma de listado donde cada renglon representa un 
	 * paquete cargado.
	 * Cada renglon debe respetar el siguiente formato: 
	 *      " + [ NroPedido - codPaquete ] direccion"
	 * por ejemplo:
	 *      " + [ 1002 - 101 ] Gutierrez 1147"
	 *      
	 * Si esa patente no esta en el sistema se debe generar una  excepcion.
	 * 
	 * Los paquetes que se cargan deben pertenecer a pedidos que se hayan terminado.
	 * 
	 * Si esta finalizado y no se encontro paquetes a cargar devuelve [].
	 *
	 */
	public String cargarTransporte(String patente) {	
		if (!existeTransporte(patente))					
			throw new RuntimeException("ERROR: El transporte ingresado no esta registrado");
		
		StringBuilder paquetesCargados = new StringBuilder();			
		Transporte transporte = buscarTransporte(patente);
		
		for (Pedido p : pedidos.values())
			p.entregarPaquetesATransporte(transporte);
	
		paquetesCargados.append(transporte.toString());
		return paquetesCargados.toString();
	}
	
	
	/**
	 * Se registra el costo del viaje de un transporte dado su patente
	 * Este costo es el que cobra el transporte (a la empresa) por entregar 
	 * la carga una vez que fue cargado con los paquetes.
	 * 
	 * Una vez cargado, aunque no se haya podido completar, el transporte 
	 * reparte los paquetes cargados.
	 *  
	 * Se devuelve el valor del viaje segun lo indicado en cada tipo de transporte.
	 * Cada tipo de transporte tiene su forma de calcular el costo del viaje.
	 *  
	 * Si esa patente no esta en el sistema se debe generar una excepcion.
	 * Si el transporte no esta cargado genera un excepcion.
	 * 
	 * En O(1)
	 */
	public double costoEntrega(String patente) {
		if (!existeTransporte(patente))
			throw new RuntimeException("ERROR: la patente no esta registrada en el sistema.");
		
		Transporte transporte = buscarTransporte(patente);
		return transporte.calcularCosto();
	}	


	/**
	 * Devuelve los pedidos cerrados y que no fueron entregados en su totalidad. 
	 * O sea, que tienen paquetes sin entregar.
	 * 
	 * Devuelve un diccionario cuya clave es el codigo del pedido y el valor es el nombre del 
	 * cliente que lo pidio.
	 * 
	 */
	public Map<Integer, String> pedidosNoEntregados() {
		Map<Integer, String> pedidos = new HashMap<>();
		for (Pedido p : this.pedidos.values())
			if (p.tienePaquetesSinEntregar())
				pedidos.put(p.consultarCodPedido(), p.consultarNombre());
		return pedidos;
	}

	
	/**
	 * Devuelve la suma del precio facturado de todos los pedidos cerrados.
	 * Si el tipo de servicio es invalido, debe generar una excepcion.
	 * 
	 * Se debe realizar esta operacion en O(1).
	 */
	public double facturacionTotalPedidosCerrados() {
		return facturacion;
	}
	
	
	/**
	 * Se consideran transportes identicos a 2 transportes cargados con:
	 *   - distinta patente, 
	 *   - mismo tipo y 
	 *   - la misma carga.
	 * Se considera misma carga al tener la misma cantidad de paquetes con las mismas caracteristicas:
	 *   - mismo volumen, 
	 *   - misma clase y 
	 *   - mismo costoDeEnvio.
	 * poner ejemplo en algun lado.
	 */
	/**
	 * Se consideran transportes identicos a 2 transportes cargados con:
	 *   - distinta patente, 
	 *   - mismo tipo y 
	 *   - la misma carga.
	 * Se considera misma carga al tener la misma cantidad de paquetes con las 
	 * mismas caracteristicas:
	 *   - mismo volumen, 
	 *   - mismo tipo 
	 *   - mismo precio y
	 *   - mismos atributos según el tipo de Paquete
	 *   VER EJEMPLO EN ENUNCIADO
	 */
	public boolean hayTransportesIdenticos() {
		for (Transporte t : transportes.values())
			for (Transporte t2 : transportes.values())
				if (t.sonIdenticos(t2))
					return true;
			
		return false;
	}
	
	@Override
	public String toString() {
		return cuit;
	}
	
	
	// Metodos auxiliares//

	private boolean existeTransporte(String patente) {
		return transportes.containsKey(patente);
	}

	private boolean existePedido(int codPedido) {
		return pedidos.containsKey(codPedido);
	}

	private Pedido buscarPedido(int codPedido) {
		return pedidos.get(codPedido);
	}

	private Transporte buscarTransporte(String patente) {
		return transportes.get(patente);
	}
	
	private void validarCliente(int dni) {
		if ( clientes.contains(dni) )
			throw new RuntimeException("ERROR: el cliente ingresado ya está registrado");
	}
}