
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CSV {
    public List<Vuelo> construirVuelos(String path){
        List<Vuelo> vuelos =  new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (!values[0].equals("NumeroVuelo")) {
                    LocalDateTime fechaDespegue = formatoFecha(values[3]);
                    LocalDateTime fechaLlegada = formatoFecha(values[4]);
                    Vuelo vuelo = new Vuelo(values[0],values[1],values[2],fechaDespegue,fechaLlegada);
                    vuelos.add(vuelo);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException i) {
            throw new RuntimeException(i);
        }
        return vuelos;
    }

    public List<Tripulacion> armarTripulacion(String path){
        List<Tripulacion> tripulaciones = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (!values[0].equals("NumeroTripulacion")) {
                    String aeropuertoOrigen = values[1];
                    List<Vuelo> camino = new ArrayList<>();
                    List<Vuelo> camino2 = new ArrayList<>();
                    int mejorCosto = Integer.MAX_VALUE;
                    Tripulacion tripulacion = new Tripulacion(mejorCosto,camino,camino2,aeropuertoOrigen);
                    tripulaciones.add(tripulacion);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException i) {
            throw new RuntimeException(i);
        }
        return tripulaciones;
    }

    private LocalDateTime formatoFecha(String fecha){
        String[] separarFecha = fecha.split(" ");
        String[] auxFecha = separarFecha[0].split("/");
        String[] auxHorario = separarFecha[1].split(":");
        LocalDateTime datetime = LocalDateTime.of(Integer.parseInt(auxFecha[2]),Integer.parseInt(auxFecha[1]),Integer.parseInt(auxFecha[0]),Integer.parseInt(auxHorario[0]),Integer.parseInt(auxHorario[1]));
        return datetime;
    }
}
