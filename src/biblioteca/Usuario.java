package biblioteca;

public class Usuario implements Comparable<Usuario> {
    private final String rut;
    private final String nombre;

    
    public Usuario(String rut, String nombre) {
        this.rut = rut;
        this.nombre = nombre;
    }

    public String getRut() {
        return rut;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "RUT: " + rut + " Nombre: " + nombre;
    }

    @Override
    public int compareTo(Usuario otro) {
        return this.nombre.compareToIgnoreCase(otro.nombre);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Usuario otro = (Usuario) obj;
        return this.nombre.equalsIgnoreCase(otro.nombre);
    }

    @Override
    public int hashCode() {
        return nombre.toLowerCase().hashCode();
    }
}
