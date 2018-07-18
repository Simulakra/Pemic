package com.berkedundar.pemic.su_anki_durum;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.berkedundar.pemic.backdata.JSONTask;
import com.berkedundar.pemic.R;
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
        _view = inflater.inflate(R.layout.fragment_sad_main, container, false);
        String _logs = "";
        try {
            _logs = new JSONTask().execute(Statics.SELECT_API).get();
            JSONObject jsonObject = new JSONObject(_logs);

            ListView lv=(ListView)_view.findViewById(R.id.log_list);
            List<String> list = new ArrayList<String>();

            JSONArray logs = jsonObject.getJSONArray("logs");
            for (int i=0; i<logs.length(); i++){
                list.add(logs.getJSONObject(i).toString());
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_list_item_1,
                    list );

            lv.setAdapter(arrayAdapter);

        } catch (Exception e) {
            new AlertDialog.Builder(getContext()).setMessage("Veri Bağlantısı Yapılamadı\n"+e.toString()).create().show(); }
        return _view;
    }

}
