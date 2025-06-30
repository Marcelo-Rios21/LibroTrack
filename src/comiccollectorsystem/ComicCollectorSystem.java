package comiccollectorsystem;

import biblioteca.Comic;
import biblioteca.Usuario;
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

    public List<Comic> listarComicsDisponibles() {
        List<Comic> disponibles = new ArrayList<>();

        for (Comic comic : comics) {
            if (!comic.estaPrestado()) {
                disponibles.add(comic);
            }
        }
        if (disponibles.isEmpty()) {
            System.out.println("No hay comics disponibles.");
        }

        return  disponibles;
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

    public void mostrarComicsPorTipo(String tipo) {
        if (tipo == null || tipo.isEmpty()) {
            System.out.println("Tipo invalido.");
            return;
        }

        boolean encontrado = false;

        for (Comic comic : comics) {
            if (comic.getTipo().name().equalsIgnoreCase(tipo)) {
                System.out.println(comic);
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontraron comic del tipo: " + tipo);
        }
    }

    public void mostrarComicsOrdenados() {
        if (comicsOrdenados.isEmpty()) {
            System.out.println("No hay comics registrados.");
            return;
        }

        System.out.println("Listado de comics ordenados alfabeticamente:");
        for (Comic comic : comicsOrdenados) {
            System.out.println("- " + comic.getTitulo());
        }
    }

    public void mostrarUsuariosOrdenados() {
        if (usuariosOrdenados.isEmpty()) {
            System.out.println("No hay usuarios registrados en el sistema.");
            return;
        }

        System.out.println("Lista de usuarios ordenada por nombre:");
        for (Usuario usuario : usuariosOrdenados) {
            System.out.println("- " + usuario.getNombre() + " - Rut: " + usuario.getRut());
        }
    }

    public void mostrarUsuariosPorRut() {
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return;
        }
        //Recorre todos los usuarios del sistema
        //Los ordena por rut
        usuarios.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(entry -> {
            Usuario usuario = entry.getValue();
            System.out.println("rut: " + usuario.getRut() + " , Nombre: " + usuario.getNombre());
        });
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
        //Por implementar
    }

    public void cargarComicsDesdeCSV(String rutaArchivo) {
        //Por implementar
    }
}
