package com.berkedundar.pemic;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.ArraySet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

public class ofisEdit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ofis_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingListeners();

    }

    private void FloatingListeners() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Set<String> offices = preferences.getStringSet("offices",null);
                if(offices==null){ offices=new HashSet<String>(); }

                String officeData="", temp;

                temp = ((EditText)findViewById(R.id.et_db_ip)).getText().toString()+"~";
                if(temp.equals("~")){
                    Snackbar.make(view, "Giriş IP bilgisi boş girilemez", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                    return;
                }
                else officeData+=temp;

                temp = ((EditText)findViewById(R.id.et_db_name)).getText().toString()+"~";
                if(temp.equals("~")){
                    Snackbar.make(view, "Ofis Adı bilgisi boş girilemez", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                else officeData+=temp;

                temp = ((EditText)findViewById(R.id.et_db_user)).getText().toString()+"~";
                if(temp.equals("~")){
                    Snackbar.make(view, "Veritabanı Kullanıcı Adı bilgisi boş girilemez", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                else officeData+=temp;

                temp = ((EditText)findViewById(R.id.et_db_pass)).getText().toString();
                if(temp.isEmpty()){

                }
                else officeData+=temp;

                offices.add(officeData);


                preferences.edit().putStringSet("offices", offices);
                new AlertDialog.Builder(ofisEdit.this).setMessage("Ofis kaydınız oluşturuldu")
                        .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).create().show();
            }
        });

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });
    }

}
