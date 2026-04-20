package com.example.dienlanh24h;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private Context context;
    private List<ProductItem> productList;
    private DataManager dataManager;

    public ProductAdapter(Context context, List<ProductItem> productList, DataManager dataManager) {
        this.context = context;
        this.productList = productList;
        this.dataManager = dataManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductItem product = productList.get(position);
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText(product.getPrice());
        holder.tvCategory.setText(product.getCategory());
        holder.tvStock.setText("Còn " + product.getStock() + " sản phẩm");
        holder.imgProduct.setImageResource(product.getImageResource());

        holder.btnBuy.setOnClickListener(v -> {
            Intent intent = new Intent(context, CheckoutActivity.class);
            intent.putExtra("itemName", product.getName());
            intent.putExtra("itemType", "product");
            intent.putExtra("price", product.getPrice());
            intent.putExtra("quantity", 1);
            intent.putExtra("totalAmount", product.getPrice());
            context.startActivity(intent);
        });

        holder.btnDetail.setOnClickListener(v -> {
            Toast.makeText(context, product.getDescription(), Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public int getItemCount() { return productList.size(); }

    public void updateList(List<ProductItem> newList) {
        this.productList = newList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvCategory, tvStock;
        ImageView imgProduct;
        Button btnBuy, btnDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvStock = itemView.findViewById(R.id.tvStock);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            btnBuy = itemView.findViewById(R.id.btnBuy);
            btnDetail = itemView.findViewById(R.id.btnDetail);
        }
    }
}