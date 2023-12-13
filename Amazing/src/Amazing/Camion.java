package Amazing;

public class Camion extends Transporte {
    private double adicXPaq;

    public Camion(String patente, int volMax, int valorViaje, double adicXPaq) {
        super(patente, volMax, valorViaje);
        this.adicXPaq = adicXPaq;
    }

    @Override
    public void cargarPaquetes(Paquete paquete) {
        if (esPaqueteEspecialValido(paquete)) {
            volMax -= paquete.obtenerVolumen();
            paquetesCargados.add(paquete);
            paquete.marcarEntregado();
        }
    }

    private boolean esPaqueteEspecialValido(Paquete paquete) {
        if (paquete instanceof PaqueteEspecial) {
            if (volMax - paquete.obtenerVolumen() >= 0 && paquete.obtenerVolumen() > 2000) {
                return true;
            }
        }
        return false;
    }


    @Override
    public double calcularCostoEntrega() {
        return adicXPaq*paquetesCargados.size()+valorViaje; 
    }

    @Override
    public String toString() {

        return " patente de camion, paquetes cargados:" + paquetesCargados.size() + " y el valor del viaje: $" + valorViaje + '\n';


    }
}