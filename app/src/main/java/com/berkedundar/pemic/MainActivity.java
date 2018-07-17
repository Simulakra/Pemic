package com.berkedundar.pemic;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    JSONObject logs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //new LoadAllProducts().execute();


        CreateFloatingBarListener();
    }

    private void CreateFloatingBarListener() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("enterance","");
                if(logs!=null) {
                    Log.d("enterance","inside");
                    try{
                    Snackbar.make(view, logs.getJSONObject("logs").getString("mac"), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    }catch (Exception e) {
                        Log.d("cant find",e.toString());
                    }
                    try {
                        String temp = new JSONTask().execute(Statics.INSERT_API,
                                "mac","11:11:11:11:11:11",
                                "action","0",
                                "time","2018-07-17 07:07:00").get();
                        new AlertDialog.Builder(MainActivity.this).setMessage(temp).create().show();
                    }catch (Exception e){
                        Log.d("Cant enter",e.toString());
                    }
                }
                else{
                    Snackbar.make(view, "Getting Values", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    try {
                        String deger = new JSONTask().execute(Statics.SELECT_API).get();
                        logs=new JSONObject(deger);

                    } catch (Exception e){
                        Log.e("Error",e.toString());
                    }
                    Log.d("end work","else");
                }
                Log.d("end work","all");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
