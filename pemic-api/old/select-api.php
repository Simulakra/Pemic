<?php
 
/*
 * Following code will list all the products
 */
 
// array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '/db-config.php';
 
// connecting to db
$db = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE) or die(mysqli_error());
 
// get all products from products table
$result = mysqli_query($db, "SELECT * FROM logs ORDER BY Time DESC") or die(mysqli_error());
 
// check for empty result
if (mysqli_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["logs"] = array();
 
    while ($row = mysqli_fetch_array($result)) {
        // temp user array
        $log = array();
        $log["mac"] = $row["MAC"];
        $log["action"] = $row["Action"];
        $log["time"] = $row["Time"];
 
        // push single product into final response array
        array_push($response["logs"], $log);
    }
    // success
    $response["success"] = 1;
 
    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No products found";
 
    // echo no users JSON
    echo json_encode($response);
}
?>