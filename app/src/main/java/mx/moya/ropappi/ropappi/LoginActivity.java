package mx.moya.ropappi.ropappi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import mx.moya.ropappi.ropappi.model.User;
import mx.moya.ropappi.ropappi.util.CategoryAdapter;
import mx.moya.ropappi.ropappi.util.DialogUtil;
import mx.moya.ropappi.ropappi.util.JSONParser;
import mx.moya.ropappi.ropappi.util.Keys;
import mx.moya.ropappi.ropappi.util.SessionStateManager;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    RequestQueue requestQueue;
    SessionStateManager sessionStateManager;
    EditText editTextEmail, editTextPassword;
    Button buttonLogin, buttonCreateAccpunt;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        requestQueue = Volley.newRequestQueue(this);
        sessionStateManager = new SessionStateManager(this);

        editTextEmail = (EditText) findViewById(R.id.edit_text_email);
        editTextPassword = (EditText) findViewById(R.id.edit_text_password);
        buttonCreateAccpunt = (Button) findViewById(R.id.button_create_account);
        buttonLogin = (Button) findViewById(R.id.button_log_in);

        buttonLogin.setOnClickListener(this);
        buttonCreateAccpunt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_create_account:
                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(intent);
                break;
            case R.id.button_log_in:

                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()) {

                    String finalURL = Keys.url_get_login_pt_1 + email + Keys.url_get_loginpt_2 + password;
                    makeRequest(finalURL);
                } else {
                    Toast.makeText(this, "Faltan campos que llenar", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void makeRequest(String finalURL) {

        progressDialog = DialogUtil.showProgressDialog(this, "Espere un momento", "Obteniendo categorias");

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, finalURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                if (response != null) {
                    try {
                        User currentUser = JSONParser.parseJsonToUser(response);
                        sessionStateManager.saveSession(currentUser);
                        Toast.makeText(LoginActivity.this, "Bienvenido" + currentUser.getNombre(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "Algo salió mal, verifica tus datos", Toast.LENGTH_SHORT).show();
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
