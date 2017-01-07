package com.singsoft.letmeknow.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.singsoft.letmeknow.R;
import com.singsoft.letmeknow.utils.CognitoHelper;

public class LoginActivity extends AppCompatActivity {
    private CognitoUserPool userPool = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userPool = CognitoHelper.getUserPool(getApplicationContext());
        setMessage("");
    }
    protected void setMessage(String text){
        TextView textView = (TextView)findViewById(R.id.textViewMessage);
        textView.setText(text);
    }

    protected void clearPassword(){
        EditText passwordTextEditor = (EditText) findViewById(R.id.editTextPassword);
        passwordTextEditor.setText(null);
    }
    protected String getEmail(){
        EditText emailTextEditor = (EditText) findViewById(R.id.editTextEmail);
        return emailTextEditor.getText().toString();
    }
    protected String getPassword(){
        EditText passwordTextEditor = (EditText) findViewById(R.id.editTextPassword);
        return passwordTextEditor.getText().toString();
    }
    public void onLoginClick(View view){
        userPool.getUser(this.getEmail()).globalSignOutInBackground(genericHandler);
        setMessage("");
    }

    public void onRegisterClick(View view){
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }

    GenericHandler genericHandler = new GenericHandler() {
        @Override
        public void onSuccess() {
            userPool.getUser(getEmail()).getSessionInBackground(authenticationHandler);

        }

        @Override
        public void onFailure(Exception exception) {
            userPool.getUser(getEmail()).getSessionInBackground(authenticationHandler);

        }
    };

    AuthenticationHandler authenticationHandler = new AuthenticationHandler() {
        @Override
        public void onSuccess(CognitoUserSession cognitoUserSession, CognitoDevice device) {
          Intent intent = new Intent(getApplicationContext(), MainActivity.class);
          startActivity(intent);
        }

        @Override
        public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String username) {
            AuthenticationDetails authenticationDetails = new AuthenticationDetails(username, getPassword(), null);
            authenticationContinuation.setAuthenticationDetails(authenticationDetails);
            authenticationContinuation.continueTask();
        }

        @Override
        public void getMFACode(MultiFactorAuthenticationContinuation multiFactorAuthenticationContinuation) {

        }

        @Override
        public void onFailure(Exception e) {
            clearPassword();
            setMessage(getResources().getString(R.string.LoginFail));
        }

        @Override
        public void authenticationChallenge(ChallengeContinuation continuation) {
            /**
             * For Custom authentication challenge, implement your logic to present challenge to the
             * user and pass the user's responses to the continuation.
             */
            if ("NEW_PASSWORD_REQUIRED".equals(continuation.getChallengeName())) {
            }
        }
    };

}
