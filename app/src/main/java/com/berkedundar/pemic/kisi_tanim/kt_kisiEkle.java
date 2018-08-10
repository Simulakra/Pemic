package com.berkedundar.pemic.kisi_tanim;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

import static android.content.Context.MODE_PRIVATE;


public class kt_kisiEkle extends Fragment {
    private View _view;
    String TAG="kt_kisiEkle";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TryToCreateSilentMacTable();
    }
    SQLiteDatabase db;
    private void TryToCreateSilentMacTable() {
        try{
            db= getContext().openOrCreateDatabase("pemic",MODE_PRIVATE,null);
            db.execSQL("CREATE TABLE SilentMacs(MAC TEXT PRIMARY KEY)");
        }catch (Exception e){}
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
            String data = new JSONTask(getContext()).execute(new Statics().PULL_ALL_NON_USERS).get();
            JSONObject users = new JSONObject(data);
            if(users.getString("success").equals("1")){
                ListView lv = (ListView) _view.findViewById(R.id.non_user_list);
                List list = new ArrayList<>();
                List silent=new ArrayList<>();
                JSONArray logs = users.getJSONArray("macs");

                for (int i = 0; i < logs.length(); i++) {
                    JSONObject _temp = logs.getJSONObject(i);
                    String mac=_temp.getString("mac");
                    if(db.query("SilentMacs",new String[]{"MAC"},
                            "MAC=?",new String[]{mac},null,null,null)
                            .getCount()==0)
                        list.add(new KT_Kisi(_temp.getString("last"), mac ));
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
            Log.e(TAG, "PullAllUserDatas: "+e.toString() );
            //new AlertDialog.Builder(getContext()).setMessage(e.toString()).create().show();
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
                startActivityForResult(intent,25);
                //((MainActivity)getActivity()).SetTabActivities();
            }
        });
    }

}
