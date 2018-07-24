package com.berkedundar.pemic.kisi_tanim;

public class KT_Kisi {
    private String mac;
    private String nickname;


    // Yapıcı metodumuzda bilgileri alıyoruz.
    public KT_Kisi(String mac, String nickname) {
        this.setMAC(mac);
        this.setNickname(nickname);
    }

    // Getter setter metodlar
    public String getMAC() {
        return mac;
    }

    public void setMAC(String mac) {
        this.mac = mac;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
