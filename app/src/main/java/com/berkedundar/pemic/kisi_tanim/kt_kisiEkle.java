package com.berkedundar.pemic.kisi_tanim;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.berkedundar.pemic.MainActivity;
import com.berkedundar.pemic.userEdit;

import com.berkedundar.pemic.R;
import com.berkedundar.pemic.backdata.JSONTask;
import com.berkedundar.pemic.backdata.ListAdapter;
import com.berkedundar.pemic.backdata.Statics;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class kt_kisiEkle extends Fragment {
    private View _view;
    String TAG="kt_kisiEkle";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        _view =inflater.inflate(R.layout.fragment_kt_kisi_ekle, container, false);
        PullAllNonUsers();
        //if(!PullAllNonUsers())
            //_view=inflater.inflate(R.layout.fragment_bad_connection, container, false);
        return _view;
    }

    private boolean PullAllNonUsers() {
        try {
            String data = new JSONTask().execute(Statics.PULL_ALL_NON_USERS).get();
            JSONObject users = new JSONObject(data);
            if(users.getString("success").equals("1")){
                ListView lv = (ListView) _view.findViewById(R.id.non_user_list);
                List list = new ArrayList<>();
                JSONArray logs = users.getJSONArray("macs");
                for (int i = 0; i < logs.length(); i++) {
                    JSONObject _temp = logs.getJSONObject(i);
                    list.add(new KT_Kisi(_temp.getString("last"), _temp.getString("mac")));
                }

                //list.add(new KT_Kisi("12:23:34:45:56:67","Patates"));

                ListAdapter adapter = new ListAdapter(getContext(), list, "KT_Kisi_non");

                lv.setAdapter(adapter);

                KisiEklemePaneli(lv);
            }
            else{
                //tanımsız kişi yok
            }

            return true;
        }
        catch (Exception e){
            new AlertDialog.Builder(getContext()).setMessage(e.toString()).create().show();
            return false;
        }
    }

    private void KisiEklemePaneli(ListView lv) {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), userEdit.class);
                ListView listView = (ListView)_view.findViewById(R.id.non_user_list);
                intent.putExtra("mac",((KT_Kisi)listView.getItemAtPosition(position)).getNickname());
                startActivity(intent);
                ((MainActivity)getActivity()).SetTabActivities();
            }
        });
    }

}
