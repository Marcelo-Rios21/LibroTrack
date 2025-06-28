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
        //Por implementar
    }

    public void agregarUsuario(Usuario usuario) {
        //Por implementar
    }

    public List<Comic> listarComicsDisponibles() {
        //Por implementar
        return null;
    }

    public Usuario buscarUsuarioPorRut(String rut) {
        //Por implementar
        return null;
    }

    public Comic buscarComicPorTitulo(String titulo) {
        //Por implementar
        return null;
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
