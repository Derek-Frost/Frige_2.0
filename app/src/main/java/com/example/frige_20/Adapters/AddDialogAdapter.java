package com.example.frige_20.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.frige_20.R;
import com.example.frige_20.RoomDB.AppDatabase;
import com.example.frige_20.RoomDB.Product;
import com.example.frige_20.RoomDB.ProductName;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddDialogAdapter extends RecyclerView.Adapter<AddDialogAdapter.ViewHolder>{
    private final LayoutInflater inflater;
    private final Context context;
    private final AppDatabase db;
    private final List<String> typesProduct;
    private List<ProductName> productNames;
    private boolean isType;
    private String type;
    private int count;
    private Product finalProduct;

    public AddDialogAdapter(Context context, AppDatabase db) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.db = db;
        isType = true;
        this.typesProduct = db.getProductDao().getType();
        Set<String> set = new HashSet<>(typesProduct);
        typesProduct.clear();
        typesProduct.addAll(set);
        this.count = typesProduct.size();
    }
    @NonNull
    @Override
    public AddDialogAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.add_dialog_row, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AddDialogAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if(isType) {
            String type = typesProduct.get(position);
            holder.text1.setText(type);
            holder.text2.setText("");
        } else {
            ProductName productName = productNames.get(position);
            holder.text1.setText(productName.getName());
            holder.text2.setText(productName.getVariety());
        }


        holder.text1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                if(isType) {
                    type = typesProduct.get(position);
                    productNames = db.getProductDao().getProductName(type);
                    isType = false;
                    count = productNames.size();
                    notifyItemRangeRemoved(0, typesProduct.size());
                    //holder.text2.setText(type);
                } else {
                    finalProduct = new Product(db.getProductDao().getProductId(productNames.get(position).getName(),
                            productNames.get(position).getVariety(), productNames.get(position).getType()), 0, 0, 0);
                    isType = true;
                    typesProduct.clear();
                    count = 0;
                    notifyDataSetChanged();
                }
            }
        });

    }

    public Product getFinalProduct(){
        return finalProduct;
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView text1, text2;
        ViewHolder(View view) {
            super(view);
            text1 = view.findViewById(R.id.textView3);
            text2 = view.findViewById(R.id.textView4);
        }
    }
}
