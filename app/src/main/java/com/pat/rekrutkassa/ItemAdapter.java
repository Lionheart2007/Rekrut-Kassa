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

        Item currentItem = getItem(position);

        if(currentItem == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.add_square, parent, false);
            listItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final View inputItem = LayoutInflater.from(getContext()).inflate(R.layout.add_item,null);

                    AlertDialog newCatDialog = new MaterialAlertDialogBuilder(getContext(), R.style.MyRounded_MaterialComponents_MaterialAlertDialog)
                            .setTitle("Add a new item!")
                            .setView(inputItem)
                            .setNeutralButton("Submit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Log.e(LOG_TAG, ((TextView) inputItem.findViewById(R.id.item_title)).getText().toString());
                                    //Log.e(LOG_TAG, ((TextView) inputItem.findViewById(R.id.item_quantity)).getText().toString());

                                    Item toSave = new Item(((TextView) inputItem.findViewById(R.id.item_title)).getText().toString(),
                                            null,
                                            Float.valueOf(((TextView) inputItem.findViewById(R.id.item_quantity)).getText().toString()),
                                            Float.valueOf(((TextView) inputItem.findViewById(R.id.item_price)).getText().toString()),
                                            Float.valueOf(((TextView) inputItem.findViewById(R.id.portion_size)).getText().toString()),
                                            ((CheckBox) inputItem.findViewById(R.id.equal_sized_portions)).isChecked(),
                                            ((CheckBox) inputItem.findViewById(R.id.needs_measuring)).isChecked(),
                                            ((TextView) inputItem.findViewById(R.id.unit)).getText().toString());

                                    toSave.setmCategory(saveManager.getmSrc());

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
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.square_item, parent, false);
            ((TextView)listItemView.findViewById(R.id.itemText)).setText(currentItem.getmTitle());
            ((TextView)listItemView.findViewById(R.id.itemQuantity)).setText(String.valueOf(currentItem.getmQuantity()));
            ((TextView)listItemView.findViewById(R.id.itemUnit)).setText(currentItem.getmUnit());

            listItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View editView = LayoutInflater.from(getContext()).inflate(R.layout.edit_item,null);
                    ((TextView) editView.findViewById(R.id.item_title)).setText(currentItem.getmTitle());
                    ((TextView) editView.findViewById(R.id.item_quantity)).setText(String.valueOf(currentItem.getmQuantity()));
                    ((TextView) editView.findViewById(R.id.item_price)).setText(String.valueOf(currentItem.getmPrice()));
                    ((TextView) editView.findViewById(R.id.item_unit)).setText(String.valueOf(currentItem.getmUnit()));


                    AlertDialog editItemDialog= new MaterialAlertDialogBuilder(getContext(), R.style.MyRounded_MaterialComponents_MaterialAlertDialog)
                            .setTitle("Edit your item!")
                            .setView(editView)
                            .setNeutralButton("Submit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Item newItem = new Item(
                                            ((TextView)editView.findViewById(R.id.item_title)).getText().toString(),
                                            currentItem.mId,
                                            Float.valueOf(((TextView)editView.findViewById(R.id.item_quantity)).getText().toString()),
                                            Float.valueOf(((TextView)editView.findViewById(R.id.item_price)).getText().toString()),
                                            currentItem.getmPortionSize(),
                                            currentItem.ismHasEqualSizedPortions(),
                                            currentItem.ismNeedsMeasuring(),
                                            currentItem.getmUnit()
                                    );

                                    Log.e(LOG_TAG,newItem.mId+"");
                                    saveManager.editSaveable(newItem.mId,newItem);

                                    ItemAdapter itemAdapter = (ItemAdapter) toPopulate.getAdapter();
                                    itemAdapter.clear();
                                    itemAdapter.addAll(saveManager.getSaveList());
                                    itemAdapter.notifyDataSetChanged();
                                }
                            })
                            .create();
                    editItemDialog.show();
                }
            });

            listItemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog deleteItemDialog = new MaterialAlertDialogBuilder(getContext(), R.style.MyRounded_MaterialComponents_MaterialAlertDialog)
                            .setMessage("Do you really want to delete "+currentItem.getmTitle() + "?")
                            .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    saveManager.deleteSaveable(currentItem.mId);
                                }
                            })
                            .create();
                    deleteItemDialog.show();


                    return true;
                }
            });
        }

        return listItemView;
    }
}
