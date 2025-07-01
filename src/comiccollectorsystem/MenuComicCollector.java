package comiccollectorsystem;

import biblioteca.Comic;
import biblioteca.Comic.TipoComic;
import biblioteca.Usuario;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuComicCollector {

    private static final Scanner input = new Scanner(System.in);
    private static final ComicCollectorSystem sistema = new ComicCollectorSystem();

    private static final String MSG_ERROR = "Error: ";

    private MenuComicCollector() {
        //Constructor vacio.
    }
    
    public static void mostrarMenu() {
        int opcion = -1;
        do {
            try {
                System.out.println("\n----- MENU PRINCIPAL DE COLECCIONABLES -----");
                System.out.println("1. Registrar usuario");
                System.out.println("2. Agregar comic");
                System.out.println("3. Reservar comic");
                System.out.println("4. Cancelar reserva");
                System.out.println("5. Vender comic");
                System.out.println("6. Cargar comics desde archivo CSV");
                System.out.println("7. Comics disponibles");
                System.out.println("8. Reservas de un usuario");
                System.out.println("9. Guardar usuarios en archivo");
                System.out.println("10. Salir");
                System.out.println("Seleccione una opcion: ");

                if (!input.hasNextInt()) {
                    System.out.println("Ingrese un numero valido.");
                    input.nextLine();
                    continue;
                }
                opcion = input.nextInt();
                input.nextLine();

                switch (opcion) {
                    case 1 -> registrarUsuario();
                    case 2 -> agregarComic();
                    case 3 -> reservarComic() ;                 
                    case 4 -> cancelarReserva();                                        
                    case 5 -> venderComic();                    
                    case 6 -> cargarComicsDesdeArchivo();          
                    case 7 -> verComicsDisponibles();                    
                    case 8 -> verReservasUsuario(); 
                    case 9 -> guardarUsuariosEnArchivo();                           
                    case 10 -> System.out.println("Gracias por usar nuestro sistema. Hasta luego!");     
                    default -> System.out.println("Opcion no valida.");             
                }
            } catch (InputMismatchException e) {
                System.out.println(MSG_ERROR + e.getMessage());
                input.nextLine();
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
            }
        } while (opcion != 10);
    }

    private static void agregarComic() {
    try {
        System.out.print("Ingrese titulo: ");
        String titulo = input.nextLine();
        if (titulo.trim().isEmpty()) {
            System.out.println("El título no puede estar vacío.");
            return;
        }

        System.out.print("Ingrese autor: ");
        String autor = input.nextLine();
        if (autor.trim().isEmpty()) {
            System.out.println("El autor no puede estar vacío.");
            return;
        }

        System.out.println("Seleccione el tipo de cómic:");
        System.out.println("1. MANGA");
        System.out.println("2. NOVELA_GRAFICA");
        System.out.println("3. FANZINE");
        System.out.print("Opción: ");

        int opcionTipo;
        if (!input.hasNextInt()) {
            System.out.println("Entrada inválida. Se esperaba un número.");
            input.nextLine(); 
            return;
        }
        opcionTipo = input.nextInt();
        input.nextLine(); 

        TipoComic tipo;
        switch (opcionTipo) {
            case 1 -> tipo = TipoComic.MANGA;
            case 2 -> tipo = TipoComic.NOVELA_GRAFICA;
            case 3 -> tipo = TipoComic.FANZINE;
            default -> {
                System.out.println("Opción no válida. Intente nuevamente.");
                return;
            }
        }

        System.out.print("Ingrese número de edición: ");
        if (!input.hasNextInt()) {
            System.out.println("Número de edición inválido.");
            input.nextLine();
            return;
        }
        int edicion = input.nextInt();
        input.nextLine();

        boolean esRaro = false;
        while (true) {
            System.out.print("¿Es edición rara? (sí/no): ");
            String respuesta = input.nextLine().trim().toLowerCase();
            if (respuesta.equals("sí") || respuesta.equals("si")) {
                esRaro = true;
                break;
            } else if (respuesta.equals("no")) {
                esRaro = false;
                break;
            } else {
                System.out.println("Debe ingresar 'sí' o 'no'.");
            }
        }

        System.out.print("Ingrese editorial: ");
        String editorial = input.nextLine();
        if (editorial.trim().isEmpty()) {
            System.out.println("La editorial no puede estar vacía.");
            return;
        }

        Comic comic = new Comic(titulo, autor, tipo, edicion, esRaro, editorial);
        sistema.agregarComic(comic);
        System.out.println("Cómic agregado correctamente.");
    } catch (Exception e) {
        System.out.println("Error al agregar cómic: " + e.getMessage());
        input.nextLine(); 
    }
}



    private static void registrarUsuario() {
        System.out.print("Ingrese RUT del usuario: ");
        String rut = input.nextLine().trim();

        if (rut.isEmpty()) {
            System.out.println("El RUT no puede estar vacío.");
            return;
        }
        System.out.print("Ingrese nombre del usuario: ");
        String nombre = input.nextLine().trim();

        if (nombre.isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }

        Usuario usuario = new Usuario(rut, nombre);
        sistema.agregarUsuario(usuario);
    }

    private static void reservarComic() {
        System.out.print("Ingrese titulo del comic a reservar: ");
        String titulo = input.nextLine();
        System.out.print("Ingrese RUT del usuario: ");
        String rut = input.nextLine();

        sistema.reservarComic(titulo, rut);
    }

    private static void cancelarReserva() {
        System.out.print("Ingrese titulo del comic: ");
        String titulo = input.nextLine();
        System.out.print("Ingrese RUT del usuario: ");
        String rut = input.nextLine();

        sistema.cancelarReserva(titulo, rut);
    }

    private static void venderComic() {
        System.out.print("Ingrese titulo del comic a vender: ");
        String titulo = input.nextLine();
        System.out.print("Ingrese RUT del comprador: ");
        String rut = input.nextLine();

        sistema.venderComic(titulo, rut);
    }

    private static void verComicsDisponibles() {
        sistema.mostrarComicsDisponibles();
    }

    private static void verReservasUsuario() {
        System.out.print("Ingrese RUT del usuario: ");
        String rut = input.nextLine();
        sistema.mostrarComicsReservadosPorUsuario(rut);
    }

    private static void cargarComicsDesdeArchivo() {
        System.out.print("Ingrese ruta del archivo CSV: ");
        System.out.println("Fomato esperado: titulo, autor, tipo, numeroEdicion, esRaro, editorial (se salta primera linea)");
        System.out.println("Ingrese la ruta del archivo: ");
        String ruta = input.nextLine();
        try {
            sistema.cargarComicsDesdeCSV(ruta, true);
            System.out.println("Carga de comics finalizada.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error en el formato del archivo: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    private static void guardarUsuariosEnArchivo() {
        System.out.print("Ingrese ruta del archivo de salida: ");

        String ruta = input.nextLine();
        sistema.guardarUsuariosEnArchivo(ruta);
    }
}
