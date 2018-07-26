<?php 
	$conn=mysqli_connect("localhost","alicem","123456","pemic");	

	$oldlistquery="SELECT * FROM onlineusers";
	$oldlist=array();
	$qr=mysqli_query($conn,$oldlistquery);
	while($zrow=mysqli_fetch_array($qr)){
		$row=array();
		$row['mac']=$zrow['MAC'];
		$row['L_Counter']=$zrow['L_Counter'];
		$oldlist[]=$row;
		

	}
	var_dump($oldlist);

	//$process=array(array());
	//$macadress=array();
	//exec('arp -an',$mac);
	//foreach ($mac as $line) {
    //echo "<br>$line <br><br><br>";
    //array_push($process,explode(" ", $line)) ;
    
    //}
    //$processLength=count($process);
    //echo $processLength;
	//for ($row = 1; $row <$processLength; $row++) {
	//  array_push($macadress, $process[$row][3]);
 	//}
 	//var_dump($macadress);
	//fopen("test.xml", "w");
 ?>