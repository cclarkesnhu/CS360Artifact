package com.CS360Project2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.content.pm.PackageManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class InventoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    InventoryAdapter adapter;
    DatabaseHelper db;
    List<Product> productList = new ArrayList<>();

    private static final int LOW_STOCK_THRESHOLD = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        db = new DatabaseHelper(this);

        createNotificationChannel();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadProducts();

        adapter = new InventoryAdapter(productList);
        recyclerView.setAdapter(adapter);
    }

    // Reload data when returning from GoodsMovementActivity
    @Override
    protected void onResume() {
        super.onResume();
        loadProducts();
        adapter.setProductList(productList);
    }

    private void loadProducts() {
        productList.clear();

        Cursor cursor = db.getAllProducts();

        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String vendor = cursor.getString(2);
                int qty = cursor.getInt(3);

                productList.add(new Product(name, id, vendor, qty));

                // 🔔 Check for low inventory
                if (qty <= LOW_STOCK_THRESHOLD) {
                    sendLowInventoryNotification(name);
                }

            } while (cursor.moveToNext());
        }

        cursor.close();
    }

    // ===============================
    // Notification Channel
    // ===============================
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "low_inventory_channel",
                    "Low Inventory Alerts",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Notifies when inventory is low");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    // ===============================
    // Send Notification
    // ===============================
    private void sendLowInventoryNotification(String productName) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                        != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                    1001
            );
            return;
        }

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, "low_inventory_channel")
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("Low Inventory Warning")
                        .setContentText(productName + " is running low!")
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);

        notificationManager.notify(
                (int) System.currentTimeMillis(),
                builder.build()
        );
    }
}
