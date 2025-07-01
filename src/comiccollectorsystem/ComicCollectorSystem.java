package comiccollectorsystem;

import biblioteca.Comic;
import biblioteca.Comic.TipoComic;
import biblioteca.Usuario;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class ComicCollectorSystem {
    private final List<Comic> comics;
    private final Map<String, Usuario> usuarios;
    private final Map<String, Comic> indicePorTitulo;
    private final Map<String, String> reservas;
    private final Set<String> titulosUnicos;
    private final Set<String> rutsUnicos;
    private final TreeSet<Comic> comicsOrdenados; 
    private final TreeSet<Usuario> usuariosOrdenados;

    public ComicCollectorSystem() {
        comics = new ArrayList<>();
        usuarios = new HashMap<>();
        indicePorTitulo = new HashMap<>();
        reservas = new HashMap<>();
        titulosUnicos = new HashSet<>();
        rutsUnicos = new HashSet<>();
        comicsOrdenados = new TreeSet<>(Comparator.comparing(Comic::getTitulo));
        usuariosOrdenados = new TreeSet<>(Comparator.comparing(Usuario::getNombre));
    }

    public void agregarComic(Comic comic) {
        if (comic == null) return;
            String titulo = comic.getTitulo();

            if (titulosUnicos.contains(titulo)) {
                System.out.println("El titulo ya está registrado");
                return;
            }

            comics.add(comic);
            indicePorTitulo.put(titulo, comic);
            titulosUnicos.add(titulo);
            comicsOrdenados.add(comic);
            System.out.println("Comic agregado con exito");
    }

    public void agregarUsuario(Usuario usuario) {
        if (usuario == null) return;

        String rut = usuario.getRut();

        if (rutsUnicos.contains(rut)) {
            System.out.println("El RUT ya está registrado.");
            return;
        }

        usuarios.put(rut, usuario);
        rutsUnicos.add(rut);
        usuariosOrdenados.add(usuario);
        System.out.println("Usuario registrado exitosamente.");
    }

    public void mostrarComicsDisponibles() {
    boolean hayDisponibles = false;
    for (Comic c : comics) {
        if (!reservas.containsKey(c.getTitulo())) {
            hayDisponibles = true;
            System.out.println(c);
        }
    }
    if (!hayDisponibles) {
        System.out.println("No hay comics disponibles.");
    }
}


    public Usuario buscarUsuarioPorRut(String rut) {
        if (rut == null || rut.isEmpty()) {
            System.out.println("RUT invalido.");
            return null;
        }
        
        Usuario usuario = usuarios.get(rut);

        if (usuario == null) {
            System.out.println("Usuario no encontrado.");
        }
        return usuario;
    }

    public Comic buscarComicPorTitulo(String titulo) {
        if (titulo == null || titulo.isEmpty()) {
            System.out.println("Titulo invalido.");
            return null;
        }

        Comic comic = indicePorTitulo.get(titulo);

        if (comic == null) {
            System.out.println("Comic no encontrado.");
        }
        return comic;
    }

    public boolean reservarComic(String titulo, String rutUsuario) {
        if (titulo == null || rutUsuario == null || titulo.isEmpty() || rutUsuario.isEmpty()) {
            System.out.println("Titulo o rut invalido.");
            return false;
        }
        
        Comic comic = buscarComicPorTitulo(titulo);
        if (comic == null) {
            return false;
        }

        if (reservas.containsKey(titulo)) {
            System.out.println("Este comic ya esta reservado por otro usuario.");
            return false;
        }

        if (!usuarios.containsKey(rutUsuario)) {
            System.out.println("El usuario no esta registrado.");
            return false;
        }

        reservas.put(titulo, rutUsuario);
        System.out.println("Reserva realizada exitosamente.");
        return true;
    }

    public boolean cancelarReserva(String titulo, String rutUsuario) {
        if (titulo == null || rutUsuario == null || titulo.isEmpty() || rutUsuario.isEmpty()) {
            System.out.println("Titulo o rut invalido.");
            return false;
        }

        if (!reservas.containsKey(titulo)) {
            System.out.println("Este comic no tiene reservas.");
            return false;
        }

        String rutReserva = reservas.get(titulo);
        if (!rutReserva.equals(rutUsuario)) {
            System.out.println("No puedes cancelar esta reserva porque pertenece a otro usuario.");
            return false;
        }

        reservas.remove(titulo);
        System.out.println("Reserva cancelada exitosamente.");
        return true;
    }

    public boolean venderComic(String titulo, String rutUsuario) {
        if (titulo == null || rutUsuario == null || titulo.isEmpty() || rutUsuario.isEmpty()) {
            System.out.println("Titulo o rut invalido.");
            return false;
        }

        Comic comic = indicePorTitulo.get(titulo);
        if (comic == null) {
            System.out.println("Comic no encontrado.");
            return false;
        }

        if (reservas.containsKey(titulo)) {
            String rutReservante = reservas.get(titulo);
            if (!rutReservante.equals(rutUsuario)) {
                System.out.println("Este comic esta reservado por otro usuario.");
                return false;
            } else {
                reservas.remove(titulo);
            }
        }

        comics.remove(comic);
        indicePorTitulo.remove(titulo);
        titulosUnicos.remove(titulo);

        System.out.println("Comic vendido exitosamente a " + rutUsuario + ".");
        return true;
    }

    public void mostrarComicsReservadosPorUsuario(String rutUsuario) {
        if (rutUsuario == null || rutUsuario.isEmpty()) {
            System.out.println("Rut invalido.");
            return;
        }

        boolean encontrado = false;
        for (Map.Entry<String, String> entrada : reservas.entrySet()) {
            String tituloComic = entrada.getKey();
            String rutReservante = entrada.getValue();

            if (rutUsuario.equals(rutReservante)) {
                Comic comic = indicePorTitulo.get(tituloComic);
                if (comic != null) {
                    System.out.println(comic);
                    encontrado = true;
                }
            }
        }

        if (!encontrado) {
            System.out.println("El usuario no tiene comics reservados.");
        }
    }

    public void guardarUsuariosEnArchivo(String rutaArchivo) {
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            for (Usuario usuario : usuarios.values()) {
                writer.write(usuario.getRut() + "," + usuario.getNombre() + "\n");
            }
            System.out.println("Usuarios guardados correctamente en el archivo: " + rutaArchivo);
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    public void cargarComicsDesdeCSV(String rutaArchivo, boolean limpiarAntes) {
        if (limpiarAntes) {
        comics.clear();
        indicePorTitulo.clear();
        titulosUnicos.clear();
        comicsOrdenados.clear();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
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
                    String tipoTexto = partes[2].trim().toUpperCase();
                    int numeroEdicion = Integer.parseInt(partes[3].trim());
                    boolean esRaro = Boolean.parseBoolean(partes[4].trim());
                    String editorial = partes[5].trim();

                    try {
                        TipoComic tipo = TipoComic.valueOf(tipoTexto);
                        Comic comic = new Comic(titulo, autor, tipo, numeroEdicion, esRaro, editorial);

                        agregarComic(comic);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Tipo de comic invalido: " + tipoTexto + ". Linea ignorada.");
                    }
                }
            }

            System.out.println("Carga de comics completada desde archivo: " + rutaArchivo);
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}
