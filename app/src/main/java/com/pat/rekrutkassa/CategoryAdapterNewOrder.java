package com.pat.rekrutkassa;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class CategoryAdapterNewOrder extends ArrayAdapter<Category> {
    private GridView toPopulate;
    private SaveManager<Category> saveManager;
    private final String LOG_TAG = "Category Adapter";
    private SaveManager<Order> currentOrder;
    public CategoryAdapterNewOrder(@NonNull Context context, int resource, SaveManager<Category> saveManager, GridView toPopulate, SaveManager<Order> currentOrder) {
        super(context, resource, saveManager.getSaveList());
        this.saveManager = saveManager;
        this.toPopulate = toPopulate;
        this.currentOrder = currentOrder;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewGroup mParent = parent;
        View listItemView = convertView;
        Category currentCategory = getItem(position);

        listItemView = LayoutInflater.from(getContext()).inflate(
                R.layout.square_category, parent, false);
        ((TextView) listItemView.findViewById(R.id.itemText)).setText(currentCategory.getmTitle());
        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveManager<Item> itemSaveManager = new SaveManager<>(getContext(), ((TextView) v.findViewById(R.id.itemText)).getText().toString(), Item.class);
                ItemAdapterNewOrder itemAdapter = new ItemAdapterNewOrder(getContext(), R.layout.square_item, itemSaveManager, toPopulate, currentOrder);
                toPopulate.setAdapter(itemAdapter);

                CategoryAdapterNewOrder categoryAdapter = (CategoryAdapterNewOrder) ((ListView) ((Activity) getContext()).findViewById(R.id.categoryList)).getAdapter();
                categoryAdapter.clear();
                categoryAdapter.addAll(saveManager.getSaveList());
                categoryAdapter.notifyDataSetChanged();
            }
        });

        listItemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog deleteCatDialog = new MaterialAlertDialogBuilder(getContext(), R.style.MyRounded_MaterialComponents_MaterialAlertDialog)
                        .setMessage("Do you really want to delete " + currentCategory.getmTitle() + "?")
                        .setCancelable(true)
                        .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                for (Category category : saveManager.getSaveList()) {
                                    Log.e(LOG_TAG, category.getmTitle() + " " + category.mId);
                                }
                                //Log.e(LOG_TAG, currentCategory.getmTitle() + " " + currentCategory.mId);
                                new SaveManager<Item>(getContext(), currentCategory.getmTitle(), Item.class).deleteSave();
                                saveManager.deleteSaveable(currentCategory.mId);
                                CategoryAdapterNewOrder categoryAdapter = (CategoryAdapterNewOrder) ((ListView) ((Activity) getContext()).findViewById(R.id.categoryList)).getAdapter();
                                categoryAdapter.clear();
                                categoryAdapter.addAll(saveManager.getSaveList());
                                categoryAdapter.notifyDataSetChanged();
                            }
                        }).create();
                deleteCatDialog.show();


                return true;
            }
        });


        return listItemView;
    }
}
