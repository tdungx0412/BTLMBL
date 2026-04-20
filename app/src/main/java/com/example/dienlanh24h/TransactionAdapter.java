package com.example.dienlanh24h;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private Context context;
    private List<Transaction> transactionList;
    private DataManager dataManager;

    public TransactionAdapter(Context context, List<Transaction> transactionList, DataManager dataManager) {
        this.context = context;
        this.transactionList = transactionList;
        this.dataManager = dataManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Transaction t = transactionList.get(position);

        holder.tvItemName.setText(t.getItemName());
        holder.tvDate.setText("📅 " + t.getDate());
        holder.tvQuantity.setText("SL: " + t.getQuantity());
        holder.tvPayment.setText(t.getPaymentMethod());
        holder.tvTotal.setText(t.getTotalAmount());
        holder.tvStatus.setText(getStatusText(t.getStatus()));

        String typeIcon = t.getItemType().equals("service") ? "🔧" : "🛒";
        holder.tvItemType.setText(typeIcon + " " + t.getItemTypeLabel());

        if (t.getStatus().equals("completed") || t.getStatus().equals("cancelled")) {
            holder.layoutActions.setVisibility(View.GONE);
        } else {
            holder.layoutActions.setVisibility(View.VISIBLE);
        }

        if (t.getItemType().equals("service") && t.getServiceDate() != null && !t.getServiceDate().isEmpty()) {
            holder.tvServiceDate.setVisibility(View.VISIBLE);
            holder.tvServiceDate.setText("📅 Hẹn: " + t.getServiceDate() + " " + t.getServiceTime());
        } else {
            holder.tvServiceDate.setVisibility(View.GONE);
        }

        holder.btnCancel.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Hủy đơn")
                    .setMessage("Bạn có chắc muốn hủy?")
                    .setPositiveButton("Hủy", (d, w) -> {
                        dataManager.updateTransactionStatus(t.getId(), "cancelled");
                        t.setStatus("cancelled");
                        notifyItemChanged(position);
                        Toast.makeText(context, "Đã hủy", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Không", null)
                    .show();
        });

        holder.btnDetail.setOnClickListener(v -> {
            String message = "Tên: " + t.getItemName() + "\n" +
                    "Loại: " + (t.getItemType().equals("service") ? "🔧 Dịch vụ" : "🛒 Sản phẩm") + "\n" +
                    "Khách: " + t.getCustomerName() + "\n" +
                    "SĐT: " + t.getPhone() + "\n" +
                    "Địa chỉ: " + t.getAddress() + "\n" +
                    "Tổng: " + t.getTotalAmount();

            if (t.getItemType().equals("service")) {
                message += "\n\n📅 Ngày hẹn: " + t.getServiceDate() + "\n" +
                        "⏰ Giờ hẹn: " + t.getServiceTime() + "\n" +
                        "📝 Ghi chú: " + t.getNotes();
            }

            new AlertDialog.Builder(context)
                    .setTitle("Chi tiết đơn")
                    .setMessage(message)
                    .setPositiveButton("OK", null)
                    .show();
        });
    }

    private String getStatusText(String status) {
        switch (status) {
            case "pending": return "⏳ Chờ xác nhận";
            case "completed": return "✅ Hoàn thành";
            case "cancelled": return "❌ Đã hủy";
            default: return status;
        }
    }

    @Override
    public int getItemCount() { return transactionList.size(); }

    public void updateList(List<Transaction> newList) {
        this.transactionList = newList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName, tvDate, tvQuantity, tvPayment, tvTotal, tvStatus, tvItemType, tvServiceDate;
        Button btnCancel, btnDetail;
        LinearLayout layoutActions;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvPayment = itemView.findViewById(R.id.tvPayment);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvItemType = itemView.findViewById(R.id.tvItemType);
            tvServiceDate = itemView.findViewById(R.id.tvServiceDate);
            btnCancel = itemView.findViewById(R.id.btnCancel);
            btnDetail = itemView.findViewById(R.id.btnDetail);
            layoutActions = itemView.findViewById(R.id.layoutActions);
        }
    }
}