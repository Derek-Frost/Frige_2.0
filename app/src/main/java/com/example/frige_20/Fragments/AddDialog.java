package com.example.frige_20.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.frige_20.Adapters.AddDialogAdapter;
import com.example.frige_20.MainActivity;
import com.example.frige_20.R;
import com.example.frige_20.RoomDB.AppDatabase;
import com.example.frige_20.RoomDB.Product;
import com.example.frige_20.RoomDB.ProductName;
import com.example.frige_20.Utils.DataUtils;
import com.example.frige_20.Utils.LocationUtils;
import com.example.frige_20.databinding.AddDialogBinding;

import java.util.Calendar;

public class AddDialog extends DialogFragment {

    private final AppDatabase db;
    @SuppressLint("StaticFieldLeak")
    private static LocationUtils locationListener;

    public AddDialog(AppDatabase db, LocationUtils locationListener) {
        this.db = db;
        AddDialog.locationListener = locationListener;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_dialog, container, false);

        com.example.frige_20.databinding.AddDialogBinding binding = AddDialogBinding.bind(v);
        RecyclerView view = binding.list;
        Button button = binding.button;
        CheckBox checkBox = binding.checkBox;
        EditText editCount = binding.editCount;
        EditText editWeight= binding.editWeight;
        AddDialogAdapter adapter = new AddDialogAdapter(getContext(), db);
        view.setLayoutManager(new LinearLayoutManager(getContext()));
        view.setAdapter(adapter);

        button.setOnClickListener(view1 -> {
            if(String.valueOf(editCount.getText()).matches("[-+]?\\d+") &&
                    Integer.parseInt(String.valueOf(editCount.getText())) > 0 &&
                    Float.parseFloat(String.valueOf(editWeight.getText())) > 0) {
                DataUtils helpData = new DataUtils();
                Product product = adapter.getFinalProduct();
                ProductName productName = db.getProductDao().getProductNameId(product.getIdproduct());
                product.setCount(Integer.parseInt(String.valueOf(editCount.getText())));
                product.setWeight(Float.parseFloat(String.valueOf(editWeight.getText()))
                        / Float.parseFloat(String.valueOf(editCount.getText())));
                product.setСoord(locationListener.getLocationS());
                product.setTime(String.valueOf(Calendar.getInstance().getTime()));
                product.setTimeEnd(helpData.plusDate(Calendar.getInstance(), productName.getTime()));
                if (checkBox.isChecked()) product.setIdFrige(1);
                db.getProductDao().insert(product);
                dismiss();
            } else Toast.makeText(getContext(), "Проверьте введенные данные", Toast.LENGTH_LONG).show();
        });
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        final MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }

    }

}
