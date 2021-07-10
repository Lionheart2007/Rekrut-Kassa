package com.pat.rekrutkassa;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

public class ItemAdapterNewOrder extends ArrayAdapter<Item> {
    private final String LOG_TAG = "Item Adapter";
    private SaveManager<Item> saveManager;
    private GridView toPopulate;
    private SaveManager<Order> currentOrder;

    public ItemAdapterNewOrder(@NonNull Context context, int resource, SaveManager<Item> saveManager, GridView gridView, SaveManager<Order> currentOrder) {
        super(context, resource, saveManager.getSaveList());
        this.saveManager = saveManager;
        this.toPopulate = gridView;
        this.currentOrder = currentOrder;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        Item currentItem = getItem(position);

        listItemView = LayoutInflater.from(getContext()).inflate(
                R.layout.square_item, parent, false);
        ((TextView) listItemView.findViewById(R.id.itemText)).setText(currentItem.getmTitle());
        ((TextView) listItemView.findViewById(R.id.itemQuantity)).setText(String.valueOf(currentItem.getmQuantity()));
        ((TextView) listItemView.findViewById(R.id.itemUnit)).setText(currentItem.getmUnit());

        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(currentOrder.getCount() == -1){
                    ArrayList<Item> orderList = new ArrayList<>();
                    orderList.add(currentItem);
                    Order order = new Order(orderList,null);

                    currentOrder.save(order);
                }
                else{
                    Order order = currentOrder.getSaveablyById(1);
                    order.addItem(currentItem);
                    currentOrder.editSaveable(1,order);

                }

            }
        });


        return listItemView;
    }
}
