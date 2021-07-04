package com.pat.rekrutkassa;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.w3c.dom.Text;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

public class ItemAdapter extends ArrayAdapter<Item> {
    private final String LOG_TAG = "Item Adapter";
    private SaveManager<Item> saveManager;
    private GridView toPopulate;

    public ItemAdapter(@NonNull Context context, int resource, SaveManager<Item> saveManager, GridView gridView) {
        super(context, resource, saveManager.getSaveList());
        this.saveManager = saveManager;
        this.toPopulate = gridView;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == getCount()){
            return 1;
        }
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return super.getCount()+1;
    }

    @Nullable
    @Override
    public Item getItem(int position) {
        if(position<getCount()-1){
            return super.getItem(position);
        }else {
            return null;
        }
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.square_item, parent, false);

        }

        Item currentItem = getItem(position);

        if(currentItem == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.square_item, parent,false);
            ((TextView)listItemView.findViewById(R.id.itemText)).setText("+");
            ((TextView)listItemView.findViewById(R.id.itemQuantity)).setVisibility(View.GONE);
            listItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final View inputItem = LayoutInflater.from(getContext()).inflate(R.layout.add_item,null);

                    AlertDialog newCatDialog = new MaterialAlertDialogBuilder(getContext(), R.style.MyRounded_MaterialComponents_MaterialAlertDialog)
                            .setMessage("Add a new item!")
                            .setView(inputItem)
                            .setNeutralButton("Submit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.e(LOG_TAG, ((TextView) inputItem.findViewById(R.id.item_title)).getText().toString());
                                    Log.e(LOG_TAG, ((TextView) inputItem.findViewById(R.id.item_quantity)).getText().toString());

                                    Item toSave = new Item(((TextView) inputItem.findViewById(R.id.item_title)).getText().toString(),
                                            null,
                                            Integer.valueOf(((TextView) inputItem.findViewById(R.id.item_quantity)).getText().toString()));

                                    Log.e(LOG_TAG,toSave.toString());
                                    saveManager.save(toSave);
                                    ItemAdapter itemAdapter = (ItemAdapter) toPopulate.getAdapter();
                                    itemAdapter.clear();
                                    itemAdapter.addAll(saveManager.getSaveList());

                                    itemAdapter.notifyDataSetChanged();

                                }
                            }).create();
                    newCatDialog.show();
                }
            });
        }
        else{
            ((TextView)listItemView.findViewById(R.id.itemQuantity)).setVisibility(View.VISIBLE);
            ((TextView)listItemView.findViewById(R.id.itemText)).setText(currentItem.getmTitle());
            ((TextView)listItemView.findViewById(R.id.itemQuantity)).setText(String.valueOf(currentItem.getmQuantity()));
        }

        return listItemView;
    }
}
