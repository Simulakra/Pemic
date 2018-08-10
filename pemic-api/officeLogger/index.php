<?php 
	
	date_default_timezone_set ( "Europe/Istanbul" );
	ini_set('max_execution_time', 300);
///////////------GET ONLİNE USERS LİST-------\\\\\\\\\\\\\\
    $process=array(array());
	$recentList=array();
	exec('FOR /L %I in (1,1,40) DO PING 192.168.1.%I -n 1 -w 500');
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
		$data = $recentList;
	 $data_string = json_encode($data);

	 $url = 'http://cgnwebtasarim.com/Yavrular/Pemic/pemic-api/patates.php';

	 $ch = curl_init($url);
	  curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "POST");
	  curl_setopt($ch, CURLOPT_POSTFIELDS, $data_string);
	  curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	  curl_setopt($ch, CURLOPT_HTTPHEADER, array(
	                     'Content-Type: application/json',
	                 'Content-Length: ' . strlen($data_string))
	   );
	  $result = curl_exec($ch);
	  curl_close($ch);

	  echo $result;
	//////////----------------------------\\\\\\\\\\\\\\\\\\\\\\

?>
