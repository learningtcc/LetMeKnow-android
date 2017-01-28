package com.singsoft.letmeknow.rest;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

/**
 * Created by meidan.zemer on 1/28/2017.
 */

public  class LetMeKnowRestApiBase extends AsyncTask<JSONObject, Void, JSONObject> {
    protected Context context;
    LetMeKnowRestApiBase(Context context){
        this.context = context;
    }

    @Override
    protected JSONObject doInBackground(JSONObject... params) {
        return null;
    }
}
