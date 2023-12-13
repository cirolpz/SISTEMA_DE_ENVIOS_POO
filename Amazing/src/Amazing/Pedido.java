package Amazing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Pedido {
    int codPedido;
    int dni;
    String nombreCliente;
    String direccionCliente;
    HashMap<Integer,Paquete> paquetes;
    boolean cerrado;
    double costoTotal;


    public Pedido(int codPedido, int dni,String nombre,String dir) {
        this.codPedido = codPedido;
        this.dni = dni;
        this.nombreCliente = nombre;
        this.direccionCliente = dir;
        this.cerrado = false;
        this.paquetes = new HashMap<Integer,Paquete>();
        this.costoTotal = 0;
    }

    public double calcularCostoPedido() {
        double costo = 0;
        for (Paquete paq : paquetes.values()) {
            costo += paq.obtenerPrecio();
        }
        agregarCostoTotal(costo);
        return costo;
    }
      
    public void agregarCostoTotal(double costo){
    	costoTotal = costo; 
    }
    
    public void agregarPaquete(Paquete paquete){
     	paquetes.put(paquete.obtenerId(), paquete);

    }
    
    public boolean tienePaquete(int codPaquete) {
    	if(paquetes.containsKey(codPaquete)) {
    		return true;
    	}
    	return false;
    }
    
    public boolean quitarPaquete(int codPaquete){ 
    	if(!cerrado) {
        	paquetes.remove(codPaquete);
        	return true;
    	}
    	return false;
    }
    
    
    public boolean tienePaquetesSinEntregar( ){
        for(Paquete paq : paquetes.values()) {
            if(!paq.isEntregado()) {
                return true;
            }
        }
        return false;
    }


    public void organizarPaquetes() {
        List<Paquete> listaEspeciales = filtrarByType(PaqueteEspecial.class);
        List<Paquete> listaOrdinarios = filtrarByType(PaqueteOrdinario.class);

        ordenarPaquetesPorVolumen(listaEspeciales);
        ordenarPaquetesPorVolumen(listaOrdinarios);

        paquetes = construirMapaOrganizado(listaEspeciales, listaOrdinarios);
    }

    private List<Paquete> filtrarByType(Class<? extends Paquete> tipoPaquete) {
        return paquetes.values().stream()
                .filter(tipoPaquete::isInstance)
                .collect(Collectors.toList());
    }

    private void ordenarPaquetesPorVolumen(List<Paquete> listaPaquetes) {
        listaPaquetes.sort(Comparator.comparingDouble(Paquete::obtenerVolumen));
    }

    private HashMap<Integer, Paquete> construirMapaOrganizado(List<Paquete> listaEspeciales, List<Paquete> listaOrdinarios) {
        HashMap<Integer, Paquete> paquetesOrganizados = new HashMap<>();

        agregarPaquetesAlMapa(paquetesOrganizados, listaEspeciales);
        agregarPaquetesAlMapa(paquetesOrganizados, listaOrdinarios);

        return paquetesOrganizados;
    }

    private void agregarPaquetesAlMapa(HashMap<Integer, Paquete> paquetesOrganizados, List<Paquete> listaPaquetes) {
        listaPaquetes.forEach(paquete -> paquetesOrganizados.put(paquete.obtenerId(), paquete));
    }

    public boolean siFueCerradoYtienePaquetesSinEntregar()
    {
        if(fueCerrado() && tienePaquetesSinEntregar()){
            return true;
        }
        return false;
    }
    public boolean tieneMismoCodPedido(int codPedido){
        if(obtenerCodPedido() == codPedido){
            return true;
        }
        return false;
    }
    public int obtenerCodPedido() {
        return codPedido;
    }

    public String obtenerNombreCliente() {
        return nombreCliente;
    }

    public HashMap<Integer,Paquete> obtenerPaquetes() {
        return paquetes;
    }

    public boolean fueCerrado() {
        return cerrado;
    }

    public void cerrarPedido() {
        this.cerrado = true;
    }

    public String obtenerDireccionCliente() {
        return direccionCliente;
    }


    @Override
    public String toString() {
        return " codigo de pedido de: " + nombreCliente  + ", su dni: " + dni + " y su direccion es: " + direccionCliente +"\n Los paquetes de "+nombreCliente+" son:\n " + paquetes + ". \nel total a pagar de "+nombreCliente+" es de: $" + costoTotal + ",(se calcula si se cerro el pedido)\n \n";
    }
}