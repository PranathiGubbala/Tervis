package com.example.pranathi.tervis;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewStockData extends AppCompatActivity {
    ListView mListView;
    ArrayList<Model> mList;
    RecordListAdapter mAdapter = null;
    DatabaseHelper myDb;
    EditText edtmed,edtstat;
    String Email = "";
    String status = "";
    String med = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myDb = new DatabaseHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stock_data);


        mListView = findViewById(R.id.listview);
        mList = new ArrayList<>();
        mAdapter = new RecordListAdapter(this, R.layout.row, mList);
        mListView.setAdapter(mAdapter);
        Session session;
        session = new Session(getApplicationContext());
        Email = session.getusename();

        Cursor res = myDb.getMedData(Email);
        while (res.moveToNext()) {
            int pid = res.getInt(0);
            med = (res.getString(1));
            status = (res.getString(2));
            mList.add(new Model(pid,med,status));
        }
        mAdapter.notifyDataSetChanged();

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                CharSequence[] items = {"update","delete"};

                AlertDialog.Builder dialog = new AlertDialog.Builder(ViewStockData.this);
                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i == 0) {
                            Cursor res = myDb.getMedData(Email);
                            ArrayList<String> arrmeds = new ArrayList<String>();
                            while (res.moveToNext()) {
                                //Log.i("msg","arrmeds --------------------------- "+arrmeds);
                                arrmeds.add(res.getString(1));
                            }
                            Log.i("msg","arrmeds----------------------"+arrmeds.get(position));

                            showDialogUpdate(ViewStockData.this,arrmeds.get(position));
                        }
                        if (i==1){
                            //delete
                            Cursor res = myDb.getMedData(Email);
                            ArrayList<String> arrmeds = new ArrayList<String>();
                            while (res.moveToNext()) {
                                //Log.i("msg","arrmeds --------------------------- "+arrmeds);
                                arrmeds.add(res.getString(1));
                            }
                            showDialogDelete(arrmeds.get(position));


                        }

                    }
                });
                dialog.show();
                return true;
            }
        });

    }
    private void updateRecordList() {
        Cursor res = myDb.getMedData(Email);
        mList.clear();
        while(res.moveToNext()) {
            int pid = res.getInt(0);
            String medname = res.getString(1);
            String stat = res.getString(2);

            mList.add(new Model(pid,medname,stat));
        }
        mAdapter.notifyDataSetChanged();
    }

    private void showDialogDelete(final String drug) {
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(ViewStockData.this);
        dialogDelete.setTitle("Warning!!");
        dialogDelete.setMessage("Are you sure to delete?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    myDb.DeleteMedData(Email,drug);
                    Toast.makeText(ViewStockData.this, "Delete successfully", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Log.e("error", e.getMessage());
                }
                updateRecordList();
            }
        });
        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialogDelete.show();
    }

    public void showDialogUpdate(Activity activity,final String medicine) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.update_dialog);
        dialog.setTitle("Update");

        edtmed = dialog.findViewById(R.id.medicine);
        edtstat = dialog.findViewById(R.id.status_med);
        Button update = dialog.findViewById(R.id.updatemed);

        Cursor res = myDb.getMedicine(Email,medicine);
        while (res.moveToNext()) {
            int pid = res.getInt(0);
            med = (res.getString(1));
            edtmed.setText(med);
            String status = (res.getString(2));
            edtstat.setText(status);
            mList.add(new Model(pid,med,status));
        }

        int width = (int)(activity.getResources().getDisplayMetrics().widthPixels*0.8);
        int height = (int)(activity.getResources().getDisplayMetrics().heightPixels*0.55);
        dialog.getWindow().setLayout(width,height);
        dialog.show();

        update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            myDb.UpdateMedData(Email,edtmed.getText().toString(),edtstat.getText().toString(),medicine);
                            //Log.i("msg","edtmed.getTEXT()-------------------mediicne"+edtmed.getText().toString()+"-------------"+medicine);
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Update Successful",Toast.LENGTH_SHORT).show();

                        }
                        catch(Exception error) {
                            Log.e("Update error",error.getMessage());

                        }
                        updateRecordList();

                    }
                }
        );


    }


}
