package com.singsoft.letmeknow.entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by meidan.zemer on 1/31/2017.
 */

public class EntityBase {
    public JSONObject toJsonObject() throws JSONException {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return (new JSONObject(json));
    }
}
