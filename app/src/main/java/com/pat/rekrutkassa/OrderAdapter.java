package com.pat.rekrutkassa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class OrderAdapter extends ArrayAdapter<Order.OrderElement> {
    int resource = -1;
    public OrderAdapter(@NonNull Context context, int resource, @NonNull List<Order.OrderElement> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(this.resource,parent,false);
        }

        Order.OrderElement currentOrderElement = getItem(position);
        ((TextView) listItemView.findViewById(R.id.item)).setText(currentOrderElement.getItem().getmTitle());
        ((TextView) listItemView.findViewById(R.id.times)).setText("x "+currentOrderElement.getCount());
        ((TextView) listItemView.findViewById(R.id.cost)).setText(currentOrderElement.getItem().getmPrice() + " €");

        return listItemView;
    }

    public String getTotal(){
        float total = 0;

        for(int i = 0; i < getCount(); i++){
            total += getItem(i).getCount() * getItem(i).getItem().getmPrice();
        }
        total = Math.round((total*100))/100f;


        return String.valueOf(total) + " €";
    }
}
