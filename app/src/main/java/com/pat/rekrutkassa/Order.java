package com.pat.rekrutkassa;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Order extends Saveable {
    ArrayList<OrderElement> orderElements = new ArrayList<>();
    private final String LOG_TAG = "ORDER";


    public Order(ArrayList<Item> items,Integer id){
        this.orderElements = formOrderElements(items);
        this.mId = id;
    }

    public Order(){
        this.orderElements = new ArrayList<>();
    }

    public void addItem(Item item){
        Log.e(LOG_TAG,"OUTERMOST");
        for(OrderElement orderElement: orderElements){
            Log.e(LOG_TAG,"ITERATING");
            Log.e(LOG_TAG,String.valueOf(orderElement.getItem().equals(item)));
            if(orderElement.getItem().equals(item)){
                orderElement.setCount(orderElement.getCount()+1);
                Log.e(LOG_TAG,"SAME");
                return;
            }
        }
        orderElements.add(new OrderElement(1,item));
    }

    public ArrayList<OrderElement> getOrderElementsList(){
        return orderElements;
    }

    private ArrayList<OrderElement> formOrderElements(ArrayList<Item> items){
        ArrayList<OrderElement> orderElements = new ArrayList<>();
        ArrayList<Item> uniqueItems = new ArrayList<>();

        for(Item item:items){

            if(!uniqueItems.contains(item)){
                uniqueItems.add(item);
                int count = 0;
                for(Item itemToCheck: items){
                    if(itemToCheck.equals(item)){
                        count++;
                    }
                }
                OrderElement orderElement = new OrderElement(count,item);
                orderElements.add(orderElement);
            }
        }

        return orderElements;
    }


    @Override
    void createSaveable(JSONObject json) {
        try {
            JSONArray orderedItems = json.getJSONArray("orders");
            for(int i = 0; i < orderedItems.length();i++){


                OrderElement orderElement = new OrderElement(orderedItems.getJSONObject(i));
                this.orderElements.add(orderElement);
            }

            mId = json.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        } {

        }
    }

    @Override
    JSONObject serialize() {
        JSONObject serializedOrder = new JSONObject();
        JSONArray orders = new JSONArray();
        try {
            if(!(this.orderElements == null)){
                for(OrderElement orderElement: this.orderElements){
                    orders.put(orderElement.serialize());
                }
            }
            serializedOrder.put("orders",orders);
            serializedOrder.put("id",mId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e(LOG_TAG + "yes",serializedOrder.toString());
        return serializedOrder;
    }

    @Override
    public void setmId(Integer mId) {
        super.setmId(mId);
    }




    public class OrderElement extends Saveable{
        int count = 0;
        Item item;
        public OrderElement(int count, Item item){
            this.item = item;
            this.count = count;
        }

        public OrderElement(JSONObject json){
            createSaveable(json);
        }

        @Override
        void createSaveable(JSONObject json) {
            try {
                this.count = json.getInt("count");
                this.item = new Item(json.getJSONObject("item"));
                this.mId = json.getInt("id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        JSONObject serialize() {
            JSONObject serializedOrderElement = new JSONObject();
            try {
                serializedOrderElement.put("count",this.count);
                serializedOrderElement.put("item",this.item.serialize());
                serializedOrderElement.put("id",this.mId);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return serializedOrderElement;
        }

        public Item getItem() {
            return item;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        @Override
        public void setmId(Integer mId) {
            super.setmId(mId);
        }
    }
}
