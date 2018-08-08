package com.billy.nativecodeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import static android.app.Activity.RESULT_OK;

public class NewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
    }

    public void clickedBack(View view) {
        Intent intent = new Intent();
        intent.putExtra("RESULTS", "Returned message!");
        setResult(RESULT_OK, intent);
        finish();
    }
}
