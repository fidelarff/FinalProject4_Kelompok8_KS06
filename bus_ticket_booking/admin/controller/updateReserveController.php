<?php
    include_once "../config/dbconnect.php";

    if(isset($_POST['upload']))
    {
       
        $bus = $_POST['bus'];
        $r_id = $_POST['r_id'];
        $starting= $_POST['starting'];
        $destination = $_POST['destination'];
        $fare = $_POST['fare'];
      
            
        $updateItem = mysqli_query($conn,"UPDATE busfor_reservation SET
            bus_id=$bus,
            starting_point='$starting', 
            destination='$destination', 
            per_day_price=$fare 
            where r_id=$r_id ") ;
        
         if(!$updateItem)
         {
             echo mysqli_error($conn);
         }
         else
         {
             echo "Records updated successfully.";
         }
     
    }
   


?>