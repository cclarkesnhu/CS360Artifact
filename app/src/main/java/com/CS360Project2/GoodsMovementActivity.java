package com.CS360Project2;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GoodsMovementActivity extends AppCompatActivity {

    EditText editProductId, editQuantity;
    TextView tvProductInfo;
    Button btnCheckId, btnAdd, btnSubtract, btnSetQuantity;

    DatabaseHelper db;
    String selectedProductId = null;
    String selectedName, selectedVendor;
    int selectedQuantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_movement);

        db = new DatabaseHelper(this);

        editProductId = findViewById(R.id.editProductId);
        editQuantity = findViewById(R.id.editQuantity);
        tvProductInfo = findViewById(R.id.tvProductInfo);
        btnCheckId = findViewById(R.id.btnCheckId);
        btnAdd = findViewById(R.id.btnAdd);
        btnSubtract = findViewById(R.id.btnSubtract);
        btnSetQuantity = findViewById(R.id.btnSetQuantity);

        // ==========================
        // CHECK PRODUCT ID
        // ==========================
        btnCheckId.setOnClickListener(v -> {

            String id = editProductId.getText().toString().trim();

            if (id.isEmpty()) {
                Toast.makeText(this, "Enter Product ID", Toast.LENGTH_SHORT).show();
                return;
            }

            Cursor cursor = db.getProductById(id);

            if (cursor.moveToFirst()) {

                selectedProductId = cursor.getString(0);
                selectedName = cursor.getString(1);
                selectedVendor = cursor.getString(2);
                selectedQuantity = cursor.getInt(3);

                updateProductInfo();

            } else {
                selectedProductId = null;
                tvProductInfo.setText("");
                Toast.makeText(this, "Product ID not found", Toast.LENGTH_SHORT).show();
            }

            cursor.close();
        });

        // ==========================
        // ADD 1
        // ==========================
        btnAdd.setOnClickListener(v -> {

            if (!checkProductSelected()) return;

            selectedQuantity += 1;
            db.updateQuantity(selectedProductId, selectedQuantity);
            updateProductInfo();
        });

        // ==========================
        // SUBTRACT 1
        // ==========================
        btnSubtract.setOnClickListener(v -> {

            if (!checkProductSelected()) return;

            if (selectedQuantity > 0) {
                selectedQuantity -= 1;
                db.updateQuantity(selectedProductId, selectedQuantity);
                updateProductInfo();
            }
        });

        // ==========================
        // SET NEW QUANTITY
        // ==========================
        btnSetQuantity.setOnClickListener(v -> {

            if (!checkProductSelected()) return;

            String qtyStr = editQuantity.getText().toString();

            if (qtyStr.isEmpty()) {
                Toast.makeText(this, "Enter quantity", Toast.LENGTH_SHORT).show();
                return;
            }

            int newQty = Integer.parseInt(qtyStr);
            if (newQty < 0) newQty = 0;

            selectedQuantity = newQty;

            db.updateQuantity(selectedProductId, selectedQuantity);

            updateProductInfo();
            editQuantity.setText("");
        });
    }

    // ==========================
    // HELPER METHOD
    // ==========================
    private boolean checkProductSelected() {
        if (selectedProductId == null) {
            Toast.makeText(this, "Check a valid Product ID first", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void updateProductInfo() {
        tvProductInfo.setText(
                "Name: " + selectedName +
                        "\nVendor: " + selectedVendor +
                        "\nCurrent Qty: " + selectedQuantity
        );
    }
}
