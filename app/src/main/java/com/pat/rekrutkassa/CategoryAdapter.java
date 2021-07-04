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

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class CategoryAdapter extends ArrayAdapter<Category> {
    private GridView toPopulate;
    private SaveManager<Category> saveManager;
    private final String LOG_TAG = "Category Adapter";

    public CategoryAdapter(@NonNull Context context, int resource,  SaveManager<Category> saveManager,GridView toPopulate) {
        super(context, resource, saveManager.getSaveList());
        this.saveManager = saveManager;
        this.toPopulate = toPopulate;
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
    public Category getItem(int position) {
        if(position<getCount()-1){
            return super.getItem(position);
        }else {
            return null;
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewGroup mParent = parent;
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                   R.layout.square_category, parent, false);

        }
        Category currentCategory = getItem(position);
        if (currentCategory == null){
            ((TextView) listItemView.findViewById(R.id.itemText)).setText("+");
            listItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final EditText inputCat = new EditText(getContext());
                    AlertDialog newCatDialog = new MaterialAlertDialogBuilder(getContext(), R.style.MyRounded_MaterialComponents_MaterialAlertDialog)
                            .setMessage("Name the category you want to add!")
                            .setView(inputCat)
                            .setNeutralButton("Submit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    saveManager.save(new Category(inputCat.getText().toString(),null));
                                    CategoryAdapter categoryAdapter = (CategoryAdapter) ((ListView)((Activity) getContext()).findViewById(R.id.categoryList)).getAdapter();
                                    categoryAdapter.clear();
                                    categoryAdapter.addAll(saveManager.getSaveList());
                                    categoryAdapter.notifyDataSetChanged();
                                }
                            }).create();
                    newCatDialog.show();

                }
            });
        }
        else {
            ((TextView) listItemView.findViewById(R.id.itemText)).setText(currentCategory.getmTitle());
            listItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SaveManager<Item> itemSaveManager = new SaveManager<>(getContext(),((TextView) v.findViewById(R.id.itemText)).getText().toString(),Item.class);
                    ItemAdapter itemAdapter = new ItemAdapter(getContext(),R.layout.square_item,itemSaveManager,toPopulate);
                    toPopulate.setAdapter(itemAdapter);

                    CategoryAdapter categoryAdapter = (CategoryAdapter) ((ListView)((Activity) getContext()).findViewById(R.id.categoryList)).getAdapter();
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

                                    for (Category category: saveManager.getSaveList()){
                                        Log.e(LOG_TAG, category.getmTitle() + " " + category.mId);
                                    }
                                    //Log.e(LOG_TAG, currentCategory.getmTitle() + " " + currentCategory.mId);
                                    new SaveManager<Item>(getContext(),currentCategory.getmTitle(),Item.class).deleteSave();
                                    saveManager.deleteSaveable(currentCategory.mId);
                                    CategoryAdapter categoryAdapter = (CategoryAdapter) ((ListView)((Activity) getContext()).findViewById(R.id.categoryList)).getAdapter();
                                    categoryAdapter.clear();
                                    categoryAdapter.addAll(saveManager.getSaveList());
                                    categoryAdapter.notifyDataSetChanged();
                                }
                            }).create();
                    deleteCatDialog.show();


                    return true;
                }
            });
        }


        return listItemView;
    }
}
