package com.berkedundar.pemic;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.berkedundar.pemic.backdata.Statics;
import com.berkedundar.pemic.kisi_tanim.kt_main;
import com.berkedundar.pemic.ozet_durum.od_main;
import com.berkedundar.pemic.su_anki_durum.sad_main;
import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    JSONObject logs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteFirstActions();
        FloatingButtonClickListenerCrete();
        SetTabActivities();
        StartLoadingImage();
        HideLoadingImage();
        //ShowLoadingImage();
        CGNInfoImage();
    }

    private void CGNInfoImage() {
        ImageView iv=(ImageView)findViewById(R.id.iv_cgn);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message="Bu uygulama CGN Yazılım & Bilişim Hizmetleri ürünüdür.\n\n" +
                        "Android Codder: Berke DÜNDAR\n" +
                        "Backend API Codder: Berke DÜNDAR\n" +
                        "Logchecker Codder: Ali Cem KARIŞ\n" +
                        "Proje Yöneticisi: Çağan S. YÜCEL\n\n" +
                        "www.cgnyazilim.com";
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage(message)
                        .create().show();
            }
        });
    }

    public void HideLoadingImage() {
        ((ImageView)findViewById(R.id.iv_load)).setVisibility(View.INVISIBLE);
    }

    public void ShowLoadingImage() {
        ((ImageView)findViewById(R.id.iv_load)).setVisibility(View.VISIBLE);
    }

    private void StartLoadingImage() {
        Glide.with(MainActivity.this)
                .load(R.drawable.wifi_load)
                .into((ImageView)findViewById(R.id.iv_load));
    }

    @SuppressLint("ResourceAsColor")
    private void SQLiteFirstActions() {
        final SQLiteDatabase db = openOrCreateDatabase("pemic",MODE_PRIVATE,null);
        try{
            Cursor cursor = db.query("offices",new String[]{"ID","Name","DB_IP","DB_Name","DB_User","DB_Pass"}
            ,null,null,null,null,null);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


            // you need to have a list of data that you want the spinner to display
            List<String> spinnerArray =  new ArrayList<String>();

            boolean first=true;
            while(cursor.moveToNext()){
                final int _id = cursor.getInt(0);
                final String _name=cursor.getString(1);
                final String _db_ip=cursor.getString(2);
                final String _db_name=cursor.getString(3);
                final String _db_user=cursor.getString(4);
                final String _db_pass=cursor.getString(5);

                spinnerArray.add(_name);

                if(first) {
                    Statics.ActiveOffice = _id;
                    Statics.ActiveIP = _db_ip;
                    Statics.ActiveDB = _db_name;
                    Statics.ActiveUser = _db_user;
                    Statics.ActivePass = _db_pass;
                }

            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, spinnerArray);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            final Spinner sItems = (Spinner) findViewById(R.id.spn_offices);
            sItems.setAdapter(adapter);

            sItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    String officename = sItems.getItemAtPosition(position).toString();
                    Cursor cursor = db.query("offices",new String[]{"ID","DB_IP","DB_Name","DB_User","DB_Pass"}
                            ,"Name=?",new String[]{officename},null,null,null);
                    cursor.moveToNext();
                    Statics.ActiveOffice = cursor.getInt(0);
                    Statics.ActiveIP=cursor.getString(1);
                    Statics.ActiveDB=cursor.getString(2);
                    Statics.ActiveUser=cursor.getString(3);
                    Statics.ActivePass=cursor.getString(4);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    Statics.ActiveOffice = -1;
                    Statics.ActiveIP="";
                    Statics.ActiveDB="";
                    Statics.ActiveUser="";
                    Statics.ActivePass="";
                }

            });

            if(cursor.getCount()==0){
                Intent intent = new Intent(MainActivity.this, ofisEdit.class);
                startActivityForResult(intent, 13);
            }

        }catch (Exception e){
            Log.e(TAG, "SQLiteFirstActions: "+e.toString() );
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
        FloatingActionButton fab2 = (FloatingActionButton)findViewById(R.id.fab_reflesh);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetTabActivities();
            }
        });
    }

    Boolean systemtabwork=false;
    String TAG="MainActivity";
    public void SetTabActivities() {
        systemtabwork=true;
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Add Fragments to adapter one by one
        adapter.addFragment(new od_main(), "Özet Durum");
        adapter.addFragment(new sad_main(), "Şu Anki Durum");
        adapter.addFragment(new kt_main(), "Kişi Tanımlama");
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        final SharedPreferences sha = MainActivity.this.getSharedPreferences("pemic",MODE_PRIVATE);
        int pos = sha.getInt("office",-1);
        Log.d(TAG, "SetTabActivities: "+pos);
        if(pos!=-1)
        {
            TabLayout.Tab tab = tabLayout.getTabAt(pos);
            tab.select();
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (!systemtabwork) {
                    SharedPreferences.Editor editor = sha.edit();
                    editor.putInt("office", tab.getPosition());
                    Log.d(TAG, "onTabSelected: " + tab.getPosition());
                    editor.commit();
                    int pos = sha.getInt("office", -1);
                    Log.d(TAG, "editor.commit: " + pos);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        systemtabwork=false;
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
