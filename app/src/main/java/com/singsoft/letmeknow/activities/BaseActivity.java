package com.singsoft.letmeknow.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GetDetailsHandler;
import com.singsoft.letmeknow.R;
import com.singsoft.letmeknow.utils.CognitoHelper;

/**
 * Created by meidan.zemer on 1/13/2017.
 */

public class BaseActivity extends AppCompatActivity {
    private ProgressDialog progress;

    protected void showProgressDialog(){
        progress.setTitle("Loading");
        progress.setMessage("Loading");
        progress.show();
    }

    protected void hideProgressDialog(){
        progress.dismiss();
    }

    protected void onCreateDone(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progress = new ProgressDialog(this);
        showProgressDialog();
        verifyUserAuthentication();
    }

    @Override
    public void onStart(){
        super.onStart();
        verifyUserAuthentication();
    }

    @Override
    public void onResume(){
        super.onResume();
        verifyUserAuthentication();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout){
            //TODO sign out not working
            CognitoHelper.getUserPool(getApplicationContext())
                    .getCurrentUser()
                    .globalSignOutInBackground(genericHandler);
        }
        return true;
    }

    protected void verifyUserAuthentication(){
        CognitoHelper.getUserPool(getApplicationContext()).getCurrentUser().getDetailsInBackground(getDetailsHandler);
    }

    protected  void moveToLogin(){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    GetDetailsHandler getDetailsHandler = new GetDetailsHandler() {
        @Override
        public void onSuccess(CognitoUserDetails cognitoUserDetails) {
            if(CognitoHelper.getCurrentSession() == null)
                CognitoHelper.getUserPool(getApplicationContext()).getCurrentUser().getSession(authenticationHandler);
            else {
                hideProgressDialog();
                onCreateDone();
            }
        }
        @Override
        public void onFailure(Exception exception) {
            moveToLogin();
        }
    };

    AuthenticationHandler authenticationHandler = new AuthenticationHandler() {
        @Override
        public void onSuccess(CognitoUserSession cognitoUserSession, CognitoDevice device) {
            CognitoHelper.setCurrentSession(cognitoUserSession);
            hideProgressDialog();
            onCreateDone();
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

    GenericHandler genericHandler = new GenericHandler() {
        @Override
        public void onSuccess() {
            moveToLogin();
        }

        @Override
        public void onFailure(Exception exception) {
            moveToLogin();
        }
    };
}
