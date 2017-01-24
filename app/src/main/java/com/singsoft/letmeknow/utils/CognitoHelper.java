package com.singsoft.letmeknow.utils;

import android.content.Context;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.regions.Regions;

/**
 * Created by meidan.zemer on 1/5/2017.
 */

public class CognitoHelper {
    private final static String userPoolId = "eu-west-1_5RhkEuTBj";
    private final static String clientId= "6kqfapj3l6ut1i303otcs30md4";
    private static  CognitoUserSession currentSession = null;

    public static void setCurrentSession(CognitoUserSession session){CognitoHelper.currentSession = session;}
    public static CognitoUserSession getCurrentSession(){return CognitoHelper.currentSession;}
    public static CognitoUserPool getUserPool(Context ctx){

        CognitoUserPool userPool = new CognitoUserPool(ctx, CognitoHelper.userPoolId, CognitoHelper.clientId, null, Regions.EU_WEST_1);
        return userPool;
    }
    public static String getUserPoolId(){
        return userPoolId;
    }
}
