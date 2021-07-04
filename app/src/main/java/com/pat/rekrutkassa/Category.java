package com.pat.rekrutkassa;

import org.json.JSONException;
import org.json.JSONObject;

public class Category extends Saveable {
    private String mTitle;

    public Category(String title, Integer id){
        this.mTypeId = 0;
        this.mTitle = title;
        this.mId = id;
    }

    @Override
    public String toString() {
        return "Category{" +
                "mTitle='" + mTitle + '\'' +
                '}';
    }

    public Category(){

    }

    @Override
    void createSaveable(JSONObject json) {
        this.mTypeId = 0;
        try {
            this.mTitle = json.getString("title");
            this.mId = json.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    @Override
    JSONObject serialize() {
        JSONObject serializedCategory = new JSONObject();
        try {
            serializedCategory.put("title", this.mTitle);
            serializedCategory.put("id",this.mId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return serializedCategory;
    }

    public String getmTitle() { return mTitle; }

    @Override
    public void setmId(Integer mId) {
        super.setmId(mId);
    }
}
