package com.pat.rekrutkassa;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

public class CategoryAdapter extends ArrayAdapter<Category> {
    public CategoryAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public CategoryAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public CategoryAdapter(@NonNull Context context, int resource, @NonNull Category[] objects) {
        super(context, resource, objects);
    }

    public CategoryAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull Category[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public CategoryAdapter(@NonNull Context context, int resource, @NonNull List<Category> objects) {
        super(context, resource, objects);
    }

    public CategoryAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Category> objects) {
        super(context, resource, textViewResourceId, objects);
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
                                    SaveManager<Category> categorySaveManager = new SaveManager<>(getContext(),"categories",Category.class);
                                    categorySaveManager.save(new Category(inputCat.getText().toString(),null));
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
                    // TODO: ACTUALLY WRITE ONCLICK FOR NON-ADDING BUTTONS
                    // TODO: ADD DELETE ON LONG CLICK FUNCTION
                }
            });
        }


        return listItemView;
    }
}
