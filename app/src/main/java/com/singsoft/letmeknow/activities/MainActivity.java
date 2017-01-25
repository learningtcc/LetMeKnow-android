package com.singsoft.letmeknow.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.singsoft.letmeknow.R;
import com.singsoft.letmeknow.utils.LetMeKnowRestApi;

import java.util.concurrent.ExecutionException;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    protected void setMessage(String text){
        TextView textView = (TextView)findViewById(R.id.textView2);
        textView.setText(text);
    }
    public void invokeRestApi(View view){
        try {
            LetMeKnowRestApi spi = new LetMeKnowRestApi();
            String str = spi.execute(this).get();
            setMessage(str);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

}
