package com.berkedundar.pemic.backdata;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.berkedundar.pemic.R;
import com.berkedundar.pemic.kisi_tanim.KT_Kisi;
import com.berkedundar.pemic.su_anki_durum.SAD_Kisi;

import java.util.List;

public class ListAdapter extends BaseAdapter {

    private List list;
    LayoutInflater layoutInflater;
    Context context;
    String hangisi;
    View satirView;

    public ListAdapter(Context context, List list, String hangisi) {
        this.context = context;
        // Layout Inflater tanımlanıyor...
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
        this.hangisi = hangisi;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (hangisi == "SAD_Kisi") {
            satirView = layoutInflater.inflate(R.layout.sad_kisi, null);

            TextView tv_mac = (TextView) satirView.findViewById(R.id.tv_mac);
            TextView tv_desc = (TextView) satirView.findViewById(R.id.tv_desc);

            SAD_Kisi sad_kisi = (SAD_Kisi) list.get(position);
            tv_mac.setText(sad_kisi.getMAC());
            String _desc = "Giriş : ";

            if(sad_kisi.getAction().equals("1")){
                _desc = "Çıkış : ";
                ImageView iv=(ImageView)satirView.findViewById(R.id.iv_action);
                iv.setImageResource(R.drawable.ww5);
            }

            tv_desc.setText(_desc + sad_kisi.getTime());
        }
        else if (hangisi == "KT_Kisi"){
            satirView = layoutInflater.inflate(R.layout.kt_kisi, null);

            TextView tv_nick = (TextView) satirView.findViewById(R.id.tv_nick);
            TextView tv_mac = (TextView) satirView.findViewById(R.id.tv_mac);

            KT_Kisi kt_kisi = (KT_Kisi) list.get(position);
            tv_nick.setText(kt_kisi.getNickname());
            tv_mac.setText(kt_kisi.getMAC());
        }
        //else
        return satirView;
    }
}
