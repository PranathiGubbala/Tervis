package com.example.pranathi.tervis;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.pranathi.tervis.PharmacyLogin.UserEmail;


public class PharmacyActivity extends AppCompatActivity {


    Button LogOUT ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy);

        LogOUT = (Button)findViewById(R.id.logout);


        // Adding click listener to Log Out button.
        LogOUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Finishing current DashBoard activity on button click.

                Intent intent = new Intent(PharmacyActivity.this,PharmacyLogin.class);
                startActivity(intent);
                finish();
                Toast.makeText(PharmacyActivity.this,"Log Out Successful", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void stockdata(View view) {

        Intent intent = new Intent(PharmacyActivity.this,StockData.class);

        startActivity(intent);
    }
    public void profile(View view) {


        Intent intent = new Intent(PharmacyActivity.this, Profile.class);

        startActivity(intent);

    }

}
