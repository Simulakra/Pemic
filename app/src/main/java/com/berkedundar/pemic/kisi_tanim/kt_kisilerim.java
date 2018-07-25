package com.berkedundar.pemic.kisi_tanim;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.berkedundar.pemic.R;
import com.berkedundar.pemic.backdata.JSONTask;
import com.berkedundar.pemic.backdata.ListAdapter;
import com.berkedundar.pemic.backdata.Statics;
import com.berkedundar.pemic.userEdit;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


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
            String data = new JSONTask().execute(new Statics().PULL_ALL_USERS).get();
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

                KisiDuzenlemePaneli(lv);
            }
            else{
                //tanımlı kişi yok
            }

            return true;
        }
        catch (Exception e){
            Log.e(TAG, "PullAllUserDatas: "+e.toString() );
            //new AlertDialog.Builder(getContext()).setMessage(e.toString()).create().show();
            return false;
        }
    }
    String TAG = "kt_kisilerim";
    private void KisiDuzenlemePaneli(ListView lv) {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int _position = position;
                new AlertDialog.Builder(getContext()).setMessage("Seçtiğiniz kullanıcı için yapacağınız işlem menüsünü seçin")
                        .setPositiveButton("Düzenle", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getActivity(), userEdit.class);
                                ListView listView = (ListView)_view.findViewById(R.id.user_list);
                                intent.putExtra("editMode",true);
                                intent.putExtra("mac",((KT_Kisi)listView.getItemAtPosition(_position)).getMAC());
                                intent.putExtra("nick",((KT_Kisi)listView.getItemAtPosition(_position)).getNickname());
                                startActivityForResult(intent,25);
                            }
                        })
                        .setNegativeButton("Sil", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ListView listView = (ListView)_view.findViewById(R.id.user_list);
                                String _temp = ((KT_Kisi)listView.getItemAtPosition(_position)).getMAC();
                                try {
                                    Log.d(TAG, "onClick: "+_temp);
                                    new JSONTask().execute(new Statics().DELETE_ONE_USER,"MAC",_temp).get();
                                } catch (Exception e){
                                    Log.e(TAG, e.toString() );
                                }
                                ((MainActivity)getActivity()).SetTabActivities();
                            }
                        })
                        .setNeutralButton("İptal",null).create().show();
            }
        });
    }
}
