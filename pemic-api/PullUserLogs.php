<?php
 
$response = array();

define('DB_USER', $_POST['DB_User']); // db user
define('DB_PASSWORD', $_POST['DB_Pass']); // db password (mention your db password here)
define('DB_DATABASE', $_POST['DB_Name']); // database name
define('DB_SERVER', "localhost"); // db server // $_POST['DB_IP']

$db = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE) or die(mysqli_error());

$result = mysqli_query($db, "SELECT logs.ID, users.Nickname, logs.Action, logs.Time 
    FROM logs INNER JOIN users on logs.MAC = users.MAC ORDER BY Time DESC") or die(mysqli_error());

if (mysqli_num_rows($result) > 0) {

    $response["logs"] = array();
 
    while ($row = mysqli_fetch_array($result)) {

        $log = array();
        $log["id"] = $row["ID"];
        $log["nickname"] = $row["Nickname"];
        $log["action"] = $row["Action"];
        $log["time"] = $row["Time"];
 
        array_push($response["logs"], $log);
    }
    $response["success"] = 1;
 
    echo json_encode($response);
} else {
    $response["success"] = 0;
    $response["message"] = "No logs found";
 
    echo json_encode($response);
}
?>