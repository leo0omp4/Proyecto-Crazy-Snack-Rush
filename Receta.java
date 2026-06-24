public class Receta {
    private String nombre;
    private int puntosActuales;
    private int tiempoLimite;
    private int tiempoTranscurrido;

    public Receta(String nombre, int puntosBase, int tiempoLimite) {
        this.nombre = nombre;
        this.puntosActuales = puntosBase;
        this.tiempoLimite = tiempoLimite;
        this.tiempoTranscurrido = 0;
    }

    public void actualizarTiempo() {
        tiempoTranscurrido++;
        // Si el tiempo transcurrido llega al límite, reducimos los puntos
        if (tiempoTranscurrido >= tiempoLimite && puntosActuales > 0) {
            puntosActuales /= 2;
            tiempoTranscurrido = 0; // Reiniciamos para el próximo ciclo de degradación
        }
    }

    public String getNombre() { return nombre; }
    public int getPuntosActuales() { return puntosActuales; }
}