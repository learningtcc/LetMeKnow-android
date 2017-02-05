package com.singsoft.letmeknow.rest;

import android.content.Context;
import android.os.AsyncTask;

import com.amazonaws.http.HttpMethodName;
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;
import com.amazonaws.mobileconnectors.apigateway.ApiRequest;
import com.amazonaws.mobileconnectors.apigateway.ApiResponse;
import com.amazonaws.regions.Regions;
import com.amazonaws.util.IOUtils;
import com.singsoft.letmeknow.utils.CognitoHelper;

/**
 * Created by meidan.zemer on 1/21/2017.
 */

public class LetMeKnowRestApi extends AsyncTask<Context, Void, String> {
    protected  String res;
    protected void callApiGateWay(){
        String endpoint= "https://whw6z2dn3h.execute-api.eu-west-1.amazonaws.com/dev/contactpoints";
        String rc;
        try {
            ApiClientFactory factory = new ApiClientFactory()
                    .apiKey("BkmjsMabWHauqcyW3FFAf2fcIEQ2LcPT2qjqvzKW");
            factory.endpoint(endpoint);
            factory.region(Regions.EU_WEST_1.getName());
            final LetMeKnowRest letMeKnowRest = factory.build(LetMeKnowRest.class);
            ApiRequest apiRequest = new ApiRequest();
            apiRequest.withHttpMethod(HttpMethodName.GET);
            apiRequest.addHeader("Content-Type","application/json");
            apiRequest.addHeader("Authorization ",CognitoHelper.getCurrentSession().getIdToken().getJWTToken());
            ApiResponse res = letMeKnowRest.execute(apiRequest);
            rc = IOUtils.toString(res.getContent());
        } catch (Exception e) {
            rc = e.getMessage();
        }
        this.res = rc;
    }
    protected String doInBackground(Context ... context) {
        callApiGateWay();
        return this.res;
    }
    protected void onPostExecute (String result){

    }
}
