package com.singsoft.letmeknow.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.singsoft.letmeknow.R;
import com.singsoft.letmeknow.utils.CognitoHelper;

public class RegisterActivity extends AppCompatActivity {
    protected String getEmail(){
        EditText emailTextEditor = (EditText) findViewById(R.id.editTextEmail);
        return emailTextEditor.getText().toString();
    }
    protected String getPassword(){
        EditText passwordTextEditor = (EditText) findViewById(R.id.editTextPassword);
        return passwordTextEditor.getText().toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
    public void onRegisterClick(View view) {
        CognitoUserAttributes attr =  new CognitoUserAttributes();
        attr.addAttribute("email",this.getEmail());
        CognitoUserPool userPool = CognitoHelper.getUserPool(getApplicationContext());
        userPool.signUpInBackground(this.getEmail(),this.getPassword(),attr, null,signUpHandler);
    }
    SignUpHandler signUpHandler = new SignUpHandler() {
        @Override
        public void onSuccess(CognitoUser user, boolean signUpConfirmationState,
                              CognitoUserCodeDeliveryDetails cognitoUserCodeDeliveryDetails) {
            int a=1;
        }

        @Override
        public void onFailure(Exception exception) {
            int b=1;
        }
    };
}
