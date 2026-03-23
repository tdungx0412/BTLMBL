package com.example.dienlanh24h;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private DatabaseHelper dbHelper;

    public DataManager(Context context) {
        dbHelper = new DatabaseHelper(context);
        // Khởi tạo dữ liệu mẫu nếu database trống
        if (dbHelper.getAllServices().isEmpty()) {
            initDefaultServices();
        }
    }

    public List<ServiceItem> getServices() {
        return dbHelper.getAllServices();
    }

    public void addService(ServiceItem service) {
        dbHelper.addService(service);
    }

    public void updateService(ServiceItem updatedService) {
        dbHelper.updateService(updatedService);
    }

    public void deleteService(String id) {
        dbHelper.deleteService(id);
    }

    private void initDefaultServices() {
        List<ServiceItem> services = new ArrayList<>();
        services.add(new ServiceItem("1", "Vệ sinh máy lạnh", "150.000đ", "0909123456",
                "Vệ sinh sạch sẽ, bơm gas, kiểm tra dàn lạnh, bảo hành 30 ngày.",
                android.R.drawable.ic_menu_gallery, "Máy lạnh"));
        services.add(new ServiceItem("2", "Sửa tủ lạnh", "200.000đ", "0909654321",
                "Sửa block, thay gas, xử lý không làm lạnh, thay ron cửa.",
                android.R.drawable.ic_menu_gallery, "Tủ lạnh"));
        services.add(new ServiceItem("3", "Sửa máy giặt", "180.000đ", "0909111222",
                "Sửa board, thay dây curoa, vệ sinh lồng giặt, xử lý không vắt.",
                android.R.drawable.ic_menu_gallery, "Máy giặt"));
        services.add(new ServiceItem("4", "Lắp đặt điều hòa", "500.000đ", "0909333444",
                "Lắp đặt máy mới, dời máy cũ, đi ống đồng, đục tường.",
                android.R.drawable.ic_menu_gallery, "Máy lạnh"));
        services.add(new ServiceItem("5", "Sửa máy nước nóng", "150.000đ", "0909555666",
                "Thay thanh đốt, thay rơ le, xử lý rò điện, không nóng.",
                android.R.drawable.ic_menu_gallery, "Máy nước nóng"));

        for (ServiceItem item : services) {
            dbHelper.addService(item);
        }
    }
}
