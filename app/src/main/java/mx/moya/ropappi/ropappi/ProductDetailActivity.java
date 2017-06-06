package mx.moya.ropappi.ropappi;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import mx.moya.ropappi.ropappi.model.Category;
import mx.moya.ropappi.ropappi.model.Product;
import mx.moya.ropappi.ropappi.model.User;
import mx.moya.ropappi.ropappi.util.Base64BitmapUtil;
import mx.moya.ropappi.ropappi.util.CategoryAdapter;
import mx.moya.ropappi.ropappi.util.DialogUtil;
import mx.moya.ropappi.ropappi.util.JSONParser;
import mx.moya.ropappi.ropappi.util.Keys;
import mx.moya.ropappi.ropappi.util.SessionStateManager;

import static mx.moya.ropappi.ropappi.R.id.text_view_product_name;

public class ProductDetailActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageViewProduct;
    TextView textViewProductName, textViewProductDescription, textViewProductPrice;
    Button buttonBuy;
    RequestQueue requestQueue;
    SessionStateManager sessionStateManager;
    ProgressDialog progressDialog;
    int id;

    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        requestQueue = Volley.newRequestQueue(this);
        sessionStateManager = new SessionStateManager(this);
        currentUser = sessionStateManager.getCurrentUser();

        id = getIntent().getIntExtra("id", -1);
        String nombreCategory = getIntent().getStringExtra("nombre");

        toolbar.setTitle(nombreCategory);

        imageViewProduct = (ImageView) findViewById(R.id.image_view_product);
        textViewProductDescription = (TextView) findViewById(R.id.text_view_product_description);
        textViewProductName = (TextView) findViewById(text_view_product_name);
        textViewProductPrice = (TextView) findViewById(R.id.text_view_product_price);
        buttonBuy = (Button) findViewById(R.id.button_buy);

        buttonBuy.setOnClickListener(this);

        makeRequest();
    }

    private void makeRequest() {
        progressDialog = DialogUtil.showProgressDialog(this, "Espere un momento", "Obteniendo categorias");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Keys.url_get_detail_product + id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();

                try {
                    Product product = JSONParser.parseJsonToProduct(response);

                    textViewProductName.setText(product.getNombre());
                    textViewProductDescription.setText(product.getDescripcion());
                    textViewProductPrice.setText("$" + product.getPrecio());
                    Bitmap bitmap = Base64BitmapUtil.decodeBase64(product.getFoto());
                    imageViewProduct.setImageBitmap(bitmap);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_buy:
                if (sessionStateManager.getCurrentUser() != null) {
                    Toast.makeText(this, "yas", Toast.LENGTH_SHORT).show();
                } else {
                    new AlertDialog.Builder(ProductDetailActivity.this)
                            .setTitle("Alto ahí")
                            .setMessage("Para continuar necesitamos que inicies sesión")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent intent = new Intent(ProductDetailActivity.this, LoginActivity.class);
                                    startActivity(intent);

                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .setIcon(R.mipmap.ic_launcher_round)
                            .show();
                }
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_acitivity_cart, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_go_to_cart:

                if (sessionStateManager.getCurrentUser() != null) {
                    User currentUser = sessionStateManager.getCurrentUser();
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put(Keys.key_id_user, currentUser.getId());
                        jsonObject.put(Keys.key_id_producto, id);

                        addToCart(jsonObject);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    new AlertDialog.Builder(ProductDetailActivity.this)
                            .setTitle("Alto ahí")
                            .setMessage("Para continuar necesitamos que inicies sesión")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent intent = new Intent(ProductDetailActivity.this, LoginActivity.class);
                                    startActivity(intent);

                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .setIcon(R.mipmap.ic_launcher_round)
                            .show();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addToCart(JSONObject jsonObject) {

        progressDialog = DialogUtil.showProgressDialog(this, "Espere un momento", "Obteniendo categorias");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Keys.url_post_add_to_cart, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    if (response.getBoolean(Keys.key_respuesta)) {
                        new AlertDialog.Builder(ProductDetailActivity.this)
                                .setTitle("Producto agregado a tu carrito")
                                .setMessage("¿Deseas mirar tu carrito o seguit comprando?")
                                .setPositiveButton("Carrito", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
                                        startActivity(intent);

                                    }
                                })
                                .setNegativeButton("Seguir comprando", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                })
                                .setIcon(R.mipmap.ic_launcher_round)
                                .show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        });

        requestQueue.add(jsonObjectRequest);

    }
}
