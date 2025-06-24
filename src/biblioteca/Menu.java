package biblioteca;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private static final Scanner input = new Scanner(System.in);
    private static final Biblioteca biblioteca = new Biblioteca();

    private static final String MSG_ERROR = "Error: ";
    private static final String MSG_TITULO_VACIO = "El titulo no puede estar vacio.";

    private Menu() {
        
    }

    public static void mostrarMenu() {
        int opcion = -1;
        do {
            try {
                System.out.println("\n----- MENU PRINCIPAL -----");
                System.out.println("1. Registrar usuario");
                System.out.println("2. Agregar libro");
                System.out.println("3. Prestar libro");
                System.out.println("4. Devolver libro");
                System.out.println("5. Libros disponibles");
                System.out.println("6. Guardar estado en archivo");
                System.out.println("7. Libros ordenados por titulo");
                System.out.println("8. Usuarios ordenados por nombre");
                System.out.println("9. Salir");
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
                    case 2 -> agregarLibro();
                    case 3 -> prestarLibro();                    
                    case 4 -> devolverLibro();                                        
                    case 5 -> biblioteca.mostrarLibrosDisponibles();                     
                    case 6 -> guardarEstado();                   
                    case 7 -> biblioteca.mostrarLibrosOrdenados();                    
                    case 8 -> biblioteca.mostrarUsuariosOrdenados();                                          
                    case 9 -> System.out.println("Gracias por usar nuestro sistema. Hasta luego!");     
                    default -> System.out.println("Opcion no valida.");             
                }
            } catch (InputMismatchException e) {
                System.out.println(MSG_ERROR + e.getMessage());
                input.nextLine();
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
            }
        } while (opcion != 9);
    }

    private static void registrarUsuario() {
        System.out.println("Ingrese nombre del usuario: ");
        String nombre = input.nextLine();

        if (nombre.isEmpty()) {
            System.out.println("El nombre no puede estar vacio.");
            return;
        }

        String rut;
        while (true) {
            System.out.println("Ingrese rut del usuario: ");
            rut = input.nextLine().trim();

            if (Biblioteca.validarRUT(rut)) {
                break;
            } else {
                System.out.println("Rut invalido. porfavor corregir a un formato valido.");
            }
        }

        try {
            Usuario usuario = new Usuario(rut, nombre);
            biblioteca.registrarUsuario(usuario);
        } catch (Exception e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
        }
    }

    private static void agregarLibro() {
        System.out.println("Ingrese el titulo del libro: ");
        String titulo = input.nextLine().trim();
        if (titulo.isEmpty()) {
            System.out.println(MSG_TITULO_VACIO);
            return;
        }

        System.out.println("Ingrese el autor del libro: ");
        String autor = input.nextLine().trim();
        if (autor.isEmpty()) {
            System.out.println("El autor no puede estar vacio.");
            return;
        }
        try {
            Libro libro = new Libro(titulo, autor);
            biblioteca.agregarLibro(libro); 
        } catch (Exception e) {
            System.out.println("Error al agregar el libro: " + e.getMessage());
        }

    }

    private static void prestarLibro() {
        System.out.println("Ingrese el titulo del libro: ");
        String titulo = input.nextLine().trim();
        if (titulo.isEmpty()) {
            System.out.println(MSG_TITULO_VACIO);
            return;
        }

        System.out.println("Ingrese el rut del usuario: ");
        String rut = input.nextLine();
        if (rut.isEmpty()) {
            System.out.println("El rut no puede estar vacio.");
            return;
        }

        try {
            biblioteca.prestarLibro(titulo, rut);
            System.out.println("Prestamo realizado.");
        } catch (LibroNoEncontradoException | LibroYaPrestadoException e) {
            System.out.println(MSG_ERROR + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error de usuario: " + e.getMessage());
        } catch (Exception e) {
            System.out.println(MSG_ERROR + e.getMessage());
        }
    }

    private static void devolverLibro() {
        System.out.println("Ingrese titulo del libro a devolver: ");
        String titulo = input.nextLine().trim();
        if (titulo.isEmpty()) {
            System.out.println(MSG_TITULO_VACIO);
            return;
        }

        try {
            biblioteca.devolverLibro(titulo);
            System.out.println("Libro devuelto con exito.");
        } catch (LibroNoEncontradoException e) {
            System.out.println(MSG_ERROR + e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println("Error invalido: " + e.getMessage());
        } catch (Exception e) {
            System.out.println(MSG_ERROR + e.getMessage());
        }
    }

    private static void guardarEstado() {
        System.out.println("Ingrese que nombre desea para su archivo: ");
        String archivo = input.nextLine();
        biblioteca.guardarEstadoEnArchivo(archivo);
    }
}
