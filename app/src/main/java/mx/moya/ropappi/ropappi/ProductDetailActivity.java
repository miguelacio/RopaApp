package mx.moya.ropappi.ropappi;

import android.content.DialogInterface;
import android.content.Intent;
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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import mx.moya.ropappi.ropappi.model.User;
import mx.moya.ropappi.ropappi.util.SessionStateManager;

import static mx.moya.ropappi.ropappi.R.id.text_view_product_name;

public class ProductDetailActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageViewProduct;
    TextView textViewProductName, textViewProductDescription;
    Button buttonBuy;
    RequestQueue requestQueue;
    SessionStateManager sessionStateManager;
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
        buttonBuy = (Button) findViewById(R.id.button_buy);

        buttonBuy.setOnClickListener(this);
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
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
