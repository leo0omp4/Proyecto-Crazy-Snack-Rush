public class Ingrediente {
    private String nombre;
    private String estado;
    private int tiempoEnProceso = 0;

    public Ingrediente(String nombre) {
        this.nombre = nombre;
        this.estado = "crudo";
        this.tiempoEnProceso = 0;
    }

    // Getters y Setters
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public int getTiempoEnProceso() { return tiempoEnProceso; }
    public void setTiempoEnProceso(int tiempo) { this.tiempoEnProceso = tiempo; }
    
    public void incrementarTiempo() { this.tiempoEnProceso++; }

    public String getNombre() {
    return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}