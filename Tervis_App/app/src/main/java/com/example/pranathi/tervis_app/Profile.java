package com.example.pranathi.tervis;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import static com.example.pranathi.tervis.PharmacyLogin.UserEmail;


public class Profile extends AppCompatActivity {


    EditText p_name,owner,p_phone,addrline1,addrline2,mandal,city,state,email,password;
    Button update_button;
    DatabaseHelper myDb;
    int pid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myDb = new DatabaseHelper(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        p_name = (EditText)findViewById(R.id.pharmacyname);
        owner = (EditText)findViewById(R.id.pharmacyowner);
        p_phone = (EditText)findViewById(R.id.MobNumber);
        addrline1 = (EditText)findViewById(R.id.addressline1);
        addrline2 = (EditText)findViewById(R.id.addressline2);
        mandal = (EditText)findViewById(R.id.mandal);
        city = (EditText)findViewById(R.id.city);
        state = (EditText)findViewById(R.id.state);
        email = (EditText)findViewById(R.id.emailid);
        password = (EditText)findViewById(R.id.pass);
        update_button = (Button)findViewById(R.id.update);
        Session session;
        session = new Session(getApplicationContext());
        String Email = session.getusename();
        Log.i("msg","Profile ----------------------------------------"+Email);
        Cursor res = myDb.getUserData(Email);
        if(res.getCount() == 0) {
            // show message
            showMessage("Empty","Nothing found");

        } else {

            while(res.moveToNext()) {
                pid = res.getInt(0);
                p_name.setText(res.getString(1));
                owner.setText(res.getString(2));
                p_phone.setText(res.getString(3));
                addrline1.setText(res.getString(4));
                addrline2.setText(res.getString(5));
                mandal.setText(res.getString(6));
                city.setText(res.getString(7));
                state.setText(res.getString(8));
                email.setText(res.getString(9));
                password.setText(res.getString(10));
            }

        }
        update();

    }
    public void update() {

        update_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        boolean isUpdated = myDb.UpdateData(pid,
                                p_name.getText().toString(),
                                owner.getText().toString(),
                                p_phone.getText().toString(),
                                addrline1.getText().toString(),
                                addrline2.getText().toString(),
                                mandal.getText().toString(),
                                city.getText().toString(),
                                state.getText().toString(),
                                email.getText().toString(),
                                password.getText().toString());

                        if (isUpdated == true) {
                            Toast.makeText(Profile.this, "Data Updated", Toast.LENGTH_LONG).show();

                            String EmailHolder = email.getText().toString();



                        } else {

                            Toast.makeText(Profile.this, "Data not Updated", Toast.LENGTH_LONG).show();

                        }

                    }
                }
        );
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}

