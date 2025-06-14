package biblioteca;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

    public void cargarLibrosDesdeArchivos(String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",", 2);
                if (partes.length == 2) {
                    String titulo = partes[0].trim();
                    String autor = partes[1].trim();
                    Libro libro = new Libro(titulo, autor);
                    agregarLibro(libro);
                }
            }
            System.out.println("Libros cargados exitosamente desde el archivo.");
            
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    public void cargarLibrosDesdeCSV(String archivo) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean primeraLinea = true;
            
            while ((linea = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }
                String[] partes = linea.split(",");

                if (partes.length >= 3) {
                    String titulo = partes[0].trim();
                    String autor = partes[1].trim();
                    boolean prestado = Boolean.parseBoolean(partes[2].trim());

                    Libro libro = new Libro(titulo, autor, prestado);
                    agregarLibro(libro);
                }
            }
        }
    }

    public void guardarEstadoEnArchivo(String archivo) {

        File archivoDestino = new File(archivo);

        try {
            if (archivoDestino.exists() && !archivoDestino.canWrite()) {
                System.out.println("No se puede escribir en el archivo especificado.");
                return;
            }
        
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
                writer.write("-- Libros --");
                writer.newLine();
                for (Libro libro : libros) {
                    writer.write("Titulo: " + libro.getTitulo() +
                                ", Autor: " + libro.getAutor() +   
                                ", Estado: " + (libro.estaPrestado() ? "Prestado" : "Disponible"));
                    writer.newLine();          
                }

                writer.newLine();
                writer.write("-- Usuarios --");
                writer.newLine();
                for (Usuario usuario : usuarios.values()) {
                    writer.write("Rut: " + usuario.getRut() + ", Nombre: " + usuario.getNombre());
                    writer.newLine();
                }

                System.out.println("Guardado en " + archivo);
            }
                
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

    public void mostrarEstadoDesdeArchivo(String archivo) {
        File archivoDestino = new File(archivo);

        if (!archivoDestino.exists()) {
            System.out.println("El archivo especificado no existe.");
            return;
        }

        if (!archivoDestino.canRead()) {
            System.out.println("No se tiene permiso para leer el archivo.");
            return;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}
