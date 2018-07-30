package com.berkedundar.pemic.su_anki_durum;

import java.util.Set;

public class SAD_Kisi {
    private String id;
    private String mac;
    private String action;
    private String time;
    private String nickname;


    // Yapıcı metodumuzda bilgileri alıyoruz.
    public SAD_Kisi(String id, String mac, String nickname, String action, String time) {
        this.setID(id);
        this.setMAC(mac);
        this.setNickname(nickname);
        this.setAction(action);
        this.setTime(time);
    }

    // Getter setter metodlar

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getMAC() {
        return mac;
    }

    public void setMAC(String mac) {
        this.mac = mac;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
