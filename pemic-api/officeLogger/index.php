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
	    	if ($now>$row['Time_Enter']) {
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
	while($zrow=mysqli_fetch_array($qr)){
		$row=array();
		$row['MAC']=$zrow['MAC'];
		$row['L_Counter']=$zrow['L_Counter'];
		$oldlist[]=$row;
	}
	$oldlistdiff=array();
	foreach ($oldlist as $x => $value) {
		array_push($oldlistdiff, $value['MAC']);
	}
	
    //////////---------Lists---------------\\\\\\\\\\\\\\
	 
	/////////-----------------------------\\\\\\\\\\\\\\\\
	/////////----------DİFF CALCULATİON----\\\\\\\\\\\\\
	$left=array_diff($oldlistdiff, $recentList);
	$came=array_diff($recentList, $oldlistdiff);
	////////-------------------------------\\\\\\\\\\\\\\\\
	$status=status();
	$leftLength=count($left);
	$cameLength=count($came);
	echo "LEFT <br>";
	foreach($left as $x => $value) {
    	echo $value.'<br>';
    	$sql4="SELECT L_Counter FROM onlineusers WHERE MAC='$value'";
    	$res=mysqli_query($conn,$sql4);
    	$counter=mysqli_fetch_array($res)['L_Counter'];
    	$counter++;
    	$sql3="UPDATE onlineusers SET L_Counter='$counter' WHERE MAC='$value'";
    	mysqli_query($conn,$sql3);
    	if ($counter==3) {
    		$sql="INSERT INTO logs (MAC,Action,Time,Disc) VALUES ('$value','0','".date("Y-m-d H:i:s")."','$status')";
			$sql2="DELETE FROM onlineusers WHERE MAC='$value'";
    		mysqli_query($conn,$sql);
    		mysqli_query($conn,$sql2);
    	}
    }
	echo "CAME <br>";
	foreach($came as $x => $value) {
    	echo $value.'<br>';
    	$sql="INSERT INTO logs (MAC,Action,Time,Disc) VALUES ('$value','1','".date("Y-m-d H:i:s")."','$status')";
		$sql2="INSERT INTO onlineusers (MAC,L_Counter) VALUES ('$value','0')";
    	mysqli_query($conn,$sql);
    	mysqli_query($conn,$sql2);
    }

    foreach ($recentList as $x => $value) {
    	$sql="SELECT L_Counter FROM onlineusers WHERE MAC='$value'";
    	$res=mysqli_query($conn,$sql);
    	$counter=mysqli_fetch_array($res)['L_Counter'];
    	if ($counter!=0) {
    		$sql2="UPDATE onlineusers SET L_Counter='0' WHERE MAC='$value'";
    		mysqli_query($conn,$sql2);
    	}
    }
    
	
	
	

    
    
    
    

?>
