<?php
if(isset($_POST['MAC'])){
	$mac=$_POST['MAC']);
	$firma="Tanımsız";
	$myfile = fopen("mac_oui2.txt", "r") or die("Unable to open file!");
	while(!feof($myfile)) {
		$temp =fgets($myfile);
		if(substr($temp, 0, 8)===$mac){
			$firma = substr($temp, 17)."<br>";
			break;
		}
	}
	fclose($myfile);
	echo $firma;
}
else{
	echo "MAC adresi girilmedi";
}
?>