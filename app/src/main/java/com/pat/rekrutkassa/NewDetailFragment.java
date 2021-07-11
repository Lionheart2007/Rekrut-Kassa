package com.pat.rekrutkassa;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewDetailFragment newInstance(String param1, String param2) {
        NewDetailFragment fragment = new NewDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_detail,container,false);

        GridView grid = rootView.findViewById(R.id.containerItems);

        ArrayList<Item> items = new ArrayList<>();
        items.add(null);


        ListView categoryList = rootView.findViewById(R.id.categoryList);

        SaveManager<Order> currentOrder = new SaveManager<>(getContext(),"currentOrder",Order.class);
        currentOrder.clearSave();



        CategoryAdapterNewOrder categoryAdapter = new CategoryAdapterNewOrder(getContext(),R.layout.square_category,new SaveManager<Category>(getContext(),"categories",Category.class),grid,currentOrder);
        categoryList.setAdapter(categoryAdapter);


        ExtendedFloatingActionButton fab = (ExtendedFloatingActionButton) rootView.findViewById(R.id.submitButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View submitView = LayoutInflater.from(getContext()).inflate(R.layout.submit_order,null);

                OrderAdapter orderAdapter = new OrderAdapter(getContext(),R.layout.order_item,currentOrder.getSaveablyById(1).getOrderElementsList());
                ((ListView) submitView.findViewById(R.id.order_element_list)).setAdapter(orderAdapter);
                ((TextView) submitView.findViewById(R.id.total)).setText(orderAdapter.getTotal());
                AlertDialog submitDialog = new MaterialAlertDialogBuilder(getContext(), R.style.MyRounded_MaterialComponents_MaterialAlertDialog)
                        .setTitle("Finalize your Order!")
                        .setView(submitView)
                        .setNeutralButton("Finalize", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                boolean enoughOfAll = true;

                                    for(Order.OrderElement orderElement:currentOrder.getSaveablyById(1).getOrderElementsList()){
                                        if(orderElement.getItem().ismHasEqualSizedPortions()){
                                            Item toEdit = orderElement.getItem();

                                            if (!(toEdit.getmPortionSize()*orderElement.getCount()>toEdit.getmQuantity())){
                                                toEdit.setmQuantity(toEdit.getmQuantity()-toEdit.getmPortionSize()*orderElement.getCount());
                                                new SaveManager<Item>(getContext(),toEdit.getmCategory(),Item.class).editSaveable(
                                                        toEdit.mId,toEdit);
                                            }

                                        }
                                    }
                                }
                        }).create();
                submitDialog.show();
            }
        });

        return rootView;
    }
}