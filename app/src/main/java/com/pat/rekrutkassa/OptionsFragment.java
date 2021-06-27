package com.pat.rekrutkassa;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.card.MaterialCardView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OptionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OptionsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    interface OnOptionsClickListener{
        void onOptionSelected(String option);
    }

    private OnOptionsClickListener mCallback;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OptionsFragment() {
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OptionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OptionsFragment newInstance(String param1, String param2) {
        OptionsFragment fragment = new OptionsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnOptionsClickListener) context;
        } catch (Exception e) {
            throw new ClassCastException(context.toString());
        }
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
        View rootView = inflater.inflate(R.layout.fragment_options_view,container,false);

        MaterialCardView mNewOption = rootView.findViewById(R.id.newOrder);
        CardView mInventoryOption = rootView.findViewById(R.id.inventory);
        CardView mOptionsOption = rootView.findViewById(R.id.options);

        mNewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(":)", String.valueOf(getResources().getBoolean(R.bool.isTablet)));
                mCallback.onOptionSelected("newOrder");
            }
        });

        mInventoryOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onOptionSelected("inventory");
            }
        });

        mOptionsOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onOptionSelected("options");
            }
        });

        return rootView;
    }
}