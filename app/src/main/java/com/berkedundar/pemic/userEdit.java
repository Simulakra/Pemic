package com.berkedundar.pemic;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.berkedundar.pemic.backdata.JSONTask;
import com.berkedundar.pemic.backdata.Statics;

public class userEdit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        StartUpForUpdate();
        FloatingListeners();
    }

    private void StartUpForUpdate() {
        Intent intent = getIntent();
        if(intent.getBooleanExtra("editMode",false)){
            String _mac=intent.getStringExtra("mac");
            String _nick=intent.getStringExtra("nick");
            ((EditText)findViewById(R.id.et_mac)).setText(_mac);
            ((EditText)findViewById(R.id.et_nick)).setText(_nick);
            userMAC=_mac;
        }
        else{
            String _mac=intent.getStringExtra("mac");
            ((EditText)findViewById(R.id.et_mac)).setText(_mac);
        }
    }

    public String userMAC="";

    private void FloatingListeners() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab3);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String temp = ((EditText)findViewById(R.id.et_nick)).getText().toString();
                if(temp.isEmpty()) {
                    Snackbar.make(view, "Kullanıcı Adı bilgisi boş girilemez", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }

                String mac = ((EditText)findViewById(R.id.et_mac)).getText().toString();

                try {
                    if (userMAC.equals("")) {
                        new JSONTask().execute(new Statics().ADD_NEW_USER, "MAC", mac, "Nickname", temp).get();
                    } else {
                        new JSONTask().execute(new Statics().EDIT_ONE_USER, "MAC", mac, "Nickname", temp, "old_mac", userMAC).get();
                    }
                }catch (Exception e) {
                    new AlertDialog.Builder(userEdit.this).setMessage(e.toString()).create().show();
                }

                new AlertDialog.Builder(userEdit.this).setMessage("Kullanıcı kaydınız tamamlandı")
                        .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).create().show();
            }
        });

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab4);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(userEdit.this).setMessage("Değişiklikleriniz kaybolacaktır.")
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
