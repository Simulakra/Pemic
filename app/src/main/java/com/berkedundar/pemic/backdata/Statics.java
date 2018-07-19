package com.berkedundar.pemic.backdata;

public class Statics {
    public static int ActiveOffice = -1;
    public static String ActiveIP ="192.168.1.23";
    public static String ActiveDB ="pemic";
    public static String ActiveUser ="root";
    public static String ActivePass ="";

    //public static String BASE_URL = "192.168.1.23";
    private static String MAIN_URL(){ return "http://"+ActiveIP+"/pemic-api"; }
    public static final String SELECT_API = MAIN_URL() + "/select-api.php";
    public static final String INSERT_API = MAIN_URL() + "/insert-api.php";
}

/*
offices TABLE
-ID int
-Name text
-DB_IP
-DB_Name
-DB_User
-DB_Pass
* */