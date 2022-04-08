package com.example.frige_20;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.frige_20.Adapters.FrigeAdapter;
import com.example.frige_20.Fragments.AddDialog;
import com.example.frige_20.Parser.JsonParser;
import com.example.frige_20.RoomDB.AppDatabase;
import com.example.frige_20.RoomDB.Product;
import com.example.frige_20.RoomDB.ProductName;
import com.example.frige_20.Utils.LocationUtils;
import com.example.frige_20.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnDismissListener {
    private List<Product> list;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch mySwitch;
    private AppDatabase db;
    private FrigeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.frige_20.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RecyclerView view = binding.frigeView;
        FloatingActionButton addButton = binding.addButton;
        FloatingActionButton resetDb = binding.resetDb;
        mySwitch = binding.switch1;

        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        LocationUtils locationListener = new LocationUtils();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 10, locationListener);



        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "products").allowMainThreadQueries().build();

        list = new ArrayList<>();
        list = db.getProductDao().getFrige();
        adapter = new FrigeAdapter(this, db, locationListener);
        adapter.setList(list);

        view.setAdapter(adapter);



        resetDb.setOnClickListener(view12 -> {
            db.getProductDao().nukeTableProduct();
            db.getProductDao().nukeTable();
            fillDB(db);
            Toast.makeText(getApplicationContext(), "Вы сбросили базу данных", Toast.LENGTH_LONG).show();
            checkSwitch();
        });

        adapter.setListener(() -> checkSwitch());

        addButton.setOnClickListener(view1 -> {
            AddDialog dialog = new AddDialog(db, locationListener);
            dialog.show(getSupportFragmentManager(), "ffffff");
            getSupportFragmentManager().executePendingTransactions();
        });


        mySwitch.setOnCheckedChangeListener((buttonView, isChecked) -> checkSwitch());

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback
                (0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                Product product = list.get(position);
                list.remove(position);
                db.getProductDao().deleteProduct(product);
                adapter.notifyItemRemoved(position);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(view);
    }

    private void fillDB(AppDatabase db){
        try {
            JSONObject obj = new JSONObject(new JsonParser().loadJSONFromAsset(this));
            List<String> listProducts = new ArrayList<>();
            listProducts.add("milk");
            listProducts.add("meat");
            listProducts.add("vegetables");
            listProducts.add("fruit");
            for (String s:
                    listProducts) {
                JSONArray m_jArry = obj.getJSONArray(s);
                ProductName productName = null;
                for (int i = 0; i < m_jArry.length(); i++) {
                    JSONObject jo_inside = m_jArry.getJSONObject(i);
                    productName = new ProductName(jo_inside.getLong("id"),
                            jo_inside.getString("name"), jo_inside.getString("type"),
                            jo_inside.getString("variety"), jo_inside.getString("time"),
                            jo_inside.getInt("canFreeze") == 1);
                    db.getProductDao().insert(productName);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("-------------------------"+e);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        Toast.makeText(this, "Вы добавили продукт", Toast.LENGTH_LONG).show();
        checkSwitch();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void checkSwitch(){
        if(mySwitch.isChecked()){
            mySwitch.setText("Морозильник");
            list = db.getProductDao().getFreeze();
        }
        else {
            mySwitch.setText("Холодильник");
            list = db.getProductDao().getFrige();
        }
        adapter.setList(list);
        adapter.notifyDataSetChanged();
    }


}