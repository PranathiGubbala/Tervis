package com.example.pranathi.tervis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /** Called when the user touches the button */
    public void getstarted(View view) {
        // Do something in response to button click
        Intent intent = new Intent(this,HomePage.class);
        startActivity(intent);
    }

}
