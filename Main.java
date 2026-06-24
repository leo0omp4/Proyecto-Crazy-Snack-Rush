import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // 1. Cocina principal
        Cocina miCocina = new Cocina();
        miCocina.setTiempo(120); // 120 segundos para el juego

        // 2. 2 chefs
        Chef chef1 = new Chef("Chef 1", 7, 6);
        Chef chef2 = new Chef("Chef 2", 8, 6);

        // 3. Añadir los chefs a la lista de la cocina
        miCocina.agregarChef(chef1);
        miCocina.agregarChef(chef2);

        // 4. Crear lista de ingredientes que se necesitan para la receta
        ArrayList<Ingrediente> ingredientesHamburguesa = new ArrayList<>();
        ingredientesHamburguesa.add(new Proteina("Carne de Res"));
        ingredientesHamburguesa.add(new PanesYBases("Pan de Hamburguesa"));
        
        // 5. Crear la receta: 
        // IMPORTANTE: Ajustamos el constructor para aceptar la lista (si así lo requiere tu Receta.java actual)
        // o si prefieres mantener el constructor de 3 parámetros, usaremos el nombre.
        // Asumiendo que ahora tu clase Receta acepta: Receta(ArrayList<Ingrediente>, int puntos, int tiempoLimite)
        Receta hamburguesa = new Receta("Hamburguesa Especial", 50, 60);
        
        // 6. Añadir la orden a la cocina
        miCocina.agregarOrden(hamburguesa);

        // 7. Mostrar la ventana del juego
        VentanaJuego ventana = new VentanaJuego(miCocina);
        ventana.requestFocusInWindow();
    }
}