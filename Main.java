import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //Cocina principal
        Cocina miCocina = new Cocina();
        miCocina.setTiempo(120); // Le damos 120 segundos (2 minutos) al juego

        //2 chefs
        Chef chef1 = new Chef("Chef 1");
        Chef chef2 = new Chef("Chef 2");

        //Añadir los chefs a la lista de la cocina
        miCocina.getChefs().add(chef1);
        miCocina.getChefs().add(chef2);

        // Crear proteína
        Proteina carne = new Proteina("Carne de Res");
        
        // Verificar su estado inicial
        // System.out.println("Estado de la " + carne.getNombre() + ": " + carne.getEstado() + " (Cocinada: " + carne.isCocinada() + ")");

        // Hacer que el Chef 1 agarre la carne
        chef1.setIngredienteActual(carne);
        
        // Validar que el chef la tiene en sus manos
        if (chef1.getIngredienteActual() != null) {
            // System.out.println(chef1.getNombre() + " ha tomado un ingrediente: " + chef1.getIngredienteActual().getNombre());
        }

        // Crear lista de ingredientes que se necesitan para una receta
        ArrayList<Ingrediente> ingredientesHamburguesa = new ArrayList<>();
        ingredientesHamburguesa.add(new Proteina("Carne de Res")); // Ojo, esta es la receta, pide carne
        ingredientesHamburguesa.add(new PanesYBases("Pan de Hamburguesa"));
        
        // Crear la receta: Lista de ingredientes, 50 puntos, 60 segundos de límite
        Receta hamburguesa = new Receta(ingredientesHamburguesa, 50, 60);
        
        // Añadir la orden a la cocina
        miCocina.getOrdenes().add(hamburguesa);
        // System.out.println("Nueva orden recibida. Puntos posibles: " + miCocina.getOrdenes().get(0).getPuntosReceta());

        // Mostrar la ventana del juego
        new VentanaJuego();

    }
}