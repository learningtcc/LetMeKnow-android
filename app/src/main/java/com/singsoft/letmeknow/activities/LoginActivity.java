package com.singsoft.letmeknow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
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
        TextView textView = (TextView)findViewById(R.id.textViewMessageLogin);
        textView.setText(text);
    }

    protected void clearPassword(){
        EditText passwordTextEditor = (EditText) findViewById(R.id.editTextPassword2);
        passwordTextEditor.setText(null);
    }
    protected String getEmail(){
        EditText emailTextEditor = (EditText) findViewById(R.id.editTextEmail);
        return emailTextEditor.getText().toString();
    }
    protected String getPassword(){
        EditText passwordTextEditor = (EditText) findViewById(R.id.editTextPassword2);
        return passwordTextEditor.getText().toString();
    }
    public void onLoginClick(View view){
        setMessage("");
        userPool.getUser(getEmail()).getSessionInBackground(authenticationHandler);
    }

    public void onRegisterClick(View view){
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }

    AuthenticationHandler authenticationHandler = new AuthenticationHandler() {
        @Override
        public void onSuccess(CognitoUserSession cognitoUserSession, CognitoDevice device) {
            CognitoHelper.setCurrentSession(cognitoUserSession);
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
