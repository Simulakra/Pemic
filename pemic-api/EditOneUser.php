<?php
 
$response = array();

define('DB_USER', $_POST['DB_User']); // db user
define('DB_PASSWORD', $_POST['DB_Pass']); // db password (mention your db password here)
define('DB_DATABASE', $_POST['DB_Name']); // database name
define('DB_SERVER', "localhost"); // db server // $_POST['DB_IP']

if (isset($_POST['old_mac'])){
    if (isset($_POST['Nickname'])){

        $db = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE) or die(mysqli_error());

        $nickname=$_POST['Nickname'];

        $result = mysqli_query($db, "UPDATE users SET Nickname='$nickname' 
            WHERE MAC='$old_mac'") or die(mysqli_error());

        if ($result) {
            $response["success"] = 1;
            $response["message"] = "User successfully created.";
     
            echo json_encode($response);
        } else {
            $response["success"] = -1;
            $response["message"] = "Oops! An error occurred. There can be other MAC adress same like yours.";
     
            echo json_encode($response);
        }
    }else {
        $response["success"] = 0;
        $response["message"] = "You don't have any of one required field?";
 
        echo json_encode($response);
    }

}else {
    $response["success"] = 0;
    $response["message"] = "Required where field is missing";
 
    echo json_encode($response);
}
?>