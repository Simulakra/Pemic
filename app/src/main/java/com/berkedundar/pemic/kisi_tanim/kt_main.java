package com.berkedundar.pemic.kisi_tanim;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.berkedundar.pemic.MainActivity;
import com.berkedundar.pemic.R;
import com.berkedundar.pemic.backdata.JSONTask;
import com.berkedundar.pemic.backdata.ListAdapter;
import com.berkedundar.pemic.backdata.Statics;
import com.berkedundar.pemic.ozet_durum.od_main;
import com.berkedundar.pemic.su_anki_durum.SAD_Kisi;
import com.berkedundar.pemic.su_anki_durum.sad_main;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class kt_main extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }
    View _view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(Statics.ActiveOffice!=-1){
            _view = inflater.inflate(R.layout.fragment_kt_main, container, false);
            SetTabActivities();

            return _view;
        }
        else{
            _view = inflater.inflate(R.layout.fragment_not_connected, container, false);
            return _view;
        }
    }

    private void SetTabActivities() {
        ViewPager viewPager = (ViewPager) _view.findViewById(R.id.vp_kt);
        kt_main.ViewPagerAdapter adapter = new kt_main.ViewPagerAdapter(getChildFragmentManager());

        // Add Fragments to adapter one by one
        adapter.addFragment(new kt_kisilerim(), "Kişilerim");
        adapter.addFragment(new kt_kisiEkle(), "Kişi Ekle");
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) _view.findViewById(R.id.ty_kt);
        tabLayout.setupWithViewPager(viewPager);

        List<String> list = new ArrayList<String>();

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
