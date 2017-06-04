package mx.moya.ropappi.ropappi.model;

/**
 * Created by jesusmiguelflores on 03/06/17.
 */

public class User {

    private String nombre;
    private String correo;
    private String foto;
    private int tipo;

    public User() {
        this.nombre = "";
        this.correo = "";
        this.foto = "";
        this.tipo = -1;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
