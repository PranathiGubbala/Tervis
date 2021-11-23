package com.example.pranathi.tervis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "tervis.db";

    private static String DB_PATH = "";
    private static final int DB_VERSION = 1;

    private SQLiteDatabase mDataBase;
    private final Context mContext;
    private boolean mNeedUpdate = false;
    public static final String pharmacy="pharmacy";

    public static final String Address="address";

    public static final String COL_2 = "P_Name";

    public static final String COL_3 = "P_Owner";

    public static final String COL_4 = "Phone";

    public static final String COL_5="email";

    public static final String COL_6="password";




    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        if (android.os.Build.VERSION.SDK_INT >= 17)
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        else
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.mContext = context;

        copyDataBase();

        this.getReadableDatabase();
    }

    public void updateDataBase() throws IOException {
        if (mNeedUpdate) {
            File dbFile = new File(DB_PATH + DB_NAME);
            if (dbFile.exists())
                dbFile.delete();

            copyDataBase();

            mNeedUpdate = false;
        }
    }

    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    private void copyDataBase() {
        if (!checkDataBase()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    private void copyDBFile() throws IOException {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        //InputStream mInput = mContext.getResources().openRawResource(R.raw.info);
        OutputStream mOutput = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0)
            mOutput.write(mBuffer, 0, mLength);
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }
    public boolean insertData(String p_name,String owner,String p_phone,String addline1,String addline2,String mandal, String city, String state, String email,String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues(); //content values lets us put info inside an object in the form of key value pairs
        ContentValues contentValues2 = new ContentValues();
        contentValues.put(COL_2,p_name);
        contentValues.put(COL_3,owner);
        contentValues.put(COL_4,p_phone);
        contentValues.put(COL_5,email);
        contentValues.put(COL_6,password);
        long result1 = db.insert(pharmacy,null ,contentValues); // insert method takes 3 parameters to insert data into table through writabledatabase
        int id = 0;
        Cursor res = db.rawQuery("select pid from pharmacy where p_owner = '"+owner+"' and password = '"+password+"' ",null);

        while (res.moveToNext()) {
            id = (res.getInt(0));
        }
        Log.i("msg","id-------------------------------------------"+id);
        contentValues2.put("Pid",id);
        contentValues2.put("AddLine1",addline1);
        contentValues2.put("AddLine2",addline2);
        contentValues2.put("Mandal",mandal);
        contentValues2.put("City",city);
        contentValues2.put("State",state);
        contentValues2.put("P_Name",p_name);
        long result2 = db.insert(Address,null ,contentValues2);

        if(result1 == -1 || result2 == -1) {
            Log.i("msg", "result1-----------------------------------" + result1);
            Log.i("msg", "result1-----------------------------------" + result2);
            return false;
        } else {
            Log.i("msg", "result1-----------------------------------" + result1);
            Log.i("msg", "result2-----------------------------------" + result2);
            return true;
        }
    }

    public boolean UpdateData(int phar_id,String p_name,String owner,String p_phone,String addline1,String addline2,String mandal, String city, String state, String email,String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv1 = new ContentValues();
        ContentValues cv2 = new ContentValues();
        cv1.put(COL_2,p_name);
        cv1.put(COL_3,owner);
        cv1.put(COL_4,p_phone);
        cv1.put(COL_5,email);
        cv1.put(COL_6,password);
        cv2.put("Pid",phar_id);
        cv2.put("AddLine1",addline1);
        cv2.put("AddLine2",addline2);
        cv2.put("Mandal",mandal);
        cv2.put("City",city);
        cv2.put("State",state);
        cv2.put("P_Name",p_name);
        db.update(pharmacy, cv1, "Pid = ?",new String[] {String.valueOf(phar_id)} );
        db.update(Address, cv2, "Pid = ?", new String[] {String.valueOf(phar_id)} );

        return true;
    }

    public Cursor getAllData(String medname, String mandal) {
        mDataBase = this.getWritableDatabase();
        Cursor res = mDataBase.rawQuery("select P_Name,AddLine1,AddLine2,Mandal,City,State from address a, medicine m where a.Pid = m.Pid and med_name = '"+medname+"' and status = 'yes' and Mandal LIKE 'Nizampet%' "  ,null);

        return res;

    }
    public Cursor getUserData(String emailid) {
        int pid = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("msg","GetUserdata--------------------------------------------------"+emailid);
        Cursor res = db.rawQuery("select pid from pharmacy where email = '"+emailid+"' ",null);
        while (res.moveToNext()) {
            pid = (res.getInt(0));
        }

        Log.i("msg","pid --------------------------"+pid);
        Cursor result = db.rawQuery("select P.Pid, P.P_Name, P.P_Owner,P.Phone,A. AddLine1, A.AddLine2, A.Mandal, A.City, A.State,P.email, P.password  from Pharmacy P JOIN address A where A.Pid = P.Pid and A.Pid = '"+pid+"' "  ,null);

        return result;

    }
    public boolean insertMedData(String emailid,String medname,String status) {
        int pid = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("msg","Userdata Insert Med--------------------------------------------------"+emailid);
        Cursor res = db.rawQuery("select pid from pharmacy where email = '"+emailid+"' ",null);
        while (res.moveToNext()) {
            pid = (res.getInt(0));
        }
        Log.i("msg","InsertMed Pid--------------------------------------------------"+pid);
        ContentValues contentValues = new ContentValues(); //content values lets us put info inside an object in the form of key value pairs
        contentValues.put("Pid",pid);
        contentValues.put("med_name",medname);
        contentValues.put("status",status);

        long result = db.insert("medicine",null ,contentValues); // insert method takes 3 parameters to insert data into table through writabledatabase

        if(result == -1) {
            Log.i("msg", "Insert Med result-----------------------------------" + result);

            return false;
        } else {
            Log.i("msg", "Insert med result-----------------------------------" + result);

            return true;
        }
    }
    public boolean UpdateMedData(String emailid,String medname,String status,String prevmed){
        int pid = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("msg","Userdata Insert Med--------------------------------------------------"+emailid);
        Cursor res = db.rawQuery("select pid from pharmacy where email = '"+emailid+"' ",null);
        while (res.moveToNext()) {
            pid = (res.getInt(0));
        }
        Log.i("msg","UpdateMed Pid--------------------------------------------------"+pid);
        ContentValues contentValues = new ContentValues(); //content values lets us put info inside an object in the form of key value pairs
        contentValues.put("pid",pid);
        contentValues.put("med_name",medname);
        contentValues.put("status",status);
        Log.i("msg","UpdateMed medname, prevmed--------------------------------------------------"+medname+prevmed);
        db.update("medicine", contentValues, "pid = ? and med_name = ?",new String[]{String.valueOf(pid), prevmed} );

        return true;
    }

    public boolean DeleteMedData(String emailid,String prevmed){
        int pid = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("msg","Deletedata Insert Med--------------------------------------------------"+emailid);
        Cursor res = db.rawQuery("select pid from pharmacy where email = '"+emailid+"' ",null);
        while (res.moveToNext()) {
            pid = (res.getInt(0));
        }

        db.delete("medicine","pid = ? and med_name = ?", new String[] {String.valueOf(pid), prevmed} );

        return true;
    }

    public Cursor getMedicine(String emailid,String prevmed){
        int pid = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select pid from pharmacy where email = '"+emailid+"' ",null);
        while (res.moveToNext()) {
            pid = (res.getInt(0));
        }
        Cursor result = db.rawQuery("select * from medicine where pid = '"+pid+"' and med_name = '"+prevmed+"' "  ,null);
        return result;
    }
    public Cursor getMedData(String emailid) {
        int pid = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("msg","GetUserdata--------------------------------------------------"+emailid);
        Cursor res = db.rawQuery("select pid from pharmacy where email = '"+emailid+"' ",null);
        while (res.moveToNext()) {
            pid = (res.getInt(0));
        }

        Log.i("msg","pid --------------------------"+pid);
        Cursor result = db.rawQuery("select * from medicine where pid = '"+pid+"' "  ,null);

        return result;

    }

    public boolean openDataBase() throws SQLException {
        mDataBase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);

        //Log.i("msg","pharmacy -----------------------------------------------------"+addrr);
        return mDataBase != null;
    }

    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            mNeedUpdate = true;
    }
}