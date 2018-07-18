package com.berkedundar.pemic;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class MainActivity extends AppCompatActivity {

    JSONObject logs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //Statics.BASE_URL = preferences.getString("baseID","192.168.1.23");

        Set<String> offices = preferences.getStringSet("offices",null);
        /*if(offices==null)
        {
            Intent intent = new Intent(MainActivity.this, ofisEdit.class);
            startActivityForResult(intent, 13);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        for (int i=0; i<offices.size(); i++){
            String _temp = offices.toArray()[i].toString();
            String[] _office = _temp.split("~");

            Button bt=new Button(getApplicationContext());
            bt.setText(_office[1]);
            toolbar.addView(bt);
        }*/
        if(offices!=null){
            Snackbar.make(getCurrentFocus(), offices.toArray()[0].toString(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Button bt=new Button(getApplicationContext());
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                //Statics.BASE_URL = preferences.getString("baseID","192.168.1.23");

                Set<String> offices = preferences.getStringSet("offices",null);
                if(offices!=null){
                    new AlertDialog.Builder(MainActivity.this).setMessage(offices.toArray()[0].toString())
                            .setPositiveButton("Tamam",null).create().show();
                }
                else{
                    new AlertDialog.Builder(MainActivity.this).setMessage("Office is null")
                            .setPositiveButton("Tamam",null).create().show();
                }
            }
        });
        bt.setText("Ofis #01");
        toolbar.addView(bt);

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        FloatingButtonClickListenerCrete();
        SetTabActivities();
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

    private void SetTabActivities() {
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
        }
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
