import java.time.LocalDateTime;
import java.util.List;

public class Tripulacion{
    private int mejorCosto;
    private List<Vuelo> caminoActual;
    private List<Vuelo> caminoFinal;
    private String aeropuertoOrigen;

    public Tripulacion(int mejorCosto, List<Vuelo> caminoActual, List<Vuelo> caminoFinal, String aeropuertoOrigen) {
        this.mejorCosto = mejorCosto;
        this.caminoActual = caminoActual;
        this.caminoFinal = caminoFinal;
        this.aeropuertoOrigen = aeropuertoOrigen;
    }

    public int getMejorCosto() {
        return mejorCosto;
    }

    public void setMejorCosto(int mejorCosto) {
        this.mejorCosto = mejorCosto;
    }

    public Vuelo getUltimoVuelo() {
        if (this.caminoActual.size() > 0) {
            return this.caminoActual.get(this.caminoActual.size() - 1);
        }
        LocalDateTime fecha1 = LocalDateTime.of(2022, 10, 21, 0, 0);
        Vuelo vuelo = new Vuelo("","",this.aeropuertoOrigen,fecha1,fecha1);
        return vuelo;
    }


    public List<Vuelo> getCaminoFinal() {
        return caminoFinal;
    }

    public void resetCaminoFinal(List<Vuelo> caminoFinal){
        this.caminoFinal.removeAll(caminoFinal);
    }

    public void setCaminoFinal(List<Vuelo> caminoFinal) {
        this.caminoFinal.addAll(caminoFinal);
    }

    public void setCaminoActual(List<Vuelo> caminoActual) {
        this.caminoActual = caminoActual;
    }

    public List<Vuelo> getCaminoActual() {
        return caminoActual;
    }

    public void setAeropuertoOrigen(String aeropuertoOrigen) {
        this.aeropuertoOrigen = aeropuertoOrigen;
    }

    public String getAeropuertoOrigen() {
        return aeropuertoOrigen;
    }
}
