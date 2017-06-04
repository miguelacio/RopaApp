package mx.moya.ropappi.ropappi.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import mx.moya.ropappi.ropappi.R;
import mx.moya.ropappi.ropappi.model.Category;
import mx.moya.ropappi.ropappi.model.Product;

/**
 * Created by jesusmiguelflores on 03/06/17.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    public static interface ProductCallbacks {
        public void OnProductSelected(Product product);
    }

    private ArrayList<Product> productArrayList;
    private Context context;
    private ProductCallbacks productCallbacks;

    public ProductAdapter(ArrayList<Product> productArrayList, Context context) {
        this.productArrayList = productArrayList;
        this.context = context;
        this.productCallbacks = (ProductCallbacks) context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_category, parent, false);
        return new ViewHolder(cardview);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Product product = productArrayList.get(position);

        Bitmap imagen = Base64BitmapUtil.decodeBase64(product.getFoto());
        holder.imageViewCategory.setImageBitmap(imagen);
        holder.textViewName.setText(product.getNombre());
        holder.clickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productCallbacks.OnProductSelected(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        ImageView imageViewCategory;
        View clickView;

        public ViewHolder(View itemView) {
            super(itemView);

            this.textViewName = (TextView) itemView.findViewById(R.id.text_view_category_name);
            this.imageViewCategory = (ImageView) itemView.findViewById(R.id.image_view_category);
            this.clickView = itemView.findViewById(R.id.click_view);
        }
    }
}
