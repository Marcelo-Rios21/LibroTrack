package biblioteca;

public class Comic extends Libro {
    
    public enum TipoComic {
        MANGA, NOVELA_GRAFICA, FANZINE
    }

    private final TipoComic tipo;
    private final int numeroEdicion;
    private final boolean esRaro;
    private final String editorial;

    public Comic(String titulo, String autor, TipoComic tipo, int numeroEdicion, boolean esRaro, String editorial) {
        super(titulo, autor);
        this.tipo = tipo;
        this.numeroEdicion = numeroEdicion;
        this.esRaro = esRaro;
        this.editorial = editorial;
    }

    public Comic(String titulo, String autor, boolean prestado, TipoComic tipo, int numeroEdicion, boolean esRaro, String editorial) {
        super(titulo, autor, prestado);
        this.tipo = tipo;
        this.numeroEdicion = numeroEdicion;
        this.esRaro = esRaro;
        this.editorial = editorial;
    }

    public TipoComic getTipo() {
        return tipo;
    }

    public int getNumeroEdicion() {
        return numeroEdicion;
    }

    public boolean isEsRaro() {
        return esRaro;
    }

    public String getEditorial() {
        return editorial;
    }

    @Override
    public String toString() {
        String estado = estaPrestado() ? "Prestado" : "Disponible";
        String rareza = esRaro ? "Si" : "No";
        return "Comic: " + getTitulo() + " -- Autor: " + getAutor() + " -- Tipo: " + tipo +
                " -- Edicion: " + numeroEdicion + " -- Rareza: " + rareza + " -- Editorial: " + editorial +
                " -- Estado: " + estado;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }

        if (!(obj instanceof Comic otro)) {
            return false;
        }

        return this.numeroEdicion == otro.numeroEdicion &&
                this.tipo == otro.tipo &&
                this.editorial.equalsIgnoreCase(otro.editorial);
    }

    @Override
    public int hashCode() {
        return super.hashCode() + tipo.hashCode() + editorial.toLowerCase().hashCode() + numeroEdicion;
    }
}
