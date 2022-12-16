<?php
    include_once "../config/dbconnect.php";
    
    if(isset($_POST['upload']))
    {
       
        $bus = $_POST['bus'];
        $starting= $_POST['starting'];
        $destination = $_POST['destination'];
        $fare = $_POST['fare'];
       

         $insert = mysqli_query($conn,"INSERT INTO busfor_reservation
         (bus_id,starting_point, destination, per_day_price) 
         VALUES ($bus,'$starting','$destination',$fare)");
 
         if(!$insert)
         {
             echo mysqli_error($conn);
         }
         else
         {
             echo "Records added successfully.";
             $update=mysqli_query($conn,"UPDATE bus set isReserved=1 WHERE bus_id=$bus");
         }
     
    }
        
?>