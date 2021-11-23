package com.example.pranathi.tervis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText p_name,owner,p_phone,addrline1,addrline2,mandal,city,state,email,password,c_password;
    Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        myDb = new DatabaseHelper(this);
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
        c_password = (EditText)findViewById(R.id.confirm_pass);
        signup = (Button)findViewById(R.id.buttonsignup);

        pharmacysignup();
    }
    public void pharmacysignup() {
        signup.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isInserted = myDb.insertData(p_name.getText().toString(),
                                owner.getText().toString(),
                                p_phone.getText().toString(),
                                addrline1.getText().toString(),
                                addrline2.getText().toString(),
                                mandal.getText().toString(),
                                city.getText().toString(),
                                state.getText().toString(),
                                email.getText().toString(),
                                password.getText().toString());

                        if (isInserted == true) {
                            Toast.makeText(SignUp.this, "Data Inserted", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(SignUp.this, PharmacyLogin.class);

                            startActivity(intent);

                            finish();
                        } else {

                            Toast.makeText(SignUp.this, "Data not Inserted", Toast.LENGTH_LONG).show();

                        }

                    }
                }
                );
    }
}