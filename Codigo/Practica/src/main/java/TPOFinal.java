import tda.ConjuntoTDA;
import tda.impl.Conjunto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class TPOFinal {

    public static void main(String[] args) {
        CSV archivo = new CSV();
        String pathVuelos = "D:\\Documents\\APUNTES FACULTAD\\Segundo año\\Segundo cuatrimestre\\Programacion 3\\Codigo\\Practica\\src\\main\\java\\Vuelos.csv";
        List<Vuelo> vuelos = archivo.construirVuelos(pathVuelos);

        String pathTripulaciones = "D:\\Documents\\APUNTES FACULTAD\\Segundo año\\Segundo cuatrimestre\\Programacion 3\\Codigo\\Practica\\src\\main\\java\\Tripulaciones.csv";
        List<Tripulacion> tripulaciones = archivo.armarTripulacion(pathTripulaciones);

        ConjuntoTDA<Vuelo> visitados = new Conjunto<>();
        visitados.inicializarConjunto();

        int costoActual = 0;

        tripulaciones = asignarTripulaciones(visitados, costoActual, tripulaciones, vuelos);

        imprimirResultado(tripulaciones);
    }

    public static List<Tripulacion> asignarTripulaciones(ConjuntoTDA<Vuelo> visitados, Integer costoActual, List<Tripulacion> tripulaciones, List<Vuelo> vuelos) {
        if (condicion(tripulaciones,vuelos)) { // Caso base: todos los vuelos fueron asignados, y la tripulacion se encuentra en el origen.
            costoActual = calcularCosto(tripulaciones);
            if (costoActual < tripulaciones.get(0).getMejorCosto()){ //Si la solucion encontrada es mejor que la anterior, se cambia
                tripulaciones.get(0).setMejorCosto(costoActual);
                for (int i = 0; i < tripulaciones.size(); i++){
                    tripulaciones.get(i).resetCaminoFinal(tripulaciones.get(i).getCaminoFinal()); //Borro la solucion obtenida previamente
                    tripulaciones.get(i).setCaminoFinal(tripulaciones.get(i).getCaminoActual()); //Copio el camino parcial al camino final
                }
            }
        } else { // Caso recursivo
            // Recorro los vuelos
            for (int i = 0; i < vuelos.size(); i++){
                // Recorro las tripulaciones
                for (int j = 0; j < tripulaciones.size(); j++){
                    //Evaluo si la tripulacion puede tomar el vuelo
                    if (vueloFactible(vuelos.get(i), tripulaciones.get(j).getUltimoVuelo(), visitados)){

                        visitados.agregar(vuelos.get(i));
                        tripulaciones.get(j).getCaminoActual().add(vuelos.get(i));

                        tripulaciones = asignarTripulaciones(visitados,costoActual, tripulaciones, vuelos); //Llamado recursivo

                        visitados.sacar(vuelos.get(i));
                        tripulaciones.get(j).getCaminoActual().remove(vuelos.get(i));
                    }
                }
            }
        }
        return tripulaciones;
    }

    private static boolean condicion(List<Tripulacion> tripulaciones, List<Vuelo> vuelos) {
        if (vuelosAsignados(tripulaciones, vuelos) && tripEnOrigen(tripulaciones)){
            return true;
        }
        return false;
    }

    private static boolean vueloFactible(Vuelo vuelo, Vuelo ultimoVuelo, ConjuntoTDA<Vuelo> visitados) {
        if (!visitados.pertenece(vuelo) && vuelo.getFechaDespegue().isAfter((ultimoVuelo.getFechaLlegada().plusHours(2))) && vuelo.getAeropuertoOrigen().hashCode() == ultimoVuelo.getAeropuertoDestino().hashCode()) {
            return true;
        }
        return false;
    }

    private static int calcularCosto(List<Tripulacion> tripulaciones) {
        int costo = 0;
        for (int i = 0; i < tripulaciones.size(); i++){
            List<Vuelo> camino = tripulaciones.get(i).getCaminoActual();
            for (int j = 0; j < camino.size() - 1; j++){
                costo += calcularHorasExtra(camino.get(j).getFechaLlegada(), camino.get(j + 1).getFechaDespegue());
            }
        }
        return costo;
    }

    private static int calcularHorasExtra(LocalDateTime actualidad, LocalDateTime fechaDespegue) {
        int horaExtra = 60;
        actualidad = actualidad.plusHours(2);
        Duration dif = Duration.between(actualidad, fechaDespegue);
        int tiempo = (int) dif.getSeconds();
        int cantHorasExtra = tiempo / 3600;
        int costoHextra = horaExtra * cantHorasExtra;
        return costoHextra;
    }

    private static boolean tripEnOrigen(List<Tripulacion> tripulaciones) {
        boolean condicion = true;
        int aux = 0;
        while (condicion && aux < tripulaciones.size()){
            if (tripulaciones.get(aux).getUltimoVuelo().getAeropuertoDestino().hashCode() != tripulaciones.get(aux).getAeropuertoOrigen().hashCode()){
                condicion = false;
            }
            aux++;
        }
        return condicion;
    }

    private static boolean vuelosAsignados (List<Tripulacion> tripulaciones, List<Vuelo> vuelos){
        boolean condicion = false;
        int aux = 0;
        int cantVuelos = 0;
        while (!condicion && aux < tripulaciones.size()){
            cantVuelos += tripulaciones.get(aux).getCaminoActual().size();
            aux++;
            if (cantVuelos == vuelos.size()){
                condicion = true;
            }
        }
        return condicion;
    }

    private static void imprimirResultado(List<Tripulacion> tripulaciones ) {
        int contadorT = 1;
        int contadorV = 1;
        System.out.println("\nCOSTO TOTAL DE HORAS EXTRAS: U$D" + tripulaciones.get(0).getMejorCosto());
        for (int i = 0; i < tripulaciones.size(); i++) {
            List<Vuelo> camino = tripulaciones.get(i).getCaminoFinal();
            System.out.println("\nMEJOR CAMINO PARA TRIPULACION " + contadorT);
            for (int j = 0; j < camino.size(); j++) {
                System.out.println("\nVuelo " + contadorV + " Codigo: " + camino.get(j).getCodigo());
                System.out.println("Desde: " + camino.get(j).getAeropuertoOrigen() + ", Despegue: " + camino.get(j).getFechaDespegue());
                System.out.println("Hasta: " + camino.get(j).getAeropuertoDestino() + ", Llegada: " + camino.get(j).getFechaLlegada());
                contadorV++;
            }
            contadorT++;
            contadorV = 1;
        }
    }
}