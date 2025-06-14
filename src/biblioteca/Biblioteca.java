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

    public void prestarLibro(String titulo, String rut) throws LibroNoEncontradoException, LibroYaPrestadoException {

        Libro libro = buscarLibroPorTitulo(titulo);
        Usuario usuario = usuarios.get(rut);

        if (libro == null) {
            throw new LibroNoEncontradoException("El libro " + titulo + "no existe en el sistema.");
        }

        if (usuario == null) {
            throw new IllegalArgumentException("El siguiente rut: " + rut + " no está registrado.");
        }

        if (libro.estaPrestado()) {
            throw new LibroYaPrestadoException("El libro " + titulo + " ya se encuentra prestado.");
        }

        libro.prestar();
    }

    public void devolverLibro(String titulo) throws LibroNoEncontradoException {
        Libro libro = buscarLibroPorTitulo(titulo);

        if (libro == null) {
            throw new LibroNoEncontradoException("El libro " + titulo + " no existe en el sistema.");
        }

        if (!libro.estaPrestado()) {
            throw new IllegalStateException("El libro " + titulo + " se encuentra disponible.");
        }

        libro.devolver();
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
