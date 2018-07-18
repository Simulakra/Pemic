package com.berkedundar.pemic;

public class Statics {
    public static String BASE_URL = "192.168.1.23";
    private static String MAIN_URL(){ return "http://"+BASE_URL+"/pemic-api"; }
    public static final String SELECT_API = MAIN_URL() + "/select-api.php";
    public static final String INSERT_API = MAIN_URL() + "/insert-api.php";
}
