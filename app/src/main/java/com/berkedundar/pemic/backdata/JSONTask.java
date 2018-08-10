package com.berkedundar.pemic.backdata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.berkedundar.pemic.MainActivity;


public class JSONTask extends AsyncTask<String, String, String> {

    Context _context;
    public JSONTask(Context context){
        if (context != null) {
            try {
            _context = context;
            ((MainActivity)_context).ShowLoadingImage();
            } catch (Exception e){}
        }
    }

    @Override
    protected String doInBackground(String... params){
        String value="";
        String TAG="JSONTask";
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setReadTimeout(10000);
            con.setConnectTimeout(15000);
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);

            List<NameValuePair> prms = new ArrayList<NameValuePair>();
            for (int i = 1; i < params.length - 1; i += 2) {
                prms.add(new BasicNameValuePair(params[i], params[i + 1]));
            }

            Log.d(TAG, "doInBackground: ActiveOffice: "+Statics.ActiveOffice);
            Log.d(TAG, "doInBackground: ActiveIP: "+Statics.ActiveIP);
            Log.d(TAG, "doInBackground: ActiveDB: "+Statics.ActiveDB);
            Log.d(TAG, "doInBackground: ActiveUser: "+Statics.ActiveUser);
            Log.d(TAG, "doInBackground: ActivePass: "+Statics.ActivePass);

            //Server Bilgileri Göndermek
            prms.add(new BasicNameValuePair("DB_IP", Statics.ActiveIP));
            prms.add(new BasicNameValuePair("DB_Name", Statics.ActiveDB));
            prms.add(new BasicNameValuePair("DB_User", Statics.ActiveUser));
            prms.add(new BasicNameValuePair("DB_Pass", Statics.ActivePass));


            OutputStream os = con.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getQuery(prms));
            writer.flush();
            writer.close();
            os.close();

            con.connect();

            BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
            value = bf.readLine();
            Log.d(TAG, "doInBackground: value: "+value);

        }catch (Exception ex){
            System.out.println(ex);
        }
        try{
            if(_context!=null)
                ((MainActivity)_context).HideLoadingImage();
        }catch (Exception e){}
        return value;
    }

    @Override
    protected void onPostExecute(String s){
        super.onPostExecute(s);
    }

    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException{
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}