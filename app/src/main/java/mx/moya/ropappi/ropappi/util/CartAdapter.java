package mx.moya.ropappi.ropappi.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import mx.moya.ropappi.ropappi.R;
import mx.moya.ropappi.ropappi.model.Product;

/**
 * Created by jesusmiguelflores on 05/06/17.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    public static interface CartCallbacks {
        public void OnCartSelected(Product product);
    }

    private ArrayList<Product> productArrayList;
    private Context context;
    private CartCallbacks cartCallbacks;

    public CartAdapter(ArrayList<Product> productArrayList, Context context) {
        this.productArrayList = productArrayList;
        this.context = context;
        this.cartCallbacks = (CartCallbacks) context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_cart, parent, false);
        return new ViewHolder(cardview);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Product product = productArrayList.get(position);

        Bitmap imagen = Base64BitmapUtil.decodeBase64(product.getFoto());
        holder.imageViewProduct.setImageBitmap(imagen);
        holder.textViewName.setText(product.getNombre());
        holder.textViewPrice.setText("$" + product.getPrecio());
        holder.textViewDescription.setText(product.getDescripcion());
        holder.buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartCallbacks.OnCartSelected(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewPrice, textViewDescription;
        ImageView imageViewProduct;
        Button buttonPay;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.text_view_product_name);
            this.imageViewProduct = (ImageView) itemView.findViewById(R.id.image_view_product);
            this.textViewPrice = (TextView) itemView.findViewById(R.id.text_view_product_price);
            this.textViewDescription = (TextView) itemView.findViewById(R.id.text_view_product_description);
            this.buttonPay = (Button) itemView.findViewById(R.id.button_buy);
        }
    }
}
