package mx.moya.ropappi.ropappi;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import mx.moya.ropappi.ropappi.model.Product;
import mx.moya.ropappi.ropappi.util.DialogUtil;
import mx.moya.ropappi.ropappi.util.JSONParser;
import mx.moya.ropappi.ropappi.util.Keys;
import mx.moya.ropappi.ropappi.util.ProductAdapter;
import mx.moya.ropappi.ropappi.util.SessionStateManager;

public class ProductsActivity extends AppCompatActivity implements ProductAdapter.ProductCallbacks {

    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    SessionStateManager sessionStateManager;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        requestQueue = Volley.newRequestQueue(this);
        sessionStateManager = new SessionStateManager(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ProductsActivity.this, 2);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        id = getIntent().getIntExtra("id", -1);
        String nombreCategory = getIntent().getStringExtra("nombre");
        setTitle(nombreCategory);

        makeRequest();
    }

    private void makeRequest() {
        progressDialog = DialogUtil.showProgressDialog(this, "Espere un momento", "Obteniendo Productos");

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Keys.url_get_products_by_category + id, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressDialog.dismiss();

                try {
                    ArrayList<Product> arrayList = JSONParser.parseJsonToProducts(response);
                    ProductAdapter productAdapter = new ProductAdapter(arrayList, ProductsActivity.this);
                    recyclerView.setAdapter(productAdapter);
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

        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void OnProductSelected(Product product) {
        Intent intent = new Intent(ProductsActivity.this, ProductDetailActivity.class);
        intent.putExtra("id", product.getId());
        intent.putExtra("nombre", product.getNombre());
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_acitivity_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_go_to_cart:

                if (sessionStateManager.getCurrentUser() != null) {
                    Intent intent = new Intent(ProductsActivity.this, CartActivity.class);
                    startActivity(intent);
                } else {
                    new AlertDialog.Builder(ProductsActivity.this)
                            .setTitle("Alto ahí")
                            .setMessage("Para continuar necesitamos que inicies sesión")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent intent = new Intent(ProductsActivity.this, LoginActivity.class);
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
}
