package com.pat.rekrutkassa;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;

public class Item extends Saveable {
    private String mTitle;
    private boolean mUsesFloats;
    private Integer mQuantity;
    private Integer mDecimalPlaces;
    private final String LOG_TAG = "Item.class";



    public Item(String title, Integer id, int quantity) {
        this.mTypeId = 1;
        this.mTitle = title;
        this.mId = id;
        this.mQuantity = quantity;
        this.mUsesFloats = false;
    }

    public Item(String title, int id, float quantity) {
        this.mTypeId = 1;
        this.mTitle = title;
        this.mId = id;

        quantity = (float) (Math.round(quantity*100.0)/100.0);

        this.mQuantity = Integer.valueOf(String.valueOf(quantity).split("\\.")[0]);
        this.mDecimalPlaces = Integer.valueOf(String.valueOf(quantity).split("\\.")[1]);

        mUsesFloats = true;
    }

    public Item() {

    }


    @NonNull
    @Override
    public String toString() {
        return mId + " " + mTitle + " " + mQuantity;
    }

    @Override
    void createSaveable(JSONObject json) {
        this.mTypeId = 1;
        try {

            this.mTitle = json.getString("title");
            this.mUsesFloats = json.getBoolean("usesFloats");
            this.mQuantity = json.getInt("quantity");
            if(mUsesFloats){
                this.mDecimalPlaces = json.getInt("decimalPlaces");
            }

            //Always put ID last here: In the probable case that it doesn't have one - like right after saving -
            //it won't instantiate empty Item objects.
            this.mId = json.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    JSONObject serialize() {
        JSONObject serializedItem = new JSONObject();

        try {
            serializedItem.put("id",this.mId);
            serializedItem.put("title",this.mTitle);
            serializedItem.put("usesFloats",this.mUsesFloats);
            serializedItem.put("quantity",this.mQuantity);
            if(mUsesFloats){
                serializedItem.put("decimalPlaces",this.mDecimalPlaces);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return serializedItem;
    }

    @Override
    public void setmId(Integer mId) {
        super.setmId(mId);
    }

    public String getmTitle() {
        return mTitle;
    }

    public Integer getmQuantity() {
        return mQuantity;
    }
}
