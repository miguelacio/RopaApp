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

import mx.moya.ropappi.ropappi.model.Category;
import mx.moya.ropappi.ropappi.util.CategoryAdapter;
import mx.moya.ropappi.ropappi.util.DialogUtil;
import mx.moya.ropappi.ropappi.util.JSONParser;
import mx.moya.ropappi.ropappi.util.Keys;
import mx.moya.ropappi.ropappi.util.SessionStateManager;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.CategoryCallbacks {

    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    SessionStateManager sessionStateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Bienvenido a Armadillo");

        requestQueue = Volley.newRequestQueue(this);
        sessionStateManager = new SessionStateManager(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);


        makeRequest();
    }

    private void makeRequest() {
        progressDialog = DialogUtil.showProgressDialog(this, "Espere un momento", "Obteniendo categorias");

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Keys.url_get_all_categories, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressDialog.dismiss();

                try {
                    ArrayList<Category> arrayList = JSONParser.parseJsonToCategoria(response);
                    CategoryAdapter categoryAdapter = new CategoryAdapter(MainActivity.this, arrayList);
                    recyclerView.setAdapter(categoryAdapter);
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
    public void OnCategorySelected(Category category) {
        Intent intent = new Intent(MainActivity.this, ProductsActivity.class);
        intent.putExtra("id", category.getId());
        intent.putExtra("nombre", category.getNombre());
        startActivity(intent);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_acitivity_main, menu);

        if (sessionStateManager.getCurrentUser() == null) {
            menu.findItem(R.id.action_log_out).setVisible(true);
        } else {
            menu.findItem(R.id.action_log_out).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_go_to_cart:
                if (sessionStateManager.getCurrentUser() != null) {
                    Intent intent = new Intent(MainActivity.this, CartActivity.class);
                    startActivity(intent);
                } else {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Alto ahí")
                            .setMessage("Para continuar necesitamos que inicies sesión")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
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
            case R.id.action_log_out:

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Cerrar Sesión")
                        .setMessage("¿estás seguro de que quieres cerrar sesión?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                sessionStateManager.logOut();
                                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setIcon(R.mipmap.ic_launcher_round)
                        .show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
