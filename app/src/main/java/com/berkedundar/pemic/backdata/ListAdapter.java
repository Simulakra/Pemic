package com.berkedundar.pemic.backdata;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.berkedundar.pemic.R;

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


        if (hangisi == "sad_kisi") {
            // Burada inflate işlemi yapılıyor.
            // Tasarımını yaptığımız layout dosyamızı view olarak alıyoruz
            satirView = layoutInflater.inflate(R.layout.sad_kisi, null);

            // Öğelerimizi satirView'dan çağırıyoruz.
            TextView tv_mac = (TextView) satirView.findViewById(R.id.tv_mac);
            TextView tv_desc = (TextView) satirView.findViewById(R.id.tv_desc);

            // Mevcut pozisyon için kisi nesnesi oluşturuluyor.
            SAD_Kisi sad_kisi = (SAD_Kisi) list.get(position);

            // Öğelerimize verilerimizi yüklüyoruz.
            tv_mac.setText(sad_kisi.getMAC());

            String _desc = "Giriş : ";

            if(sad_kisi.getAction().equals("1")){
                _desc = "Çıkış : ";
                ImageView iv=(ImageView)satirView.findViewById(R.id.iv_action);
                iv.setImageResource(R.drawable.ww5);
            }

            tv_desc.setText(_desc + sad_kisi.getTime());

            // Mevcut satır için işlem tamam ve view return ediliyor.
            //return satirView;
        }
        //else if ()
        //else
        return satirView;
    }
}
