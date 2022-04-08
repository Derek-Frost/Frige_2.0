package com.example.frige_20.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.frige_20.Fragments.MapsFragment;
import com.example.frige_20.MainActivity;
import com.example.frige_20.R;
import com.example.frige_20.RoomDB.AppDatabase;
import com.example.frige_20.RoomDB.Product;
import com.example.frige_20.RoomDB.ProductName;
import com.example.frige_20.Utils.DataUtils;
import com.example.frige_20.Utils.Listener;
import com.example.frige_20.Utils.LocationUtils;

import java.util.Calendar;
import java.util.List;

public class FrigeAdapter extends RecyclerView.Adapter<FrigeAdapter.ViewHolder>{
    private final LayoutInflater inflater;
    private static List<Product> products;
    private static AppDatabase db;
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private Listener listener;

    public FrigeAdapter(Context context, AppDatabase db,  LocationUtils locationListener) {
        products = db.getProductDao().getAllProducts();
        this.inflater = LayoutInflater.from(context);
        FrigeAdapter.db = db;
        FrigeAdapter.context = context;

    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void setList(List<Product> list){
        products = list;
    }


    @NonNull
    @Override
    public FrigeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FrigeAdapter.ViewHolder holder, int position) {
        Product product = products.get(position);
        ProductName productName = db.getProductDao().getProductNameId(product.getIdproduct());
        holder.name.setText(productName.getName());
        holder.variety.setText(productName.getVariety());
        holder.timer.setText(product.getTimeEnd());
        holder.count.setText(String.valueOf(product.getcount()));
        holder.weight.setText(String.valueOf(product.getweight() * product.getcount()));

    }

    @Override
    public int getItemCount() {
        return products.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView name, variety, timer, count, weight;
        final ImageButton buttonGPS, buttonMinus;
        ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.productName);
            variety = view.findViewById(R.id.productVariety);
            timer = view.findViewById(R.id.timer);
            count = view.findViewById(R.id.count);
            weight = view.findViewById(R.id.weight);
            buttonGPS = view.findViewById(R.id.gpsButton);
            buttonMinus = view.findViewById(R.id.minusButton);

            buttonGPS.setOnClickListener(view1 -> {
                if(getItem(getLayoutPosition()).getСoord() != null) {
                    MapsFragment map = new MapsFragment(getItem(getLayoutPosition()).getСoord(),
                            db.getProductDao().getNameProduct(getItem(getLayoutPosition()).getIdproduct()));
                    //Toast.makeText(context, getItem(getLayoutPosition()).getСoord(), Toast.LENGTH_LONG).show();
                    map.show(((MainActivity) context).getSupportFragmentManager(), "");
                } else {
                    Toast.makeText(context, "Вы положили этот продукт дома", Toast.LENGTH_LONG).show();
                }

            });

            buttonMinus.setOnClickListener(view12 -> {
                Product product = getItem(getLayoutPosition());
                ProductName productName = db.getProductDao().getProductNameId(product.getIdproduct());
                DataUtils helpData = new DataUtils();
                //Если продукт в холодильнике
                if(product.getIdFrige() == 0) {
                    //Если его количество больше 1, то уменьшаем на 1 и обновляем БД
                    //Если == 1, то удаляем объект из БД
                    if (product.getcount() > 1) {
                        product.setCount(product.getcount() - 1);
                        db.getProductDao().update(product);
                    } else db.getProductDao().deleteProduct(product);
                //Если продукт в морозильнике
                } else {
                    //Если его количество больше 1, то уменьшаем на 1 в морозильнике
                    // и добавляем 1 того же продукта в холодильнике
                    //Если == 1, то удаляем продукт из морозильника и его копию помещаем в холодильник
                    if(product.getcount() > 1){
                        product.setCount(product.getcount() - 1);
                        db.getProductDao().update(product);
                        db.getProductDao().insert(new Product(
                                product.getIdproduct(),
                                product.getweight(),
                                1,
                                0,
                                String.valueOf(Calendar.getInstance().getTime()),
                                helpData.plusDate(Calendar.getInstance(), productName.getTime())
                                ));
                    } else {
                        db.getProductDao().deleteProduct(product);
                        product.setIdFrige(0);
                        db.getProductDao().insert(product);
                    }
                }
                listener.onEvent();
            });
        }


        private Product getItem(int position){
            return products.get(position);
        }


    }



}
