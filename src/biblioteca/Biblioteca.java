package biblioteca;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Biblioteca {
    private final List<Libro> libros;
    private final Map<String, Usuario> usuarios;


    public Biblioteca() {
        libros = new ArrayList<>();
        usuarios = new HashMap<>();
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public Map<String, Usuario> getUsuarios() {
        return usuarios;
    }

    public void agregarLibro(Libro libro) {
        if (libro != null && buscarLibroPorTitulo(libro.getTitulo()) == null) {
            libros.add(libro);
        }
    }

    public void registrarUsuario(Usuario usuario) {
        if (usuario != null && !usuarios.containsKey(usuario.getRut())) {
            usuarios.put(usuario.getRut(), usuario);
        }
    }  

    public Libro buscarLibroPorTitulo(String titulo) {
        for (Libro libro : libros) {
            if (libro.getTitulo().equalsIgnoreCase(titulo)) {
                return libro;
            }
        }
        return null;
    }

    public void prestarLibro(String titulo, String rut) {
        Libro libro = buscarLibroPorTitulo(titulo);
        Usuario usuario = usuarios.get(rut);

        if (libro == null) {
            System.out.println("El libro no existe en el sistema.");
            return;
        }

        if (usuario == null) {
            System.out.println("El usuario no esta registrado.");
            return;
        }

        if (libro.estaPrestado()) {
            System.out.println("El libro no se encuentra disponible.");
            return;
        }

        libro.prestar();
        System.out.println("Libro prestado exitosamente a " + usuario.getNombre());
    }

    public void devolverLibro(String titulo) {
        Libro libro = buscarLibroPorTitulo(titulo);

        if (libro == null) {
            System.out.println("El libro no existe en el sistema.");
            return;
        }

        if (!libro.estaPrestado()) {
            System.out.println("El libro no está actualmente prestado.");
            return;
        }

        libro.devolver();
        System.out.println("Libro devuelto exitosamente.");
    }

    public void mostrarLibrosDisponibles() {
        boolean hayDisponibles = false;

        for (Libro libro : libros) {
            if (!libro.estaPrestado()) {
                System.out.println(libro);
                hayDisponibles = true;
            }
        }

        if (!hayDisponibles) {
            System.out.println("No hay libros disponibles en este momento.");
        }
    }
}
