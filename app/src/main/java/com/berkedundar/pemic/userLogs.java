package com.berkedundar.pemic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.berkedundar.pemic.backdata.JSONTask;
import com.berkedundar.pemic.backdata.ListAdapter;
import com.berkedundar.pemic.backdata.Statics;
import com.berkedundar.pemic.su_anki_durum.SAD_Kisi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class userLogs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_logs);

        fillAllUserLogs();
    }

    private boolean fillAllUserLogs() {
        String mac = getIntent().getStringExtra("mac");
        String _logs = "", TAG="userLogs";
        try {
            _logs = new JSONTask().execute(new Statics().ONE_USER_LOGS, "MAC", mac).get();
            JSONObject jsonObject = new JSONObject(_logs);
            if(jsonObject.getString("success").equals("1")) {
                ListView lv = (ListView) findViewById(R.id.user_logs);
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

                ListAdapter adapter = new ListAdapter(userLogs.this, list, "SAD_Kisi");

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
