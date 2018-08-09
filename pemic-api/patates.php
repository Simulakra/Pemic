<?php 
	$fp = fopen('php://input', 'r');
	$recentListj = stream_get_contents($fp);
	$recentList = json_decode($recentListj);
	var_dump($recentList);
	$conn=mysqli_connect("localhost","pemic","lnS74w9?","pemic");
	function status()
    {
    	$conn=mysqli_connect("localhost","pemic","lnS74w9?","pemic");
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