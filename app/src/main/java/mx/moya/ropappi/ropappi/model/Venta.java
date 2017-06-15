package mx.moya.ropappi.ropappi.model;

/**
 * Created by jesusmiguelflores on 06/06/17.
 */

public class Venta {

    private int id;
    private int id_producto;
    private String nombre_product;
    private int precio_product;
    private String descripcion_product;
    private int id_category_product;
    private String talla_product;
    private String foto_product;
    private String direccion;

    public Venta() {
        this.id = -1;
        this.id_producto = -1;
        this.nombre_product = "";
        this.precio_product = -1;
        this.descripcion_product = "";
        this.id_category_product = -1;
        this.talla_product = "";
        this.foto_product = "";
        this.direccion = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre_product() {
        return nombre_product;
    }

    public void setNombre_product(String nombre_product) {
        this.nombre_product = nombre_product;
    }

    public int getPrecio_product() {
        return precio_product;
    }

    public void setPrecio_product(int precio_product) {
        this.precio_product = precio_product;
    }

    public String getDescripcion_product() {
        return descripcion_product;
    }

    public void setDescripcion_product(String descripcion_product) {
        this.descripcion_product = descripcion_product;
    }

    public int getId_category_product() {
        return id_category_product;
    }

    public void setId_category_product(int id_category_product) {
        this.id_category_product = id_category_product;
    }

    public String getTalla_product() {
        return talla_product;
    }

    public void setTalla_product(String talla_product) {
        this.talla_product = talla_product;
    }

    public String getFoto_product() {
        return foto_product;
    }

    public void setFoto_product(String foto_product) {
        this.foto_product = foto_product;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
