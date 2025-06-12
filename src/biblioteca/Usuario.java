package biblioteca;

public class Usuario {
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
}
