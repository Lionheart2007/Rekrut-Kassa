package com.pat.rekrutkassa;

import org.json.JSONObject;

abstract class Saveable {
    public Integer mId = null;
    public Integer mTypeId = null;

    abstract void createSaveable(JSONObject json);
    abstract JSONObject serialize();
}
