package com.singsoft.letmeknow.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.services.cognitoidentityprovider.model.GlobalSignOutRequest;
import com.singsoft.letmeknow.R;
import com.singsoft.letmeknow.utils.CognitoHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CognitoUser cognitoUser = CognitoHelper.getUserPool(getApplicationContext()).getCurrentUser();
        //cognitoUser.globalSignOut();
       /* TextView textView = (TextView) findViewById(R.id.textView2);
         CognitoUser user = CognitoHelper.getUserPool(getApplicationContext()).getCurrentUser();
        textView.setText(user.getUserId());*/
    }
}
