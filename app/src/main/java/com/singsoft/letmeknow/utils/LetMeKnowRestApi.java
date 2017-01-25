package com.singsoft.letmeknow.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.amazonaws.http.HttpMethodName;
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;
import com.amazonaws.mobileconnectors.apigateway.ApiRequest;
import com.amazonaws.mobileconnectors.apigateway.ApiResponse;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.amazonaws.regions.Regions;
import com.amazonaws.util.IOUtils;

/**
 * Created by meidan.zemer on 1/21/2017.
 */

public class LetMeKnowRestApi extends AsyncTask<Context, Void, String> {
    AuthenticationHandler authenticationHandler = new AuthenticationHandler() {
        @Override
        public void onSuccess(CognitoUserSession cognitoUserSession, CognitoDevice device) {
            CognitoHelper.setCurrentSession(cognitoUserSession);
            callApiGateWay();

        }
        @Override
        public void authenticationChallenge(ChallengeContinuation continuation) {}
        @Override
        public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String username) { }
        @Override
        public void getMFACode(MultiFactorAuthenticationContinuation multiFactorAuthenticationContinuation) {}
        @Override
        public void onFailure(Exception e) {}
    };
    protected  Context context;
    protected  String res;
    protected void callApiGateWay(){
        String rc;
        try {
           /* CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                    context,          // activity context
                    "eu-west-1:9338fe1e-fd4f-4f86-be21-25af608e7668", // Identity Pool ID
                    Regions.EU_WEST_1// region of Cognito identity pool
            );*/
          /*  Map<String, String> logins = new HashMap<String, String>();
            logins.put("cognito-idp.eu-west-1.amazonaws.com/eu-west-1_5RhkEuTBj",CognitoHelper.getCurrentSession().getIdToken().getJWTToken());
            credentialsProvider.setLogins(logins);*/
            ApiClientFactory factory = new ApiClientFactory()
                    .apiKey("BkmjsMabWHauqcyW3FFAf2fcIEQ2LcPT2qjqvzKW");
                    //.credentialsProvider(credentialsProvider);
            factory.endpoint("https://8rd0wztxo5.execute-api.eu-west-1.amazonaws.com/Dev/LetMeKnowTestUser");
            factory.region(Regions.EU_WEST_1.getName());
            final LetMeKnowLambdaMicroserviceTestClient letMeKnowLambdaMicroserviceTestClient = factory.build(LetMeKnowLambdaMicroserviceTestClient.class);
            ApiRequest apiRequest = new ApiRequest();
            apiRequest.withHttpMethod(HttpMethodName.GET);
            apiRequest.addHeader("Content-Type","application/json");
            apiRequest.addHeader("Authorization ",CognitoHelper.getCurrentSession().getIdToken().getJWTToken());
            ApiResponse res = letMeKnowLambdaMicroserviceTestClient.execute(apiRequest);
            rc = IOUtils.toString(res.getContent());
        } catch (Exception e) {
            rc = e.getMessage();
        }
        this.res = rc;
    }
    protected String doInBackground(Context ... context) {
        this.context = context[0];
        if(CognitoHelper.getCurrentSession() == null)
            CognitoHelper.getUserPool(context[0]).getCurrentUser().getSession(authenticationHandler);
        else
            callApiGateWay();
        return this.res;
    }

    protected void onPostExecute (String result){

    }
}
