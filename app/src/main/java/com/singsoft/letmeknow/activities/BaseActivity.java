package com.singsoft.letmeknow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

<<<<<<< HEAD
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GetDetailsHandler;
import com.singsoft.letmeknow.R;
import com.singsoft.letmeknow.utils.CognitoHelper;


=======
import com.amazonaws.auth.CognitoCredentialsProvider;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.tokens.CognitoAccessToken;
import com.singsoft.letmeknow.R;
import com.singsoft.letmeknow.utils.CognitoHelper;

>>>>>>> Support logout not yet working
/**
 * Created by meidan.zemer on 1/13/2017.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        }

        @Override
        public void onFailure(Exception exception) {
            moveToLogin();
        }
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
