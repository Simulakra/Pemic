<?php 
	$conn=mysqli_connect("localhost","alicem","123456","pemic");
	date_default_timezone_set ( "Europe/Istanbul" );
	ini_set('max_execution_time', 300);


	function status()
    {
    	$conn=mysqli_connect("localhost","alicem","123456","pemic");
    	date_default_timezone_set ( "Europe/Istanbul" );
    	$sqlgetoffice="SELECT * FROM office";
    	$return=mysqli_query($conn,$sqlgetoffice);
    
    	while ($row=mysqli_fetch_array($return)) {
    	$now=date('G')*60;
    	$now+=date('i');
    	if ($now>$row['time-enter']) {
    		return false;
    	}else{
    		return true;
    	}
    }
    }
	function checkout(){
		$conn=mysqli_connect("localhost","alicem","123456","pemic");
	}
    ///////////------GET ONLİNE USERS LİST-------\\\\\\\\\\\\\\
    $process=array(array());
	$recentList=array();
	exec('FOR /L %I in (1,1,50) DO PING 192.168.1.%I -n 2 -w 500');
	exec('arp -a',$mac);
	
	foreach ($mac as $line) {

	    array_push($process,explode(" ", $line)) ;
	}
    
    $processLength=count($process);
    echo $processLength;
	//var_dump($process);
	for ($row = 1; $row <$processLength; $row++) {
		if($process[$row][17]=="dynamic"){			
			for($i=0;$i<15;$i++){
				if(strlen($process[$row][$i])==17){
				array_push($recentList, $process[$row][$i]);
				}
			}
		}
	}
 	//////////----------------------------\\\\\\\\\\\\\\\\\\\\\\
	$oldlistquery="SELECT * FROM onlineusers";
	$oldlist=array();
	$qr=mysqli_query($conn,$oldlistquery);
	while($row=mysqli_fetch_array($qr)){
		array_push($oldlist,$row['MAC']);
	}
	
    //////////---------Lists---------------\\\\\\\\\\\\\\
	 
	/////////-----------------------------\\\\\\\\\\\\\\\\
	/////////----------DİFF CALCULATİON----\\\\\\\\\\\\\
	$left=array_diff($oldlist, $recentList);
	$came=array_diff($recentList, $oldlist);
	mysqli_query($conn,"DELETE FROM onlineusers");
	////////-------------------------------\\\\\\\\\\\\\\\\
	$status=status();
	$leftLength=count($left);
	$cameLength=count($came);
	echo "LEFT <br>";
	foreach($left as $x => $value) {
    	echo $value.'<br>';
    	$sql="INSERT INTO logs (MAC,Action,Time,Disc) VALUES ('$value','0','".date("Y-m-d H:i:s")."','$status')";
		sql2="DELETE FROM "//burda kaldım
    	mysqli_query($conn,$sql);
    	echo mysqli_error($conn);
   	}
	echo "CAME <br>";
	foreach($came as $x => $value) {
    	echo $value.'<br>';
    	$sql="INSERT INTO logs (MAC,Action,Time,Disc) VALUES ('$value','1','".date("Y-m-d H:i:s")."','$status')";
		$sql2="INSERT INTO onlineusers (MAC) VALUES ('$value')";
    	mysqli_query($conn,$sql);
		mysqli_query($conn,$sql2);
    }
	
	
	

    
    
    
    

?>
