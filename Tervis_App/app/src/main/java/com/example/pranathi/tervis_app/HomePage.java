package com.example.pranathi.tervis;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import java.io.File;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

    }
        public void ptactivity1(View view) {
        // Do something in response to button click
        Intent intent = new Intent(this,MapsActivity.class);
        startActivity(intent);
    }
    public void pharmacylogin(View view) {
        // Do something in response to button click
        Intent intent = new Intent(this,PharmacyLogin.class);
        startActivity(intent);

    }


}
