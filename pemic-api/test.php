<?php
 //require_once __DIR__ . '/old/db-config.php';

$response = array();

require_once __DIR__ . '/old/db-config.php';

$db = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE) or die(mysqli_error());

$result = mysqli_query($db, "SELECT * FROM logs ORDER BY Time DESC") or die(mysqli_error());

if (mysqli_num_rows($result) > 0) {

    $response["logs"] = array();
 
    while ($row = mysqli_fetch_array($result)) {

        $log = array();
        $log["id"] = $row["ID"];
        $log["mac"] = $row["MAC"];
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