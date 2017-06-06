package mx.moya.ropappi.ropappi.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import mx.moya.ropappi.ropappi.model.Category;
import mx.moya.ropappi.ropappi.model.Product;
import mx.moya.ropappi.ropappi.model.User;

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
        product.setPrecio(response.getInt(Keys.key_precio));
        product.setNombre(response.getString(Keys.key_nombre));

        return product;

    }
}
