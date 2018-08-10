<?php 
	
		$conn=mysqli_connect("localhost","alicem","123456","pemic");
		$sql="SELECT EXTRACT(DAY FROM Time) FROM logs ORDER BY `logs`.`Time` DESC";
		$res=mysqli_query($conn,$sql);
		$s = mysqli_fetch_array($res);
		echo $s[0];
		if (date('d')!=$s[0]) {
		echo "OLDU";
		}
	
 ?>