package Amazing;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class Transporte {
	private HashMap<Integer, Paquete> paquetes;
	protected double valorXViaje;
	protected String patente;
	protected int volMax;
	private StringBuilder paquetesCargados;

	public Transporte(String patente, int volMax, double valorXviaje) {
		if (patente.equals("") || volMax < 0 || valorXviaje < 0)
			throw new RuntimeException("ERROR: datos invalidos");
		
		this.paquetes = new HashMap<Integer, Paquete>();
		this.patente = patente;
		this.volMax = volMax;
		this.valorXViaje = valorXviaje;
		this.paquetesCargados = new StringBuilder();
	}

	public void cargar(int codPaquete, Paquete p, int codPedido, String direccion) {
		if (paquetes.containsKey(codPaquete))
			return;
		
		paquetes.put(codPaquete, p);
		this.volMax -= p.cargarPaquete();
		paquetesCargados.append(" + [ ").append(codPedido).append(" - ").append(codPaquete).append(" ] ").append(direccion).append("\n");
	}

	public int consultarVolumenMax() {
		return volMax;
	}

	public int consultarCantPaq() {
		return paquetes.size();
	}

	public double consultarValorXViaje() {
		return valorXViaje;
	}

	public String toString() {
		return paquetesCargados.toString();
	}
	
	public abstract void agregarPaquete(int idPaquete, Paquete paq, int codPedido, String direccion);
	
	public abstract double calcularCosto();
	
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
	 *   - mismos atributos seg�n el tipo de Paquete
	 *   Devuelve True si encuentra dos transportes cargados que son iguales, considerando que
	 *   ser�n iguales si:
	 *   - Tienen diferente patente, pero
	 *   - son de la misma clase y
	 *   - tienen carga id�ntica,
	 *   � aunque el volumen m�ximo sea distinto.
	 *   La carga ser� id�ntica si tienen la misma cantidad de paquetes y los paquetes coinciden 
	 *   en volumen, clase y precio, adem�s de los atributos propios de cada clase de paquete.
	 */
	public boolean sonIdenticos(Transporte t) {
		if (consultarCantPaq() == 0 || t.tienenMismaPatente(this.patente)) 
	        return false;

	    boolean cargasIdenticas = true;
	    Iterator<Map.Entry<Integer, Paquete>> iterator = this.paquetes.entrySet().iterator();
	    while (iterator.hasNext()) {
	        Map.Entry<Integer, Paquete> entry = iterator.next();
	        Paquete p = entry.getValue();
	        
	        cargasIdenticas &= t.tienenCargasIdenticas(p);
	    }
	    return (   t.tienenMismaCantidadDePaq(paquetes.size())
	            && this.tienenMismoTipo(t)
	            && cargasIdenticas );
	}
	
	private boolean tienenMismaPatente(String patente) {
		return patente.equals(this.patente);
	}

	private boolean tienenCargasIdenticas(Paquete paq2) {
		boolean cargasIdenticas = true;
		for (Paquete paq : this.paquetes.values()) {
			cargasIdenticas |= paq2.tienenMismasCaracteristicas(paq);
		}
		return cargasIdenticas;
	}

	private boolean tienenMismaCantidadDePaq(int cantPaq2) {
		return paquetes.size() == cantPaq2;
	}
	
	public abstract boolean tienenMismoTipo(Transporte t);
	
	//con equals
	
//	boolean mismoTransporte(Transporte otroTransporte) {
//	if(mismaClase(otroTransporte)) {
//		if(otroTransporte.mismaCantidadPaquetes( paquetes.size() ))
//			if(otroTransporte.mismaCarga(paquetes.values()))
//				return true;
//	}
//	return false;
//}
//
//private boolean mismaClase(Transporte otroTransporte) {
//	return this.getClass() == otroTransporte.getClass();
//}
//
//private boolean mismaCarga(Collection<Paquete> collection) {
//	boolean todosIguales = true;
//	for(Paquete paq: this.paquetes.values())
//		for(Paquete otroPaq: collection)
//			todosIguales = todosIguales && paq.equals(otroPaq);
//	return todosIguales;
//}
//
//private boolean mismaCantidadPaquetes(int otraCantPaquetes) {
//	int cantPaquetes = this.paquetes.size();
//	return cantPaquetes == otraCantPaquetes;
//}
}