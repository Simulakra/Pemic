package com.berkedundar.pemic.kisi_tanim;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.berkedundar.pemic.R;
import com.berkedundar.pemic.backdata.JSONTask;
import com.berkedundar.pemic.backdata.ListAdapter;
import com.berkedundar.pemic.backdata.Statics;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class kt_kisilerim extends Fragment {
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
        _view = inflater.inflate(R.layout.fragment_kt_kisilerim, container, false);
        PullAllUserDatas();
        // if(!PullAllUserDatas())
            //_view=inflater.inflate(R.layout.fragment_bad_connection, container, false);
        return _view;
    }
    JSONObject users;
    View _view;

    private boolean PullAllUserDatas() {

        try {
            String data = new JSONTask().execute(Statics.PULL_ALL_USERS).get();
            users = new JSONObject(data);
            if(users.getString("success").equals("1")) {
                ListView lv = (ListView) _view.findViewById(R.id.user_list);
                List list = new ArrayList<>();

                JSONArray logs = users.getJSONArray("users");
                for (int i = 0; i < logs.length(); i++) {
                    JSONObject _temp = logs.getJSONObject(i);
                    list.add(new KT_Kisi(_temp.getString("mac"), _temp.getString("nickname")));
                }

                //list.add(new KT_Kisi("12:23:34:45:56:67", "Patates"));

                ListAdapter adapter = new ListAdapter(getContext(), list, "KT_Kisi");

                lv.setAdapter(adapter);
            }
            else{
                //tanımlı kişi yok
            }

            return true;
        }
        catch (Exception e){
            new AlertDialog.Builder(getContext()).setMessage(e.toString()).create().show();
            return false;
        }
    }
}
