public class Ingrediente {
    private String nombre;
    private String estado; // Ejemplo: "crudo", "preparado", "quemado"

    public Ingrediente(String nombre) {
        this.nombre = nombre;
        this.estado = "crudo";
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}