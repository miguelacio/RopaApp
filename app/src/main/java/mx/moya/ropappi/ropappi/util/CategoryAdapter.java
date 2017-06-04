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

/**
 * Created by jesusmiguelflores on 03/06/17.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    public static interface CategoryCallbacks {
        public void OnCategorySelected(Category category);
    }

    private Context context;
    private ArrayList<Category> arrayList;
    private CategoryCallbacks categoryCallbacks;

    public CategoryAdapter(Context context, ArrayList<Category> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.categoryCallbacks = (CategoryCallbacks) context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_category, parent, false);
        return new ViewHolder(cardview);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Category category = arrayList.get(position);

        Bitmap imagen = Base64BitmapUtil.decodeBase64(category.getFoto());
        holder.imageViewCategory.setImageBitmap(imagen);
        holder.textViewName.setText(category.getNombre());
        holder.clickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryCallbacks.OnCategorySelected(category);
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
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
