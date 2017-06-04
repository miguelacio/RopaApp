package mx.moya.ropappi.ropappi.model;

/**
 * Created by jesusmiguelflores on 03/06/17.
 */

public class Category {
    private int id;
    private String nombre;
    private String descripcion;
    private String foto;

    public Category() {
        this.id = -1;
        this.nombre = "";
        this.descripcion = "";
        this.foto = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
