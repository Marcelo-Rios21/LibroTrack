package biblioteca;

import java.util.Scanner;

public class Menu {
    private static final Scanner input = new Scanner(System.in);
    private static final Biblioteca biblioteca = new Biblioteca();

    public static void mostrarMenu() {
        int opcion;
        do { 
            System.out.println("\n----- MENU PRINCIPAL -----");
            System.out.println("1. Registrar usuario");
            System.out.println("2. Agregar libro");
            System.out.println("3. Prestar libro");
            System.out.println("4. Devolver libro");
            System.out.println("5. Mostrar libros disponibles");
            System.out.println("6. Guardar estado en archivo");
            System.out.println("7. Salir");
            System.out.println("Seleccione una opcion: ");
            while (!input.hasNextInt()) {
            System.out.println("Ingrese un numero valido.");
            input.next();
            }
            opcion = input.nextInt();
            input.nextLine();

            switch (opcion) {
                case 1:
                    registrarUsuario();
                    break;
                case 2:
                    agregarLibro();
                    break;
                case 3:
                    prestarLibro();
                    break;
                case 4:
                    devolverLibro();
                    break;
                case 5:
                    biblioteca.mostrarLibrosDisponibles();
                    break;
                case 6:
                    guardarEstado();
                    break;
                case 7:
                    System.out.println("Gracias por usar nuestro sistema. Hasta luego!");
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        } while (opcion != 7);
    }

    private static void registrarUsuario() {
        System.out.println("Ingrese nombre del usuario: ");
        String nombre = input.nextLine();
        String rut;

        while (true) {
            System.out.println("Ingrese rut del usuario: ");
            rut = input.nextLine();

            if (biblioteca.validarRUT(rut)) {
                break;
            } else {
                System.out.println("Rut invalido. porfavor corregir a un formato valido.");
            }
        }

        Usuario usuario = new Usuario(rut, nombre);
        biblioteca.registrarUsuario(usuario);
    }

    private static void agregarLibro() {
        System.out.println("Ingrese el titulo del libro: ");
        String titulo = input.nextLine();
        System.out.println("Ingrese el autor del libro: ");
        String autor = input.nextLine();

        Libro libro = new Libro(titulo, autor);
        biblioteca.agregarLibro(libro);
    }

    private static void prestarLibro() {
        System.out.println("Ingrese el titulo del libro: ");
        String titulo = input.nextLine();
        System.out.println("Ingrese el rut del usuario: ");
        String rut = input.nextLine();

        try {
            biblioteca.prestarLibro(titulo, rut);
            System.out.println("Prestamo realizado.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void devolverLibro() {
        System.out.println("Ingrese titulo del libro a devolver: ");
        String titulo = input.nextLine();

        try {
            biblioteca.devolverLibro(titulo);
            System.out.println("Libro devuelto con exito.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void guardarEstado() {
        System.out.println("Ingrese que nombre desea para su archivo: ");
        String archivo = input.nextLine();
        biblioteca.guardarEstadoEnArchivo(archivo);
    }
}
