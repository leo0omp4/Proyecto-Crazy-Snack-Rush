import java.util.ArrayList;
import java.util.List;

public class Estacion {
    private String nombre;
    private String tipo;
    private int posX, posY;
    private List<Ingrediente> ingredientesAceptados;

    public Estacion(String nombre, String tipo, int posX, int posY) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.posX = posX;
        this.posY = posY;
        this.ingredientesAceptados = new ArrayList<>();
    }

    // Getters
    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
    public int getPosX() { return posX; }
    public int getPosY() { return posY; }
}