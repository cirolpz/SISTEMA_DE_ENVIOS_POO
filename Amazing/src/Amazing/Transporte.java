package Amazing;

import java.util.ArrayList;
import java.util.List;

public abstract class Transporte {
    public String patente;
    public int volMax;
    public List<Paquete> paquetesCargados;
    public int valorViaje;

    public Transporte(String patente, int volMax, int valorViaje) {
        this.patente = patente;
        this.volMax = volMax;
        this.paquetesCargados = new ArrayList<Paquete>();
        this.valorViaje = valorViaje;
    }

    public boolean paquetesCoinciden(List<Paquete> paquetesAComparar) {
        if (!mismaCantidadPaquetes(paquetesAComparar)) {
            return false;
        }

        return todosPaquetesCoinciden(paquetesAComparar);
    }

    private boolean mismaCantidadPaquetes(List<Paquete> paquetesAComparar) {
        return paquetesAComparar.size() == paquetesCargados.size();
    }

    private boolean todosPaquetesCoinciden(List<Paquete> paquetesAComparar) {
        for (Paquete paqueteAComparar : paquetesAComparar) {
            if (!existePaqueteCoincidente(paqueteAComparar)) {
                return false;
            }
        }
        return true;
    }
    private boolean existePaqueteCoincidente(Paquete paqueteAComparar) {
        for (Paquete paqueteCargado : paquetesCargados) {
            if (paquetesSonIguales(paqueteAComparar, paqueteCargado)) {
                return true;
            }
        }
        return false;
    }/* con stream:
    private boolean existePaqueteCoincidente(Paquete paqueteAComparar) {
        return paquetesCargados.stream()//Convierte la lista paquetesCargados en un flujo (stream),
                .anyMatch(paqueteCargado -> paquetesSonIguales(paqueteAComparar, paqueteCargado));// anyMatch se utilizan para verificar si hay algún paquete en paquetesCargados que sea igual al paqueteAComparar.
    }*/

    @Override
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
    public boolean paquetesSonIguales(Paquete paquete1, Paquete paquete2) {
        return paquete1.obtenerVolumen() == paquete2.obtenerVolumen() &&
                paquete1.getClass() == paquete2.getClass() && (paquete1.obtenerPrecio() == paquete2.obtenerPrecio());
    }
    public void cargarPaquetes(Paquete paquete) {}
    public abstract double calcularCostoEntrega();

    public String mostrarPatente() {
        return patente;
    }


    @Override
    public String toString() {
        return " patente: '" + patente +", el " +volMax+ "' paquetes cargados:" + paquetesCargados + " y el valor del viaje: $" + valorViaje + '\n';
    }
}
