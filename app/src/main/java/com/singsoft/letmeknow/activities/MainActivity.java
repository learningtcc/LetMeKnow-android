package com.singsoft.letmeknow.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.singsoft.letmeknow.R;
import com.singsoft.letmeknow.entities.ContactPoint;
import com.singsoft.letmeknow.rest.LetMeKnowApiContactPoints;

import java.util.ArrayList;


public class MainActivity extends BaseActivity {
    protected LetMeKnowApiContactPoints letMeKnowApiContactPoints = new LetMeKnowApiContactPoints();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setMessage("");
    }

    @Override
    protected  void onCreateDone(){
        getContactPoints();
    }

    protected void setMessage(String text){
        TextView textView = (TextView)findViewById(R.id.textView2);
        textView.setText(text);
    }
    protected void getContactPoints(){
        setMessage("");
        try {
            ArrayList<ContactPoint> contactPoints = null;
            contactPoints = letMeKnowApiContactPoints.getUserContactPoints();
            for(ContactPoint cp : contactPoints){
                TextView tv = new TextView(this);
                tv.setText(cp.getName());
                setContentView(tv);
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void invokeRestApi(View view){
        setMessage("");
        try {
            EditText newCpName = (EditText) findViewById(R.id.NewCPtxt);
            ContactPoint cp = new ContactPoint();
            cp.setName(newCpName.getText().toString());
            cp = letMeKnowApiContactPoints.addContactPoint(cp);
            TextView tv = new TextView(this);
            tv.setText(cp.getName());
            setContentView(tv);
        } catch (Exception e) {
           setMessage(e.getMessage());
        }
    }

}
