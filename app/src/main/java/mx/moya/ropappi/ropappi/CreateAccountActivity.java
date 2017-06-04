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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import mx.moya.ropappi.ropappi.model.User;
import mx.moya.ropappi.ropappi.util.DialogUtil;
import mx.moya.ropappi.ropappi.util.JSONParser;
import mx.moya.ropappi.ropappi.util.Keys;
import mx.moya.ropappi.ropappi.util.SessionStateManager;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {

    SessionStateManager sessionStateManager;
    EditText editTextName, editTextEmail, editTextPassword;
    Button buttonCreateAccount;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        requestQueue = Volley.newRequestQueue(this);
        sessionStateManager = new SessionStateManager(this);

        editTextEmail = (EditText) findViewById(R.id.edit_text_email);
        editTextName = (EditText) findViewById(R.id.edit_text_name);
        editTextPassword = (EditText) findViewById(R.id.edit_text_password);

        buttonCreateAccount = (Button) findViewById(R.id.button_create_account);

        buttonCreateAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_create_account:
                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    progressDialog = DialogUtil.showProgressDialog(CreateAccountActivity.this, "Espere un momento", "Creando cuenta");

                    try {
                        JSONObject jsonObjectUser = new JSONObject();
                        jsonObjectUser.put(Keys.key_contrasena, password);
                        jsonObjectUser.put(Keys.key_correo, email);
                        jsonObjectUser.put(Keys.key_foto, "");
                        jsonObjectUser.put(Keys.key_nombre, name);
                        jsonObjectUser.put(Keys.key_tipo, 1);

                        makeRequest(jsonObjectUser);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                }
                break;
        }
    }

    private void makeRequest(final JSONObject jsonObjectUser) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Keys.url_post_create_account, jsonObjectUser, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    if (response.getBoolean(Keys.key_respuesta)) {
                        User currentUser = JSONParser.parseJsonToUser(jsonObjectUser);
                        sessionStateManager.saveSession(currentUser);
                        Toast.makeText(CreateAccountActivity.this, "Bievenido " + currentUser.getNombre(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(CreateAccountActivity.this, response.getString(Keys.key_msg), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String s;

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
