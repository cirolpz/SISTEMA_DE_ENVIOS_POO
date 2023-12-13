package Amazing;

public class Utilitario extends Transporte {
    protected double valorExtra = 0;

    public Utilitario(String patente, int volMax, int valorViaje, double valorExtra) {
        super(patente, volMax, valorViaje);
        this.valorExtra = valorExtra;
    }
    @Override
    public void cargarPaquetes(Paquete paquete) {
    	
    	if (paquete instanceof PaqueteEspecial) {
    		if(volMax - paquete.obtenerVolumen() >= 0) { //Al agregar un paquete ocupa un volumen, es decir le quita volumen al vol max del transporte.
    			volMax -= paquete.obtenerVolumen();
                paquetesCargados.add(paquete);
                paquete.marcarEntregado();
    		}
    	}
    		    		
        if (paquete instanceof PaqueteOrdinario) {
        	if(volMax - paquete.obtenerVolumen() >= 0) {
        		volMax -= paquete.obtenerVolumen();
                paquetesCargados.add(paquete);
                paquete.marcarEntregado();
        	}
        }
 
    }

    @Override
    public double calcularCostoEntrega() {
        if (transporteEstaVacio()) {
            throw new RuntimeException("El transporte está vacío");
        }

        if (cantidadDePaquetesEnTransporte() <= 3) {
            return valorViaje;
        } else {
            return valorViaje + valorExtra;
        }
    }

    private boolean transporteEstaVacio() {
        return paquetesCargados.isEmpty();
    }

    private int cantidadDePaquetesEnTransporte() {
        return paquetesCargados.size();
    }

    @Override
    public String toString() {

        return " patente de utilitario, paquetes cargados:" + paquetesCargados.size() + " y el valor del viaje: $" + valorViaje + '\n';

    }
}