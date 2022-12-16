<?php
    include_once "../config/dbconnect.php";
    
    if(isset($_POST['upload']))
    {
       
        $bus = $_POST['bus'];
        $starting= $_POST['starting'];
        $destination = $_POST['destination'];
        $schedule = $_POST['schedule'];
        $departure = $_POST['departure'];
        $fare = $_POST['fare'];
        $available = $_POST['available'];
        $newDate = date("Y-m-d", strtotime($schedule));
        $the_time = date('h:i A', strtotime($departure));
       

         $insert = mysqli_query($conn,"INSERT INTO travelschedule
         (bus_id,starting_point, destination, schedule_date, departure_time, fare_amount, available_seats) 
         VALUES ($bus,'$starting','$destination','$newDate','$the_time',$fare,$available)");
 
         if(!$insert)
         {
             echo mysqli_error($conn);
         }
         else
         {
             echo "Records added successfully.";
             $update=mysqli_query($conn,"UPDATE bus set isReserved=2 WHERE bus_id=$bus");
         }
     
    }
        
?>