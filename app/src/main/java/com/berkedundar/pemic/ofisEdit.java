package com.berkedundar.pemic;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
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

    int officeID = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ofis_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        StartUpForUpdate();
        FloatingListeners();

    }

    private void StartUpForUpdate() {
        Intent intent = getIntent();
        if(intent.getBooleanExtra("editMode",false)){
            int _id = intent.getIntExtra("id",-1);
            String _name=intent.getStringExtra("name");
            String _db_ip=intent.getStringExtra("db_ip");
            String _db_name=intent.getStringExtra("db_name");
            String _db_user=intent.getStringExtra("db_user");
            String _db_pass=intent.getStringExtra("db_pass");
            ((EditText)findViewById(R.id.et_name)).setText(_name);
            ((EditText)findViewById(R.id.et_db_ip)).setText(_db_ip);
            ((EditText)findViewById(R.id.et_db_name)).setText(_db_name);
            ((EditText)findViewById(R.id.et_db_user)).setText(_db_user);
            ((EditText)findViewById(R.id.et_db_pass)).setText(_db_pass);
            officeID=_id;
        }
    }

    private void FloatingListeners() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db= openOrCreateDatabase("pemic",MODE_PRIVATE,null);

                String temp;
                ContentValues values = new ContentValues();
                temp = ((EditText)findViewById(R.id.et_name)).getText().toString();
                if(temp.isEmpty()) {
                    Snackbar.make(view, "Ofis Adı bilgisi boş girilemez", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                values.put("Name",temp);
                String officename=temp;

                temp = ((EditText)findViewById(R.id.et_db_ip)).getText().toString();
                if(temp.isEmpty()) {
                    Snackbar.make(view, "Veritabanı IP bilgisi boş girilemez", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                values.put("DB_IP",temp);

                temp = ((EditText)findViewById(R.id.et_db_name)).getText().toString();
                if(temp.isEmpty()) {
                    Snackbar.make(view, "Veritabanı Adı bilgisi boş girilemez", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                values.put("DB_Name",temp);

                temp = ((EditText)findViewById(R.id.et_db_user)).getText().toString();
                if(temp.isEmpty()) {
                    Snackbar.make(view, "Kullanıcı Adı bilgisi boş girilemez", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                values.put("DB_User",temp);

                temp = ((EditText)findViewById(R.id.et_db_pass)).getText().toString();
                values.put("DB_Pass",temp);

                if(officeID==-1) {
                    if(db.query("offices",new String[]{"Count(*)"},"Name=?",new String[]{officename},null,null,null).getCount()==0)
                    {
                        Snackbar.make(view, "Aynı isimde başka bir ofis tanımlamışsınız.\nFarklı bir isim giriniz.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        return;
                    }
                    else db.insert("offices", null, values);
                }
                else db.update("offices",values,"ID=?",new String[]{( String.valueOf(officeID))});

                new AlertDialog.Builder(ofisEdit.this).setMessage("Ofis kaydınız tamamlandı")
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
                new AlertDialog.Builder(ofisEdit.this).setMessage("Değişiklikleriniz kaybolacaktır.")
                        .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).setNegativeButton("Burada Kal",null).create().show();
            }
        });
    }
}
