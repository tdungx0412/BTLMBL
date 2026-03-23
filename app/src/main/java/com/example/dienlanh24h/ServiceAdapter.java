package com.example.dienlanh24h;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {

    private Context context;
    private List<ServiceItem> serviceList;
    private DataManager dataManager;
    private OnServiceClickListener listener;

    public interface OnServiceClickListener {
        void onEdit(ServiceItem service);
    }

    public ServiceAdapter(Context context, List<ServiceItem> serviceList,
                          DataManager dataManager, OnServiceClickListener listener) {
        this.context = context;
        this.serviceList = serviceList;
        this.dataManager = dataManager;
        this.listener = listener;
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

        holder.btnDetail.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("service", service);
            context.startActivity(intent);
        });

        holder.btnCall.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + service.getPhone()));
            context.startActivity(callIntent);
        });

        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Quản lý dịch vụ")
                    .setMessage("Chọn hành động cho \"" + service.getName() + "\"")
                    .setPositiveButton("✏️ Sửa", (dialog, which) -> {
                        if (listener != null) {
                            listener.onEdit(service);
                        }
                    })
                    .setNegativeButton("🗑️ Xóa", (dialog, which) -> {
                        new AlertDialog.Builder(context)
                                .setTitle("Xác nhận xóa")
                                .setMessage("Bạn có chắc muốn xóa dịch vụ này?")
                                .setPositiveButton("Xóa", (d, w) -> {
                                    dataManager.deleteService(service.getId());
                                    serviceList.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, serviceList.size());
                                    Toast.makeText(context, "Đã xóa dịch vụ", Toast.LENGTH_SHORT).show();
                                })
                                .setNegativeButton("Hủy", null)
                                .show();
                    })
                    .setNeutralButton("Hủy", null)
                    .show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public void updateList(List<ServiceItem> newList) {
        this.serviceList = newList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvCategory;
        ImageView imgService;
        Button btnCall, btnDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            imgService = itemView.findViewById(R.id.imgService);
            btnCall = itemView.findViewById(R.id.btnCall);
            btnDetail = itemView.findViewById(R.id.btnDetail);
        }
    }
}