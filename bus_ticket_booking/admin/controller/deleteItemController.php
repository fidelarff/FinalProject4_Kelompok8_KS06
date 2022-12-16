<?php

    include_once "../config/dbconnect.php";
    
    $p_id=$_POST['record'];
    $query="DELETE FROM travelschedule where schedule_id='$p_id'";

    $data=mysqli_query($conn,$query);

    if($data){
        echo"Schedule Deleted";
    }
    else{
        echo"Not able to delete";
    }
    
?>