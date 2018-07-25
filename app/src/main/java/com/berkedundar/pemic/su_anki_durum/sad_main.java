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
            if(!FillListView())
                _view = inflater.inflate(R.layout.fragment_bad_connection, container, false);
            return _view;
        }
        else{
            _view = inflater.inflate(R.layout.fragment_not_connected, container, false);
            return _view;
        }
    }
    String TAG = "sad_main";
    private boolean FillListView() {
        String _logs = "";
        try {
            _logs = new JSONTask().execute(new Statics().PULL_ALL_LOGS).get();
            JSONObject jsonObject = new JSONObject(_logs);
            if(jsonObject.getString("success").equals("1")) {
                ListView lv = (ListView) _view.findViewById(R.id.log_list);
                List list = new ArrayList<>();

                JSONArray users = null;
                try{
                    users = new JSONObject(new JSONTask().execute(new Statics().PULL_ALL_USERS).get()).getJSONArray("users");
                } catch (Exception e){
                    Log.e(TAG, "FillListView: " + e.toString() );
                }

                JSONArray logs = jsonObject.getJSONArray("logs");
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
                    list.add(new SAD_Kisi(_temp.getString("id"), shown
                            , _temp.getString("action"), _temp.getString("time")));
                }

                ListAdapter adapter = new ListAdapter(getContext(), list, "SAD_Kisi");

                lv.setAdapter(adapter);
            }
            else {
                //tanımlı log yok
            }
            return true;
        } catch (Exception e) {
            Log.e("sad_main",e.toString());
            return false;
            //new AlertDialog.Builder(getContext()).setMessage("Veri Bağlantısı Yapılamadı\n"+e.toString()).create().show();
        }
    }

}
