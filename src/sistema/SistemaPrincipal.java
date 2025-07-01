package sistema;

import biblioteca.Menu;
import comiccollectorsystem.MenuComicCollector;
import java.util.Scanner;

public class SistemaPrincipal {
   
    public static void mostrarMenuPrincipal() {
        Scanner input = new Scanner(System.in);
        int opcion = -1;

        do {
            try {
                System.out.println("--- Bienvenido a LibroTrack ---");
                System.out.println("1. Ir a la Biblioteca");
                System.out.println("2. Ir a la tienda de coleccionables.");
                System.out.println("3. Salir");
                System.out.println("Seleccione una opcion: ");

                if (!input.hasNextInt()) {
                    System.out.println("Ingrese un numero valido.");
                    input.nextLine();
                    continue;
                }
                opcion = input.nextInt();
                input.nextLine();

                switch (opcion) {
                    case 1 -> Menu.mostrarMenu();
                    case 2 -> MenuComicCollector.mostrarMenu();
                    case 3 -> System.out.println("Gracias por usar nuestro sistema. Hasta luego!"); 
                    default -> System.out.println("Opcion no valida.");                            
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada no valida. Ingrese un numero.");
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
            }
        } while (opcion != 3);
    }
}
