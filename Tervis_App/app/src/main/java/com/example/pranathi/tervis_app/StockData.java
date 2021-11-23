package com.example.pranathi.tervis;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StockData extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText medname,status;
    Button add,view;
    String Email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myDb = new DatabaseHelper(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_data);



        medname = (EditText)findViewById(R.id.medname);
        status = (EditText)findViewById(R.id.status);
        add = (Button)findViewById(R.id.addmed);
        view = (Button)findViewById(R.id.viewmed);

        addmed();
        viewmed();
    }
    public void addmed() {
        Session session;
        session = new Session(getApplicationContext());
        Email = session.getusename();
        Log.i("msg","addmed---------------------------------------------------"+Email);
        add.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isInserted = myDb.insertMedData(Email,medname.getText().toString(),
                                status.getText().toString());

                        if (isInserted == true) {
                            Toast.makeText(StockData.this, "Data Added", Toast.LENGTH_LONG).show();

                        } else {

                            Toast.makeText(StockData.this, "Data not Inserted", Toast.LENGTH_LONG).show();

                        }

                    }
                }
        );
    }
    public void viewmed() {
        Session session;
        session = new Session(getApplicationContext());
        Email = session.getusename();
        Log.i("msg","viewmed---------------------------------------------------"+Email);
        view.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cursor res = myDb.getMedData(Email);
                        if(res.getCount() == 0) {
                            // show message
                            showMessage("Empty","Nothing found");
                            return;
                        } else {

                        }
                        Intent intent = new Intent(StockData.this,ViewStockData.class);
                        startActivity(intent);
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
