<?php
    include_once "../config/dbconnect.php";

    if(isset($_POST['upload']))
    {
       
        $bus = $_POST['bus'];
        $schedule_id = $_POST['schedule_id'];
        $starting= $_POST['starting'];
        $destination = $_POST['destination'];
        $schedule = $_POST['schedule'];
        $departure = $_POST['departure'];
        $fare = $_POST['fare'];
        $available = $_POST['available'];
        $newDate = date("Y-m-d", strtotime($schedule));
        $the_time = date('h:i A', strtotime($departure));
      
            
        $updateItem = mysqli_query($conn,"UPDATE travelschedule SET
            bus_id=$bus,
            starting_point='$starting', 
            destination='$destination', 
            schedule_date='$newDate', 
            departure_time='$departure', 
            fare_amount=$fare, 
            available_seats=$available where schedule_id=$schedule_id ") ;
        
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