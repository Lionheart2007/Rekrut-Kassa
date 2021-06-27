package com.pat.rekrutkassa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

        return listItemView;
    }
}
