<?php
    include_once "../config/dbconnect.php";
if( isset($_POST['bus_id']) ){
    
    $bus_id=$_POST['bus_id'];
    $p_name= $_POST['p_name'];
    $b_number= $_POST['b_number'];
    $capacity= $_POST['capacity'];

    if( isset($_FILES['newImage']) ){
        
        $location="https://pramishathapa.com.np/BusImages/";
        $img = $_FILES['newImage']['name'];
        $tmp = $_FILES['newImage']['tmp_name'];
        $dir = '../../BusImages/';
        $ext = strtolower(pathinfo($img, PATHINFO_EXTENSION));
        $valid_extensions = array('jpeg', 'jpg', 'png', 'gif','webp');
        $image =rand(1000,1000000).".".$ext;
        $final_image=$location. $image;
        if (in_array($ext, $valid_extensions)) {
          
            move_uploaded_file($tmp, $dir.$image);
        }
    }else{
        $final_image=$_POST['existingImage'];
    }
    $updateItem = mysqli_query($conn,"UPDATE bus SET 
        bus_name='$p_name', 
        bus_number=$b_number, 
        capacity=$capacity,
        bus_image='$final_image' 
        WHERE bus_id=$bus_id");


    if($updateItem)
    {
        echo "true";
    }else{
        echo mysqli_error($conn);
    }
}
?>