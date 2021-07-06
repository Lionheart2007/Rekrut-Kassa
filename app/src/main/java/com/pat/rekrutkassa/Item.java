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
    private Float mQuantity;
    private Float mPrice;
    private boolean mHasEqualSizedPortions;
    private boolean mNeedsMeasuring;
    private String mUnit;
    private final String LOG_TAG = "Item.class";

    public Item(String title, Integer id, float quantity, float price, boolean hasEqualSizedPortions, boolean needsMeasuring, String unit) {
        this.mTypeId = 1;
        this.mTitle = title;
        this.mId = id;
        this.mQuantity = (float) (Math.round(quantity*100.0)/100.0);
        this.mUnit = unit;
        this.mPrice = (float) (Math.round(price*100.0)/100.0);;
        this.mHasEqualSizedPortions = hasEqualSizedPortions;
        this.mNeedsMeasuring = needsMeasuring;
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
            this.mQuantity = Float.parseFloat(json.getString("quantity"));
            this.mPrice = Float.parseFloat(json.getString("price"));
            this.mHasEqualSizedPortions = json.getBoolean("hasEqualSizedPortions");
            this.mNeedsMeasuring = json.getBoolean("needsMeasuring");
            this.mUnit = json.getString("unit");
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
            serializedItem.put("quantity",String.valueOf(this.mQuantity));
            serializedItem.put("price",String.valueOf(this.mPrice));
            serializedItem.put("hasEqualSizedPortions",this.mHasEqualSizedPortions);
            serializedItem.put("needsMeasuring",this.mNeedsMeasuring);
            serializedItem.put("unit",this.mUnit);
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

    public Float getmQuantity() {
        return mQuantity;
    }

    public String getmUnit() {
        return mUnit;
    }

    public Float getmPrice() {
        return mPrice;
    }
}
