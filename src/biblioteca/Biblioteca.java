package biblioteca;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Biblioteca {
    // Lista principal de libros
    private final List<Libro> libros;
    //Mapa de usuarios con clave RUT
    private final Map<String, Usuario> usuarios;
    //Indice para busqueda directa
    private final Map<String, Libro> indicePorTitulo;
    //Conjunto que previene duplicados de titulos
    private final Set<String> titulosUnicos;
    //Conjunto que evita duplicados de RUT
    private final Set<String> rutsUnicos;
    //Conjunto ordenado de libros por titulo
    private final TreeSet<Libro> librosOrdenados;
    //Conjunto ordenado de usuarios por nombre
    private final TreeSet<Usuario> usuariosOrdenados;


    public Biblioteca() {
        libros = new ArrayList<>();

        usuarios = new HashMap<>();
        indicePorTitulo = new HashMap<>();

        titulosUnicos = new HashSet<>();
        rutsUnicos = new HashSet<>();

        librosOrdenados = new TreeSet<>();
        usuariosOrdenados = new TreeSet<>();
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public Map<String, Usuario> getUsuarios() {
        return usuarios;
    }

    public void agregarLibro(Libro libro) {
        if (libro == null) return;

        String titulo = libro.getTitulo().toLowerCase();

        if (titulosUnicos.contains(titulo)) {
            System.out.println("El libro ya existe en la biblioteca.");
            return;
        }

        libros.add(libro);
        titulosUnicos.add(titulo);
        librosOrdenados.add(libro);
        indicePorTitulo.put(titulo, libro);
        System.out.println("Libro agregado.");
    }

    public void registrarUsuario(Usuario usuario) {
        if (usuario == null) return;

        String rut = usuario.getRut();

        if (rutsUnicos.contains(rut)) {
            System.out.println("El rut ya está registrado.");
            return;
        }
        usuarios.put(rut, usuario);
        rutsUnicos.add(rut);
        usuariosOrdenados.add(usuario);
        System.out.println("Usuario registrado.");
    }  

    public Libro buscarLibroPorTitulo(String titulo) {
        if (titulo == null) return null;
        return indicePorTitulo.get(titulo.toLowerCase());
    }

    public void prestarLibro(String titulo, String rut) throws LibroNoEncontradoException, LibroYaPrestadoException {

        Libro libro = buscarLibroPorTitulo(titulo);
        Usuario usuario = usuarios.get(rut);

        if (libro == null) {
            throw new LibroNoEncontradoException("El libro " + titulo + " no existe en el sistema.");
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

    public void mostrarLibrosOrdenados() {
        if (librosOrdenados.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        System.out.println("--- Libros ordenados por titulo ---");
        for (Libro libro : librosOrdenados) {
            System.out.println(libro);
        }
    }

    public void mostrarUsuariosOrdenados() {
        if (usuariosOrdenados.isEmpty()) {
           System.out.println("No hay usuarios registrados."); 
           return;
        }

        System.out.println("--- Usuarios ordenados por nombre ---");
        for (Usuario usuario : usuariosOrdenados) {
            System.out.println(usuario);
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

    public void cargarLibrosDesdeCSV(String archivo, boolean limpiarAntes) throws IOException {
        if (limpiarAntes) {
            libros.clear();
            librosOrdenados.clear();
            titulosUnicos.clear();
            indicePorTitulo.clear();
        }

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

    public void cargarUsuariosDesdeCSV(String archivo, boolean limpiarAntes) {
        if (limpiarAntes) {
            usuarios.clear();
            usuariosOrdenados.clear();
            rutsUnicos.clear();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))){
            String linea;
            boolean primeraLinea = true;

            while ((linea = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }
                String[] partes = linea.split(",\\s*");

                if (partes.length >= 2) {
                    String rut = partes[0].trim();
                    String nombre = partes[1].trim();

                    if (!validarRUT(rut)) {
                        System.out.println("Rut invalido en archivo: " + rut);
                        continue;
                    }

                    if (rutsUnicos.contains(rut)) {
                        System.out.println("Rut duplicado en archivo: " + rut);
                        continue;
                    }
                    Usuario usuario = new Usuario(rut, nombre);
                    usuarios.put(rut, usuario);
                    rutsUnicos.add(rut);
                    usuariosOrdenados.add(usuario);
                }
            }
            System.out.println("Usuarios cargados con exito desde el archivo.");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de usuarios: " + e.getMessage());
        }
    }

    public static boolean validarRUT(String rut) {
        if (rut == null) return false;

        rut = rut.replace(".", "").toUpperCase();

        if (!rut.matches("^\\d{7,8}-[\\dK]$")) {
            return false;
        }

        String[] partes = rut.split("-");
        String cuerpo = partes[0];
        String dv = partes[1];

        int suma = 0;
        int factor = 2;

        for (int i = cuerpo.length() - 1; i >= 0; i--) {
            suma += Character.getNumericValue(cuerpo.charAt(i)) * factor;
            factor = (factor == 7) ? 2 : factor + 1;
        }

        int resto = 11 - (suma % 11);
        String dvEsperado;
        if (resto == 11) {
            dvEsperado = "0";
        } else if (resto == 10) {
            dvEsperado = "K";
        } else {
            dvEsperado = String.valueOf(resto);
        }

        return dv.equals(dvEsperado);
    }

}
