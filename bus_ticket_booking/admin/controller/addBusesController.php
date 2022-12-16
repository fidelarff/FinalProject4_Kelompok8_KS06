<?php
    include_once "../config/dbconnect.php";
    
    if(isset($_POST['upload']))
    {
       
        $bus_name = $_POST['p_name'];
        $b_number= $_POST['b_number'];
        $capacity = $_POST['capacity'];
       
       
            
        $name = $_FILES['file']['name'];
        $temp = $_FILES['file']['tmp_name'];
         
        $location="https://pramishathapa.com.np/BusImages/";
        $image=$location.$name;
     
        $target_dir="../../BusImages/";
        $finalImage=$target_dir.$name;

        move_uploaded_file($temp,$finalImage);

         $insert = mysqli_query($conn,"INSERT INTO bus
         (bus_name,bus_number, capacity,bus_image) 
         VALUES ('$bus_name',$b_number,$capacity,'$image')");
 
         if(!$insert)
         {
             echo mysqli_error($conn);
         }
         else
         {
             echo "Records added successfully.";
         }
     
    }
        
?>