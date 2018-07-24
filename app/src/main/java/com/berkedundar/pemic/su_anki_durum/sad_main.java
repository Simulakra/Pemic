package com.berkedundar.pemic.su_anki_durum;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.berkedundar.pemic.backdata.JSONTask;
import com.berkedundar.pemic.R;
import com.berkedundar.pemic.backdata.ListAdapter;
import com.berkedundar.pemic.backdata.Statics;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class sad_main extends Fragment {
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
            _view = inflater.inflate(R.layout.fragment_sad_main, container, false);
            Log.d("test",String.valueOf(FillListView()));
            //if(!FillListView())
                //_view = inflater.inflate(R.layout.fragment_bad_connection, container, false);
            return _view;
        }
        else{
            _view = inflater.inflate(R.layout.fragment_not_connected, container, false);
            return _view;
        }
    }

    private boolean FillListView() {
        String _logs = "";
        try {
            _logs = new JSONTask().execute(Statics.PULL_ALL_LOGS).get();
            JSONObject jsonObject = new JSONObject(_logs);
            Log.d("data",jsonObject.toString());
            if(jsonObject.getString("success").equals("1")) {
                ListView lv = (ListView) _view.findViewById(R.id.log_list);
                List list = new ArrayList<>();

                JSONArray logs = jsonObject.getJSONArray("logs");
                for (int i = 0; i < logs.length(); i++) {
                    JSONObject _temp = logs.getJSONObject(i);
                    list.add(new SAD_Kisi(_temp.getString("id"), _temp.getString("mac")
                            , _temp.getString("action"), _temp.getString("time")));
                }

                ListAdapter adapter = new ListAdapter(getContext(), list, "SAD_Kisi");

                lv.setAdapter(adapter);
            }
            else {
                //tanımlı log yok falan
            }
            return true;
        } catch (Exception e) {
            return false;
            //new AlertDialog.Builder(getContext()).setMessage("Veri Bağlantısı Yapılamadı\n"+e.toString()).create().show();
        }
    }

}
