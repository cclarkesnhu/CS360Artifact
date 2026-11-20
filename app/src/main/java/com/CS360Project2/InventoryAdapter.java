package com.CS360Project2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> {

    private List<Product> productList;

    public InventoryAdapter(List<Product> productList) {
        this.productList = productList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvId, tvVendor, tvQuantity;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvId = itemView.findViewById(R.id.tvId);
            tvVendor = itemView.findViewById(R.id.tvVendor);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
        }
    }

    public void setProductList(List<Product> products) {
        this.productList = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_inventory, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.tvName.setText(product.name);
        holder.tvId.setText(product.id);
        holder.tvVendor.setText(product.vendor);
        holder.tvQuantity.setText("Qty: " + product.quantity);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
