package com.berkedundar.pemic.backdata;

public class SAD_Kisi {
    private String mac;
    private String action;
    private String time;


    // Yapıcı metodumuzda bilgileri alıyoruz.
    public SAD_Kisi(String mac, String action, String time) {
        this.setMAC(mac);
        this.setAction(action);
        this.setTime(time);
    }

    // Getter setter metodlar
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
}
