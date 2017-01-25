package com.singsoft.letmeknow.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.singsoft.letmeknow.R;
import com.singsoft.letmeknow.utils.CognitoHelper;
import com.singsoft.letmeknow.utils.StringUtils;

public class RegisterActivity extends AppCompatActivity {

    private EditText passwordTextEditor;
    private EditText passwordTextEditor2;
    private Button registerBtn;
    private CognitoUser registeredUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        passwordTextEditor = (EditText) findViewById(R.id.editTextPasswordRegister);
        passwordTextEditor2 = (EditText) findViewById(R.id.editTextPassword2Register);
        registerBtn = (Button) findViewById(R.id.buttonRegister);
        setMessage("");
        passwordTextEditor2.addTextChangedListener(passwword2Watcher);
        registerBtn.setEnabled(false);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    protected String getEmail() {
        EditText emailTextEditor = (EditText) findViewById(R.id.editTextEmail);
        return emailTextEditor.getText().toString();
    }

    protected String getPassword() {
        return passwordTextEditor.getText().toString();
    }

    protected String getPassword2() {
        return passwordTextEditor2.getText().toString();
    }

    protected void validateInput(){
        String message = "";
        boolean enabledBtn = false;
        if(!StringUtils.isEmail(getEmail())){
            message=getResources().getString(R.string.EmailNotValid);
        } else if(StringUtils.isPasswordValid(getPassword())){
            message = getResources().getString(R.string.PasswordNotValid);
        } else if(!getPassword().equals(getPassword2())){
            message = getResources().getString(R.string.PasswordVerificationFailed);
        }else
            enabledBtn = true;
        setMessage(message);
        registerBtn.setEnabled(enabledBtn);
    }
    protected void setMessage(String text){
        TextView textView = (TextView)findViewById(R.id.textViewMessageRegister);
        textView.setText(text);
    }
    protected  void moveToLogin(){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    protected  void openVerificaionCodeDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.ConfimUserTitle));
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        registeredUser.signOut();

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                String userCode = input.getText().toString();
                registeredUser.signOut();

                registeredUser.confirmSignUpInBackground(userCode,false,verificationCodeHandler);
            }
        });

        builder.show();
    }

    GenericHandler verificationCodeHandler = new GenericHandler() {
        @Override
        public void onSuccess() {
            moveToLogin();
        }

        @Override
        public void onFailure(Exception exception) {
            Button registerBtn = (Button) findViewById(R.id.buttonRegister);
            registerBtn.setText(getResources().getString(R.string.ConfirmLabel));
            setMessage(exception.getMessage());
        }
    };


    TextWatcher passwword2Watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        @Override
        public void afterTextChanged(Editable s) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
          validateInput();
        }

    };

    public void onRegisterClick(View view) {
        if(registeredUser == null){
            CognitoUserAttributes attr = new CognitoUserAttributes();
            attr.addAttribute("email", this.getEmail());
            CognitoUserPool userPool = CognitoHelper.getUserPool(getApplicationContext());
            userPool.signUpInBackground(this.getEmail(), this.getPassword(), attr, null, signUpHandler);
        }else{
            openVerificaionCodeDialog();
        }
    }

    SignUpHandler signUpHandler = new SignUpHandler() {
        @Override
        public void onSuccess(CognitoUser user, boolean signUpConfirmationState,
                              CognitoUserCodeDeliveryDetails cognitoUserCodeDeliveryDetails) {

            if(!signUpConfirmationState){
                registeredUser = user;
                openVerificaionCodeDialog();
            }else{
                moveToLogin();
            }
        }

        @Override
        public void onFailure(Exception exception) {
            setMessage(exception.getMessage());
        }
    };

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Register Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
