package com.berkedundar.pemic.ozet_durum;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.berkedundar.pemic.R;
import com.berkedundar.pemic.backdata.JSONTask;
import com.berkedundar.pemic.backdata.ListAdapter;
import com.berkedundar.pemic.backdata.Statics;
import com.berkedundar.pemic.su_anki_durum.SAD_Kisi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class od_main extends Fragment {

    public od_main() {
        // Required empty public constructor
    }
    View _view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) { }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(Statics.ActiveOffice!=-1){
            _view = inflater.inflate(R.layout.fragment_od_main, container, false);
            if(!FillListView())
                _view = inflater.inflate(R.layout.fragment_bad_connection, container, false);
            return _view;
        }
        else{
            _view = inflater.inflate(R.layout.fragment_not_connected, container, false);
            return _view;
        }
    }

    String TAG="od_main";
    private boolean FillListView() {
        String _logs = "";
        try {
            _logs = new JSONTask().execute(Statics.PULL_ONLINE_USERS).get();
            JSONObject jsonObject = new JSONObject(_logs);
            if(jsonObject.getString("success").equals("1")) {
                ListView lv = (ListView) _view.findViewById(R.id.log_user_list);
                List list = new ArrayList<>();

                JSONArray users = null;
                try{
                    users = new JSONObject(new JSONTask().execute(Statics.PULL_ALL_USERS).get()).getJSONArray("users");
                } catch (Exception e){
                    Log.e(TAG, "FillListView: " + e.toString() );
                }

                JSONArray logs = jsonObject.getJSONArray("users");
                for (int i = 0; i < logs.length() && i < Statics.ShowLogCount; i++) {
                    JSONObject _temp = logs.getJSONObject(i);

                    String shown=_temp.getString("mac");
                    if(users != null){
                        for (int j=0;j<users.length();j++){
                            JSONObject ttt = users.getJSONObject(j);
                            if(ttt.getString("mac").equals(shown)){
                                shown = ttt.getString("nickname");
                                break;
                            }
                        }
                    }


                    list.add(new OD_Kisi(shown));
                }

                ListAdapter adapter = new ListAdapter(getContext(), list, "OD_Kisi");

                lv.setAdapter(adapter);
            }
            else {
                //tanımlı log yok
            }
            return true;
        } catch (Exception e) {
            Log.e("od_main",e.toString());
            return false;
            //new AlertDialog.Builder(getContext()).setMessage("Veri Bağlantısı Yapılamadı\n"+e.toString()).create().show();
        }
    }
}
