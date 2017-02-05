package com.singsoft.letmeknow.rest;

import android.os.AsyncTask;

import com.amazonaws.http.HttpMethodName;
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;
import com.amazonaws.mobileconnectors.apigateway.ApiRequest;
import com.amazonaws.mobileconnectors.apigateway.ApiResponse;
import com.amazonaws.regions.Regions;
import com.amazonaws.util.IOUtils;
import com.singsoft.letmeknow.utils.CognitoHelper;

import org.json.JSONObject;


/**
 * Created by meidan.zemer on 1/28/2017.
 */

public abstract class LetMeKnowRestApiBase extends AsyncTask<Object, Void , JSONObject>{
    protected static final String endPointBase = "https://whw6z2dn3h.execute-api.eu-west-1.amazonaws.com/dev/";
    protected static final String apikey = "BkmjsMabWHauqcyW3FFAf2fcIEQ2LcPT2qjqvzKW";
    protected static final String region = Regions.EU_WEST_1.getName();
    protected String resourceName;

    protected String getEndPoint(){
        return this.endPointBase + resourceName;
    }

    protected JSONObject callRestApi(HttpMethodName method, String path, JSONObject body ){
        JSONObject jsonObj = new JSONObject();
        String rc;
        try {
            ApiClientFactory factory = new ApiClientFactory()
                    .apiKey(this.apikey)
                    .endpoint(this.endPointBase+path)
                    .region(this.region);
            final LetMeKnowRest letMeKnowRest = factory.build(LetMeKnowRest.class);
            ApiRequest apiRequest = new ApiRequest();
            apiRequest.withHttpMethod(method);
            apiRequest.addHeader("Content-Type","application/json");
            apiRequest.addHeader("Authorization ", CognitoHelper.getCurrentSession().getIdToken().getJWTToken());
            if(body != null) {
                String bodyStr = body.toString();
                apiRequest.withBody(bodyStr);
                apiRequest.addHeader("Content-Length",Integer.toString(bodyStr.getBytes().length));
            }
            ApiResponse res = letMeKnowRest.execute(apiRequest);
            rc = IOUtils.toString(res.getContent());
        } catch (Exception e) {
            rc = e.getMessage();
        }
        return jsonObj;
    }
}
