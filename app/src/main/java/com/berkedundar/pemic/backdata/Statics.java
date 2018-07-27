package com.berkedundar.pemic.backdata;

public class Statics {
    public static int ActiveOffice = -1;
    public static String ActiveIP ="";
    public static String ActiveDB ="";
    public static String ActiveUser ="";
    public static String ActivePass ="";

    public static int ShowLogCount = 50;

    //public static String BASE_URL = "192.168.1.23";
    private String MAIN_URL(){ return "http://"+ActiveIP+"/Pemic/pemic-api"; }
    public final String PULL_ALL_LOGS = MAIN_URL() + "/PullAllLogs.php";
    public final String PULL_ONLINE_USERS = MAIN_URL() + "/PullOnlineUsers.php";
    public final String PULL_USER_LOGS = MAIN_URL() + "/PullUserLogs.php";
    public final String PULL_ALL_USERS = MAIN_URL() + "/PullAllUsers.php";
    public final String PULL_ALL_NON_USERS = MAIN_URL() + "/PullAllNonUsers.php";
    public final String ADD_NEW_USER = MAIN_URL() + "/AddNewUser.php";
    public final String EDIT_ONE_USER = MAIN_URL() + "/EditOneUser.php";
    public final String DELETE_ONE_USER = MAIN_URL() + "/DeleteOneUser.php";
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