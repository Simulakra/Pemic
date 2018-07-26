<?php
 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['mac']) && isset($_POST['action']) && isset($_POST['time'])) {
 
    $mac = $_POST['mac'];
    $action = $_POST['action'];
    $time = $_POST['time'];
 
    // include db connect class
    require_once __DIR__ . '/db-config.php';
 
    // connecting to db
    $db =  mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE) or die(mysqli_error());
 
    // mysql inserting a new row
    $result = mysqli_query($db, "INSERT INTO logs(MAC,Action,Time) VALUES('$mac', '$action', '$time')");
 
    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Product successfully created.";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
 
        // echoing JSON response
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
?>