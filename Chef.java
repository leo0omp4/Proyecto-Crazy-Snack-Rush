public class Chef {
    private String nombre;
    private int puntos;
    private Ingrediente ingredienteActual;
    
    // Coordenadas en la cuadrícula
    private int posX;
    private int posY;

    // Constructor
    public Chef(String nombre, int posX, int posY) {
        this.nombre = nombre;
        this.puntos = 0;
        this.ingredienteActual = null;
        this.posX = posX;
        this.posY = posY;
    }

    // Métodos para mover al chef
    public void mover(int deltaX, int deltaY) {
        this.posX += deltaX;
        this.posY += deltaY;
    }

    // Getters y Setters para posiciones
    public int getPosX() { return posX; }
    public void setPosX(int posX) { this.posX = posX; }

    public int getPosY() { return posY; }
    public void setPosY(int posY) { this.posY = posY; }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getPuntos() { return puntos; }
    public void setPuntos(int puntos) { 
        this.puntos = Math.max(0, puntos); 
    }
    public Ingrediente getIngredienteActual() { return ingredienteActual; }
    public void setIngredienteActual(Ingrediente ingrediente) { this.ingredienteActual = ingrediente; }
}