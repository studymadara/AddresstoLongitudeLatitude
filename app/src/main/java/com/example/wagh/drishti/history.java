package com.example.wagh.drishti;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Wagh on 23-Jun-16.
 */
public class history extends AppCompatActivity {

    Double latitude,longitude,latitude1,longitude1;
    SQLiteDatabase mydb;
    String address2,address3;
    TextView tv1,tv2;
    String tablename="TEST";
    String result;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        tv1=(TextView)findViewById(R.id.ans);
        tv2=(TextView)findViewById(R.id.ans2);
        //getting data from home activity

        try {


            Intent i2 = getIntent();
            latitude = i2.getDoubleExtra("Lat", 000);
            longitude = i2.getDoubleExtra("Long", 000);
            address2=i2.getStringExtra("Address");
            String lati = latitude.toString();
            String longi = longitude.toString();
            tv1.setText("Latitude  "+lati + "  Longitude  " + longi+"  Address  "+address2);

            //saving data
            mydb = this.openOrCreateDatabase(tablename, MODE_PRIVATE, null);
            mydb.execSQL("CREATE TABLE IF NOT EXISTS " + tablename + "(Lat DOUBLE,Long DOUBLE,Address VARCHAR2);");
            mydb.execSQL("INSERT INTO " + tablename + " (Lat,Long,Address) VALUES (" + latitude + ","+longitude+",'"+address2+"');");
            //saving ends

            //fetching starts

            Cursor cc = mydb.rawQuery("SELECT * FROM " + tablename, null);
            int i = cc.getColumnIndex("Lat");
            int ii = cc.getColumnIndex("Long");
            int iii= cc.getColumnIndex("Address");
            cc.moveToFirst();

            //getting the count of the table

            Toast.makeText(getApplicationContext(),"ScrollDown for history data",Toast.LENGTH_LONG).show();

            int count =cc.getCount();
            for(int zi=0;zi<=count;zi++)
            {

                latitude1=cc.getDouble(i);
                longitude1=cc.getDouble(ii);
                address3=cc.getString(iii);
                result+=" \nAddress::";
                result+=address3;
                result+=" \nLatitude::";
                result+=latitude1.toString();
                result+=" \nLongitude::";
                result+=longitude1.toString();
                tv2.setText("Database:::::::::::::::::::\n"+result+"\n");
                cc.moveToNext();

            }

            //fetching ends  #strange it didnt worked in mac pc



        }
        catch (Exception e)
        {
            Log.e("ERROR","ACTIVITY 2",e);
        }
        finally {
            mydb.close();
        }




    }
}
