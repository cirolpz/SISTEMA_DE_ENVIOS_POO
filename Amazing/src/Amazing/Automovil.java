package Amazing;

public class Automovil extends Transporte {
    private int maxPaq;

    public Automovil(String patente, int volMax, int valorViaje, int maxPaq) {
        super(patente, volMax,valorViaje);
        this.maxPaq = maxPaq;
    }

    @Override
    public void cargarPaquetes(Paquete paquete) {
        if (puedeCargarPaquete(paquete)) {
            volMax -= paquete.obtenerVolumen();
            paquetesCargados.add(paquete);
            paquete.marcarEntregado();
        }
    }

    private boolean puedeCargarPaquete(Paquete paquete) {
        if (paquete instanceof PaqueteOrdinario) {
            if (volMax - paquete.obtenerVolumen() >= 0) {
                if (paquete.obtenerVolumen() < 2000 && paquetesCargados.size() <= maxPaq) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean equals(Object transporte) {
        if (this == transporte) {
            return true;
        }
        if (transporte == null || !this.getClass().equals(transporte.getClass())) {
            return false;
        }

        Transporte otroTransporte = (Transporte) transporte;
        if(this.paquetesCargados.size()==otroTransporte.paquetesCargados.size()) {
            if(this.paquetesCargados.size() > 0) {
                return this.paquetesCoinciden(otroTransporte.paquetesCargados);
            }
        }
        return false;
    }


    @Override
    public double calcularCostoEntrega() {
        return valorViaje; 
    }

    @Override
    public String toString() {
        return " patente de automovil, paquetes cargados:" + paquetesCargados.size() + " y el valor del viaje: $" + valorViaje + '\n';

    }
}