<?php

    include_once "../config/dbconnect.php";
    
    $p_id=$_POST['record'];
    $query="DELETE FROM busfor_reservation where r_id='$p_id'";

    $data=mysqli_query($conn,$query);

    if($data){
        echo"Bus reserve Deleted";
    }
    else{
        echo"Not able to delete";
    }
    
?>