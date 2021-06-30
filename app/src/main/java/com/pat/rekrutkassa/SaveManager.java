package com.pat.rekrutkassa;

import android.content.Context;

import org.json.JSONArray;

import java.io.File;
import android.util.Log;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import androidx.fragment.app.FragmentManager;

public class SaveManager<T extends Saveable>{
    Context mContext;
    String mSrc;
    File mFile;
    private String LOG_TAG = "SAVEMANAGER";

    public SaveManager(Context context, String src){
        this.mContext = context;
        this.mSrc = src;
        this.mFile = new File(mContext.getFilesDir(),mSrc+".json");
    }

    public void editSaveable(){

    }

    public void deleteSaveable(){

    }

    private JSONArray reassignID(){

        // TODO: ADD RETURN STATEMENT
        return null;
    }

    public void save(){

    }

    public T getSaveablyById(){

        // TODO: ADD RETURN STATEMENT
        return null;
    }

    private int getLastId(){

        // TODO: ADD RETURN STATEMENT
        return 0;
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
        return contents;
    }

    public ArrayList<T> getSave(){

        // TODO: ADD RETURN STATEMENT
        return null;
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
}
