package com.pat.rekrutkassa;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SaveManager<T extends Saveable>{
    Context mContext;
    String mSrc;
    File mFile;
    final Class<T> typeParameterClass;

    private String LOG_TAG = "SAVEMANAGER";

    public SaveManager(Context context, String src, Class<T> typeParameterClass){
        this.mContext = context;
        this.mSrc = src;
        this.typeParameterClass = typeParameterClass;
        this.mFile = new File(mContext.getFilesDir(),mSrc+".json");
    }

    public void editSaveable(int id, T saveable){
        saveable.setmId(id);
        try {
            JSONArray save = new JSONArray(readSave());
            save.put(id-1,saveable.serialize());
            writeSaveFile(save);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void deleteSaveable(int id){
        JSONArray save = null;
        try{
            save = new JSONArray(readSave());
            save.remove(id-1);
            save = reassignID(save);
            writeSaveFile(save);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private JSONArray reassignID(JSONArray save){
        for(int i = 0; i < save.length(); i++){
            JSONObject saveableJson = null;
            try{
                saveableJson = save.getJSONObject(i);

                T saveable = typeParameterClass.newInstance();
                saveable.createSaveable(saveableJson);
                Log.e("REASSIGN ID", saveable.toString());
                saveable.setmId(i+1);
                save.put(i,saveable.serialize());

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        Log.e("REASSIGN ID",save.toString());
        return save;
    }

    public ArrayList<T> getSaveList(){
        ArrayList<T> saveList = new ArrayList<>();
        try {
            JSONArray save = new JSONArray(readSave());
            T saveable;
            for(int i = 0; i < save.length(); i++){
                saveable = null;
                saveable = typeParameterClass.newInstance();
                saveable.createSaveable(save.getJSONObject(i));
                saveList.add(saveable);

                if(saveable.mTypeId == 1){
                    Log.e(LOG_TAG,save.getJSONObject(i).toString());
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return saveList;
    }
    public void save(T saveable) {
        JSONArray save;

        try{
            Log.e(LOG_TAG,((Item) saveable).getmTitle());
        }catch (ClassCastException e){

        }

        try {
            if (mFile.length() == 0) {
                save = new JSONArray();
            } else {
                save = new JSONArray(readSave());
            }
            save.put(saveable.serialize());
            Log.e("save", save.toString());
            save = reassignID(save);

            writeSaveFile(save);


        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(LOG_TAG,"Couldn't save saveable");

        }
    }

    public int getCount(){
        int count = -1;
        try {
            JSONArray save = new JSONArray(readSave());
            count = save.length();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  count;
    }

    public T getSaveablyById(int id){
        JSONArray save = null;
        T saveable = null;
        try {
            save = new JSONArray(readSave());
            saveable = typeParameterClass.newInstance();
            saveable.createSaveable(save.getJSONObject(id-1));

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return saveable;
    }

    private int getLastId(){
        int id = 0;
        JSONArray save;
        try {
            save = new JSONArray(readSave());
            id = save.length()-1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return id;
    }

    public String readSave(){
        int length = (int) mFile.length();
        byte[] bytes = new byte[length];
        try {
            FileInputStream fis = new FileInputStream(mFile);
            fis.read(bytes);
            fis.close();
            Log.e(LOG_TAG,"Reading save successful "+mSrc);

        }
        catch (IOException e){
            Log.e(LOG_TAG,"Reading save failed "+mSrc);
        }
        String contents = new String(bytes);
        Log.e(LOG_TAG,contents);
        return contents;
    }

    private JSONArray getSave(){
        JSONArray save = null;
        try{
            if(!(mFile.length() == 0)){
                save = new JSONArray(readSave());
            }
        }catch (JSONException e){
            Log.e(LOG_TAG,"Failed getting save");
        }
        return getSave();
    }

    public void clearSave(){
        deleteSave();
        clearSave();
        Log.e(LOG_TAG,"Cleared "+mSrc);
    }

    public void deleteSave(){
        if (this.mFile.exists()){
            mFile.delete();
            Log.e(LOG_TAG, "Deleted save file "+mSrc);
        }
    }

    public void createSave(){
        try {
            mFile.createNewFile();
            Log.e(LOG_TAG,"Created save file "+mSrc );
        }
        catch (IOException e){
            Log.e(LOG_TAG,"File creation failed "+mSrc);
        }

    }

    private T getInstanceOfT(Class<T> aClass) throws InstantiationException, IllegalAccessException {
        return aClass.newInstance();
    }

    private void writeSaveFile(JSONArray save){
        try{
            FileOutputStream fos = new FileOutputStream(mFile, false);
            fos.write((save.toString()).getBytes());
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
