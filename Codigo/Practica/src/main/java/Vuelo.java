import java.time.LocalDateTime;

public class Vuelo {
    private String codigo;
    private String aeropuertoOrigen;
    private String aeropuertoDestino;
    private LocalDateTime fechaDespegue;
    private LocalDateTime fechaLlegada;

    public Vuelo(String codigo, String aeropuertoOrigen, String aeropuertoDestino, LocalDateTime fechaDespegue, LocalDateTime fechaLlegada) {
        this.codigo = codigo;
        this.aeropuertoOrigen = aeropuertoOrigen;
        this.aeropuertoDestino = aeropuertoDestino;
        this.fechaDespegue = fechaDespegue;
        this.fechaLlegada = fechaLlegada;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getAeropuertoOrigen() {
        return aeropuertoOrigen;
    }

    public String getAeropuertoDestino() {
        return aeropuertoDestino;
    }

    public LocalDateTime getFechaDespegue() {
        return fechaDespegue;
    }

    public LocalDateTime getFechaLlegada() {
        return fechaLlegada;
    }

}
