package com.example.dienlanh24h;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {

    private Context context;
    private List<ServiceItem> serviceList;
    private DataManager dataManager;

    public ServiceAdapter(Context context, List<ServiceItem> serviceList, DataManager dataManager) {
        this.context = context;
        this.serviceList = serviceList;
        this.dataManager = dataManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_service, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ServiceItem service = serviceList.get(position);
        holder.tvName.setText(service.getName());
        holder.tvPrice.setText(service.getPrice());
        holder.tvCategory.setText(service.getCategory());
        holder.imgService.setImageResource(service.getImageResource());

        holder.btnBook.setOnClickListener(v -> {
            Intent intent = new Intent(context, CheckoutActivity.class);
            intent.putExtra("itemName", service.getName());
            intent.putExtra("itemType", "service");
            intent.putExtra("price", service.getPrice());
            intent.putExtra("quantity", 1);
            intent.putExtra("totalAmount", service.getPrice());
            context.startActivity(intent);
        });

        holder.btnCall.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(android.net.Uri.parse("tel:" + service.getPhone()));
            context.startActivity(callIntent);
        });
    }

    @Override
    public int getItemCount() { return serviceList.size(); }

    public void updateList(List<ServiceItem> newList) {
        this.serviceList = newList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvCategory;
        ImageView imgService;
        Button btnCall, btnBook;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            imgService = itemView.findViewById(R.id.imgService);
            btnCall = itemView.findViewById(R.id.btnCall);
            btnBook = itemView.findViewById(R.id.btnBook);
        }
    }
}