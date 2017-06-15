package mx.moya.ropappi.ropappi.model;

/**
 * Created by jesusmiguelflores on 03/06/17.
 */

public class Product {

    private int id;
    private String nombre;
    private String descripcion;
    private int precio;
    private int id_category;
    private String talla;
    private String foto;

    public Product() {
        this.talla = "";
        this.id = -1;
        this.nombre = "";
        this.descripcion = "";
        this.precio = -1;
        this.id_category = -1;
        this.foto = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
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

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
