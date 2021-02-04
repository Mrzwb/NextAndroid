package com.zwb.next.application.systemui;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ActionBarActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableDisplayHome();
    }

    @TargetApi(11)
    private void enableDisplayHome() {
        this.getActionBar().setDisplayHomeAsUpEnabled(true);
    }


}
