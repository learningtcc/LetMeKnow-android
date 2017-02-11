package com.singsoft.letmeknow.rest;

import com.amazonaws.http.HttpMethodName;
import com.singsoft.letmeknow.entities.ContactPoint;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by meidan.zemer on 1/29/2017.
 */

public class LetMeKnowApiContactPoints {

    public ContactPoint addContactPoint(ContactPoint contactPoint) throws ExecutionException, InterruptedException {
        ContactPoint newContactPoint = null;
        AddContactPointRestApi addContactPointRestApi = new AddContactPointRestApi();
        JSONObject jsonObject = addContactPointRestApi.execute(contactPoint).get();
        return newContactPoint;
    }

    class AddContactPointRestApi extends  LetMeKnowRestApiBase {
        @Override
        protected JSONObject doInBackground(Object... params) {
            ContactPoint cp = (ContactPoint) params[0];
            try {
                return this.callRestApi(HttpMethodName.POST, "contactpoints", cp.toJsonObject());
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public ArrayList<ContactPoint> getUserContactPoints() throws ExecutionException, InterruptedException {
        ArrayList<ContactPoint> contactPoints = new ArrayList<>();
        GetUserContactPointsRestApi getUserContactPointsRestApi = new GetUserContactPointsRestApi();
        JSONObject jsonObject = getUserContactPointsRestApi.execute().get();
        return contactPoints;
    }
    class GetUserContactPointsRestApi extends  LetMeKnowRestApiBase{
        @Override
        protected JSONObject doInBackground(Object... params) {
            return this.callRestApi(HttpMethodName.GET,"contactpoints",null);
        }
    }
}
