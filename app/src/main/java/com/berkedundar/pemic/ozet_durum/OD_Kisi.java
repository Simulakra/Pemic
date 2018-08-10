package com.berkedundar.pemic.ozet_durum;

public class OD_Kisi {
    private String mac;
    private String nickname;

    public OD_Kisi(String mac, String nickname) {
        this.setMAC(mac);
        setNickname(nickname);
    }

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
