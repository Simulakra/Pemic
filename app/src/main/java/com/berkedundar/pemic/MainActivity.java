package com.berkedundar.pemic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.berkedundar.pemic.backdata.Statics;
import com.berkedundar.pemic.kisi_tanim.kt_main;
import com.berkedundar.pemic.ozet_durum.od_main;
import com.berkedundar.pemic.su_anki_durum.sad_main;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.berkedundar.pemic.R.drawable.button_values;

public class MainActivity extends AppCompatActivity {

    JSONObject logs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteFirstActions();
        FloatingButtonClickListenerCrete();
        SetTabActivities();
    }

    @SuppressLint("ResourceAsColor")
    private void SQLiteFirstActions() {
        final SQLiteDatabase db = openOrCreateDatabase("pemic",MODE_PRIVATE,null);
        try{
            Cursor cursor = db.query("offices",new String[]{"ID","Name","DB_IP","DB_Name","DB_User","DB_Pass"}
            ,null,null,null,null,null);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

            int _count=0;
            while(cursor.moveToNext()){
                _count++;
                final int _id = cursor.getInt(0);
                final String _name=cursor.getString(1);
                final String _db_ip=cursor.getString(2);
                final String _db_name=cursor.getString(3);
                final String _db_user=cursor.getString(4);
                final String _db_pass=cursor.getString(5);

                Button bt=new Button(getApplicationContext());
                bt.setText(_name);
                bt.setBackground(getResources().getDrawable(R.drawable.button_values));
                bt.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        /*Intent intent = new Intent(MainActivity.this, ofisEdit.class);
                        intent.putExtra("editMode", true);
                        intent.putExtra("id", _id);
                        intent.putExtra("name", _name);
                        intent.putExtra("db_ip", _db_ip);
                        intent.putExtra("db_name", _db_name);
                        intent.putExtra("db_user", _db_user);
                        intent.putExtra("db_pass", _db_pass);
                        startActivityForResult(intent, 13);*/
                        new AlertDialog.Builder(MainActivity.this)
                                .setMessage("Ofisi kaldırmak istediğinize emin misiniz?")
                                .setPositiveButton("Kaldır", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        db.delete("offices","ID=?",new String[]{String.valueOf(_id)});
                                        RestartApp();
                                    }
                                })
                                .setNeutralButton("İptal",null).create().show();
                        return false;
                    }
                });
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //office connection yap
                        Statics.ActiveOffice = _id;
                        Statics.ActiveIP = _db_ip;
                        Statics.ActiveDB = _db_name;
                        Statics.ActiveUser = _db_user;
                        Statics.ActivePass = _db_pass;
                        ((Button)v).setTextColor(R.color.colorAccent);
                        SetTabActivities();
                    }
                });
                toolbar.addView(bt);
            }
            if(cursor.getCount()==0){
                Intent intent = new Intent(MainActivity.this, ofisEdit.class);
                startActivityForResult(intent, 13);
            }

        }catch (Exception e){
            db.execSQL("CREATE TABLE offices(" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Name TEXT, DB_IP TEXT, DB_Name TEXT," +
                    "DB_User TEXT, DB_Pass TEXT)");
            Intent intent = new Intent(MainActivity.this, ofisEdit.class);
            startActivityForResult(intent, 13);
        }
    }

    private void FloatingButtonClickListenerCrete() {
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab_add_office);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ofisEdit.class);
                startActivityForResult(intent, 13);
            }
        });
    }

    String TAG="MainActivity";
    public void SetTabActivities() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Add Fragments to adapter one by one
        adapter.addFragment(new od_main(), "Özet Durum");
        adapter.addFragment(new sad_main(), "Şu Anki Durum");
        adapter.addFragment(new kt_main(), "Kişi Tanımlama");
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 13) { //ofisEdit gidişi
            RestartApp();
        }
        if (requestCode == 25) { //ofisEdit gidişi
            SetTabActivities();
        }
    }

    private void RestartApp(){
        Intent mStartActivity = new Intent(MainActivity.this, MainActivity.class);
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(MainActivity.this, mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager)MainActivity.this.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
        System.exit(0);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
