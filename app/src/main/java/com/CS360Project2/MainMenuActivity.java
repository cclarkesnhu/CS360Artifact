package com.CS360Project2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenuActivity extends AppCompatActivity {

    Button btnGoodsMovement, btnInventoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        btnGoodsMovement = findViewById(R.id.btnGoodsMovement);
        btnInventoryList = findViewById(R.id.btnInventoryList);

        // Open Goods Movement screen
        btnGoodsMovement.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenuActivity.this, GoodsMovementActivity.class);
            startActivity(intent);
        });

        // Open Inventory screen
        btnInventoryList.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenuActivity.this, InventoryActivity.class);
            startActivity(intent);
        });
    }
}

