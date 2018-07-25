package com.berkedundar.pemic.backdata;

public class Statics {
    public static int ActiveOffice = -1;
    public static String ActiveIP ="192.168.1.23";
    public static String ActiveDB ="pemic";
    public static String ActiveUser ="root";
    public static String ActivePass ="";

    //public static String BASE_URL = "192.168.1.23";
    private static String MAIN_URL(){ return "http://"+ActiveIP+"/pemic-api"; }
    public static final String PULL_ALL_LOGS = MAIN_URL() + "/PullAllLogs.php";
    public static final String PULL_USER_LOGS = MAIN_URL() + "/PullUserLogs.php";
    public static final String PULL_ALL_USERS = MAIN_URL() + "/PullAllUsers.php";
    public static final String PULL_ALL_NON_USERS = MAIN_URL() + "/PullAllNonUsers.php";
    public static final String ADD_NEW_USER = MAIN_URL() + "/AddNewUser.php";
    public static final String EDIT_ONE_USER = MAIN_URL() + "/EditOneUser.php";
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