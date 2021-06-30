package com.pat.rekrutkassa;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Item extends Saveable {
    private String mTitle;
    private boolean mUsesFloats;
    private Integer mQuantity;
    private Integer mDecimalPlaces;



    public Item(String title, int id, int quantity) {
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

    @Override
    void createSaveable(JSONObject json) {
        this.mTypeId = 1;
        try {
            this.mId = json.getInt("id");
            this.mTitle = json.getString("title");
            this.mUsesFloats = json.getBoolean("usesFloats");
            this.mQuantity = json.getInt("quantity");
            if(mUsesFloats){
                this.mDecimalPlaces = json.getInt("decimalPlaces");
            }
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
}
