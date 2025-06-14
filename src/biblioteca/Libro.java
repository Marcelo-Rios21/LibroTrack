package biblioteca;

public class Libro {
    private final String titulo;
    private final String autor;
    private boolean prestado;

    public Libro(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
        this.prestado = false;
    }
    
    public Libro(String titulo, String autor, boolean prestado) {
        this.titulo = titulo;
        this.autor = autor;
        this.prestado = prestado;
    }


    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public boolean estaPrestado() {
        return prestado;
    }

    public void prestar() {
        this.prestado = true;
    }

    public void devolver() {
        this.prestado = false;
    }

    @Override
    public String toString() {
        String estado = prestado ? "Prestado" : "Disponible";
        return "Titulo: " + titulo + " Autor: " + autor + " Estado: " + estado;
    }
}