package mx.moya.ropappi.ropappi;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.service.voice.VoiceInteractionSession;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
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

import mx.moya.ropappi.ropappi.model.Carrito;
import mx.moya.ropappi.ropappi.model.Product;
import mx.moya.ropappi.ropappi.model.User;
import mx.moya.ropappi.ropappi.util.CartAdapter;
import mx.moya.ropappi.ropappi.util.DialogUtil;
import mx.moya.ropappi.ropappi.util.JSONParser;
import mx.moya.ropappi.ropappi.util.Keys;
import mx.moya.ropappi.ropappi.util.ProductAdapter;
import mx.moya.ropappi.ropappi.util.SessionStateManager;

public class CartActivity extends AppCompatActivity implements CartAdapter.CartCallbacks, View.OnClickListener {

    SessionStateManager sessionStateManager;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    User currentUser;
    Button buttonPayAll;
    ArrayList<Carrito> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        sessionStateManager = new SessionStateManager(this);
        requestQueue = Volley.newRequestQueue(this);
        currentUser = sessionStateManager.getCurrentUser();

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        buttonPayAll = (Button) findViewById(R.id.button_buy_all);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        buttonPayAll.setOnClickListener(this);
        makeRequestCartData();
    }

    private void makeRequestCartData() {


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Keys.url_get_my_cart + currentUser.getId(), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                try {
                    arrayList = JSONParser.parseJsonToCarritos(response);
                    CartAdapter cartAdapter = new CartAdapter(arrayList, CartActivity.this);
                    int count = 0;
                    for (Carrito p : arrayList) {
                        count = count + p.getPrecio_product();
                    }
                    buttonPayAll.setText("Pagar todo $" + count);
                    recyclerView.setAdapter(cartAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void OnCartSelected(final Carrito product) {
        new AlertDialog.Builder(CartActivity.this)
                .setTitle("Ingresa los datos de tu tarjeta")
                .setView(R.layout.dialog_payment)
                .setPositiveButton("Comprar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        payOne(Keys.url_delete_pay_single + product.getId());
                        makeRequestCartData();

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }

    private void payOne(String s) {


        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.DELETE, s, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                if (response != null) {


                } else {
                    Toast.makeText(CartActivity.this, "Algo salió mal, verifica tus datos", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_buy_all:
                new AlertDialog.Builder(CartActivity.this)
                        .setTitle("Ingresa los datos de tu tarjeta")
                        .setView(R.layout.dialog_payment)
                        .setPositiveButton("Comprar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                progressDialog = DialogUtil.showProgressDialog(CartActivity.this, "Espere un momento", "Pagando productos");
                                for (Carrito p : arrayList) {
                                    payOnebyOne(Keys.url_delete_pay_single + p.getId());
                                }
                                progressDialog.dismiss();
                                makeRequestCartData();

                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
                break;

        }

    }


    private void payOnebyOne(String s) {

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.DELETE, s, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                if (response != null) {


                } else {
                    progressDialog.dismiss();
                    Toast.makeText(CartActivity.this, "Algo salió mal, verifica tus datos", Toast.LENGTH_SHORT).show();
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

}
