<div class="container px-5 mx-5">
    

<?php
    include_once "../config/dbconnect.php";
	$ID=$_POST['record'];
	$qry=mysqli_query($conn, "SELECT * FROM bus WHERE bus_id='$ID'");
	$numberOfRow=mysqli_num_rows($qry);
	if($numberOfRow>0){
		while($row1=mysqli_fetch_array($qry)){
     
?>
<form onsubmit="updateBus()" enctype='multipart/form-data' method="POST">
	<div class="form-group">
      <input type="text" class="form-control" id="bus_id" value="<?=$row1['bus_id']?>" hidden>
    </div>
    <div class="form-group">
      <label for="name">Bus Name:</label>
      <input type="text" class="form-control" id="p_name" value="<?=$row1['bus_name']?>">
    </div>
    <div class="form-group">
      <label for="b_number">Bus Number:</label>
      <input type="number" class="form-control" id="b_number" value="<?=$row1['bus_number']?>">
    </div>
    <div class="form-group">
        <label for="capacity">Capacity:</label>
        <input type="number" class="form-control" id="capacity" value="<?=$row1['capacity']?>">
    </div>
      <div class="form-group ">
         <img class="mb-2" height='150px' src='<?=$row1["bus_image"]?>'>
         <div>
            <label for="file">Choose Image:</label>
            <input type="text" id="existingImage" class="form-control" value="<?=$row1['bus_image']?>" hidden>
            <input type="file" id="newImage" value="">
         </div>
    </div>
   
    <div class="form-group">
      <button type="submit" style="height:40px" class="btn btn-primary">Update Bus Details</button>
    </div>
    <?php
    		}
    	}
    ?>
 </form>
 
 </div>