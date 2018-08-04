package com.berkedundar.pemic.backdata;

import android.app.AlertDialog;

import com.berkedundar.pemic.MainActivity;
import com.berkedundar.pemic.userEdit;
import com.berkedundar.pemic.userLogs;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.berkedundar.pemic.R;
import com.berkedundar.pemic.kisi_tanim.KT_Kisi;
import com.berkedundar.pemic.ozet_durum.OD_Kisi;
import com.berkedundar.pemic.su_anki_durum.SAD_Kisi;
import com.daimajia.swipe.SwipeLayout;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

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
            String nick=sad_kisi.getNickname();
            if(nick.isEmpty()){
                tv_mac.setText("**Tanımsız MAC**\n"+sad_kisi.getMAC());
                tv_mac.setTextSize(11);
                tv_mac.setTextColor(Color.parseColor("#aaaaaa"));
            }
            else{
                tv_mac.setText(sad_kisi.getNickname());
            }
            String _desc = "Giriş : ";

            if(sad_kisi.getAction().equals("0")){
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

            final KT_Kisi kt_kisi = (KT_Kisi) list.get(position);
            tv_nick.setText(kt_kisi.getNickname());
            tv_mac.setText(kt_kisi.getMAC());

            ImageView iv=(ImageView)satirView.findViewById(R.id.iv_people);
            iv.setImageResource(R.drawable.qqq1);

            SwipeLayout swipeLayout =  (SwipeLayout)satirView.findViewById(R.id.od_swipe_kt);

            //set show mode.
            swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

            //add drag edge.(If the BottomView has 'layout_gravity' attribute, this line is unnecessary)
            //swipeLayout.addDrag(SwipeLayout.DragEdge.Top, satirView.findViewById(R.id.bottom_wrapper));
            swipeLayout.setRightSwipeEnabled(false);
            swipeLayout.addDrag(SwipeLayout.DragEdge.Left, satirView.findViewById(R.id.bottom_wrapper));

            swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
                @Override
                public void onClose(SwipeLayout layout) {
                    //when the SurfaceView totally cover the BottomView.
                }

                @Override
                public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                    //you are swiping.
                }

                @Override
                public void onStartOpen(SwipeLayout layout) {

                }

                @Override
                public void onOpen(SwipeLayout layout) {
                    //when the BottomView totally show.
                }

                @Override
                public void onStartClose(SwipeLayout layout) {

                }

                @Override
                public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                    //when user's hand released.
                }
            });
            iv=(ImageView)satirView.findViewById(R.id.iv_settings);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,userLogs.class);
                    intent.putExtra("mac",kt_kisi.getMAC());
                    context.startActivity(intent);
                }
            });
        }

        else if (hangisi == "KT_Kisi_non"){
            satirView = layoutInflater.inflate(R.layout.kt_kisiekle, null);

            TextView tv_nick = (TextView) satirView.findViewById(R.id.tv_nick);
            TextView tv_mac = (TextView) satirView.findViewById(R.id.tv_mac);

            final KT_Kisi kt_kisi = (KT_Kisi) list.get(position);
            tv_nick.setText(kt_kisi.getNickname());
            tv_mac.setText("Son Log: " + kt_kisi.getMAC());

            SwipeLayout swipeLayout =  (SwipeLayout)satirView.findViewById(R.id.od_swipe_kt);

            //set show mode.
            swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

            //add drag edge.(If the BottomView has 'layout_gravity' attribute, this line is unnecessary)
            //swipeLayout.addDrag(SwipeLayout.DragEdge.Top, satirView.findViewById(R.id.bottom_wrapper));

            swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
                @Override
                public void onClose(SwipeLayout layout) {
                    //when the SurfaceView totally cover the BottomView.
                }

                @Override
                public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                    //you are swiping.
                }

                @Override
                public void onStartOpen(SwipeLayout layout) {

                }

                @Override
                public void onOpen(SwipeLayout layout) {
                    //when the BottomView totally show.
                }

                @Override
                public void onStartClose(SwipeLayout layout) {

                }

                @Override
                public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                    //when user's hand released.
                }
            });
            ImageView iv=(ImageView)satirView.findViewById(R.id.iv_logs);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,userLogs.class);
                    intent.putExtra("mac",kt_kisi.getNickname());
                    context.startActivity(intent);
                }
            });
            iv=(ImageView)satirView.findViewById(R.id.iv_addnew);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, userEdit.class);
                    intent.putExtra("mac",kt_kisi.getNickname());
                    context.startActivity(intent);
                }
            });
            iv=(ImageView)satirView.findViewById(R.id.iv_settings);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(context)
                            .setMessage("Bu MAC adresini kişi ekle menüsünde bir daha görmemek istiyorsunuz.\n" +
                                    "Bu adım geri alınamaz!\nEmin misiniz?")
                            .setPositiveButton("MAC Adresini Yoksay", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SQLiteDatabase db= context.openOrCreateDatabase("pemic",MODE_PRIVATE,null);
                                    ContentValues values=new ContentValues();
                                    values.put("MAC",kt_kisi.getNickname());
                                    db.insert("SilentMacs",null,values);
                                    ((MainActivity)context).SetTabActivities();
                                }
                            }).setNeutralButton("İptal",null).create().show();
                }
            });
        }

        else if (hangisi == "OD_Kisi"){
            satirView = layoutInflater.inflate(R.layout.od_kisi, null);


            TextView tv_mac = (TextView) satirView.findViewById(R.id.tv_mac);
            TextView tv_desc = (TextView) satirView.findViewById(R.id.tv_desc);


            final OD_Kisi od_kisi = (OD_Kisi) list.get(position);
            String _nick=od_kisi.getNickname();
            if(_nick.isEmpty()){
                tv_mac.setText(od_kisi.getMAC());
                tv_desc.setText("");
            }
            else{
                tv_mac.setText(od_kisi.getNickname());
                tv_desc.setText(od_kisi.getMAC());
            }

            ImageView iv=(ImageView)satirView.findViewById(R.id.iv_action);
            iv.setImageResource(R.drawable.r33);


            SwipeLayout swipeLayout =  (SwipeLayout)satirView.findViewById(R.id.od_swipe);

            //set show mode.
            swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

            //add drag edge.(If the BottomView has 'layout_gravity' attribute, this line is unnecessary)
            swipeLayout.setRightSwipeEnabled(false);
            swipeLayout.addDrag(SwipeLayout.DragEdge.Left, satirView.findViewById(R.id.bottom_wrapper));

            swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
                @Override
                public void onClose(SwipeLayout layout) {
                    //when the SurfaceView totally cover the BottomView.
                }

                @Override
                public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                    //you are swiping.
                }

                @Override
                public void onStartOpen(SwipeLayout layout) {

                }

                @Override
                public void onOpen(SwipeLayout layout) {
                    //when the BottomView totally show.
                }

                @Override
                public void onStartClose(SwipeLayout layout) {

                }

                @Override
                public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                    //when user's hand released.
                }
            });
            iv=(ImageView)satirView.findViewById(R.id.iv_search);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,userLogs.class);
                    intent.putExtra("mac",od_kisi.getMAC());
                    context.startActivity(intent);
                }
            });
        }
        //else
        return satirView;
    }
}
