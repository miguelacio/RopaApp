package mx.moya.ropappi.ropappi.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import mx.moya.ropappi.ropappi.model.Carrito;
import mx.moya.ropappi.ropappi.model.Category;
import mx.moya.ropappi.ropappi.model.Product;
import mx.moya.ropappi.ropappi.model.User;
import mx.moya.ropappi.ropappi.model.Venta;

/**
 * Created by jesusmiguelflores on 03/06/17.
 */

public class JSONParser {

    public static ArrayList<Category> parseJsonToCategoria(JSONArray jsonArray) throws JSONException {
        ArrayList<Category> categoryArrayList = new ArrayList<>();
        Category category;

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObjectCategoria = jsonArray.getJSONObject(i);
            category = new Category();
            category.setId(jsonObjectCategoria.getInt(Keys.key_id));
            category.setDescripcion(jsonObjectCategoria.getString(Keys.key_descripcion));
            category.setFoto(jsonObjectCategoria.getString(Keys.key_foto));
            category.setNombre(jsonObjectCategoria.getString(Keys.key_nombre));
            categoryArrayList.add(category);
        }

        return categoryArrayList;
    }


    public static ArrayList<Product> parseJsonToProducts(JSONArray jsonArray) throws JSONException {
        ArrayList<Product> productArrayList = new ArrayList<>();
        Product product;

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObjectCategoria = jsonArray.getJSONObject(i);
            product = new Product();
            product.setId(jsonObjectCategoria.getInt(Keys.key_id));
            product.setDescripcion(jsonObjectCategoria.getString(Keys.key_descripcion));
            product.setFoto(jsonObjectCategoria.getString(Keys.key_foto));
            product.setPrecio(jsonObjectCategoria.getInt(Keys.key_precio));
            product.setTalla(jsonObjectCategoria.getString(Keys.key_talla));
            product.setNombre(jsonObjectCategoria.getString(Keys.key_nombre));
            productArrayList.add(product);
        }

        return productArrayList;
    }

    public static User parseJsonToUser(JSONObject jsonObjectUser) throws JSONException {
        User user = new User();

        user.setNombre(jsonObjectUser.getString(Keys.key_nombre));
        user.setCorreo(jsonObjectUser.getString(Keys.key_correo));
        user.setId(jsonObjectUser.getInt(Keys.key_id));
        return user;
    }

    public static Product parseJsonToProduct(JSONObject response) throws JSONException {
        Product product = new Product();

        product.setId(response.getInt(Keys.key_id));
        product.setDescripcion(response.getString(Keys.key_descripcion));
        product.setFoto(response.getString(Keys.key_foto));
        product.setTalla(response.getString(Keys.key_talla));
        product.setPrecio(response.getInt(Keys.key_precio));
        product.setNombre(response.getString(Keys.key_nombre));

        return product;

    }


    public static ArrayList<Carrito> parseJsonToCarritos(JSONArray jsonArray) throws JSONException {
        ArrayList<Carrito> carritoArrayList = new ArrayList<>();
        Carrito product;

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObjectCategoria = jsonArray.getJSONObject(i);
            product = new Carrito();
            product.setId(jsonObjectCategoria.getInt(Keys.key_id));
            product.setDescripcion_product(jsonObjectCategoria.getString(Keys.key_descripcion_product));
            product.setFoto_product(jsonObjectCategoria.getString(Keys.key_foto_product));
            product.setPrecio_product(jsonObjectCategoria.getInt(Keys.key_precio_product));
            product.setTalla_product(jsonObjectCategoria.getString(Keys.key_talla_product));
            product.setNombre_product(jsonObjectCategoria.getString(Keys.key_nombre_product));
            carritoArrayList.add(product);
        }

        return carritoArrayList;
    }

    public static ArrayList<Venta> parseJsonToVentas(JSONArray jsonArray) throws JSONException {
        ArrayList<Venta> ventaArrayList = new ArrayList<>();
        Venta product;

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            product = new Venta();
            product.setId(jsonObject.getInt(Keys.key_id));
            product.setDescripcion_product(jsonObject.getString(Keys.key_descripcion_product));
            product.setFoto_product(jsonObject.getString(Keys.key_foto_product));
            product.setPrecio_product(jsonObject.getInt(Keys.key_precio_product));
            product.setTalla_product(jsonObject.getString(Keys.key_talla_product));
            product.setNombre_product(jsonObject.getString(Keys.key_nombre_product));
            ventaArrayList.add(product);
        }

        return ventaArrayList;
    }
}
