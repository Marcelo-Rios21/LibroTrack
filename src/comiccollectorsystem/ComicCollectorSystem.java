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
    private final Set<String> titulosUnicos;
    private final Set<String> rutsUnicos;
    private final TreeSet<Comic> comicsOrdenados; 
    private final TreeSet<Usuario> usuariosOrdenados;

    public ComicCollectorSystem() {
        comics = new ArrayList<>();
        usuarios = new HashMap<>();
        indicePorTitulo = new HashMap<>();
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

    public boolean prestarComic(String titulo, String rutUsuario) {
        //Por implementar
        return false;
    }

    public void devolverComic(String titulo) {
        //Por implementar
    }

    public void guardarUsuariosEnArchivo(String rutaArchivo) {
        //Por implementar
    }

    public void cargarComicsDesdeCSV(String rutaArchivo) {
        //Por implementar
    }
}
