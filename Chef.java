public class Chef {
    private String nombre;
    private int puntos;
    private Ingrediente ingredienteActual; // Aquí se cumple la regla de "un ingrediente a la vez"

    // Constructor
    public Chef(String nombre) {
        this.nombre = nombre;
        this.puntos = 0; // Inicia con 0 puntos
        this.ingredienteActual = null; // Inicia con las manos vacías
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getPuntos() { return puntos; }
    
    // Método modificado para asegurar que los puntos nunca sean negativos según las reglas
    public void setPuntos(int puntos) { 
        if(puntos < 0) {
            this.puntos = 0;
        } else {
            this.puntos = puntos; 
        }
    }

    public Ingrediente getIngredienteActual() { return ingredienteActual; }
    public void setIngredienteActual(Ingrediente ingredienteActual) { this.ingredienteActual = ingredienteActual; }
}