<div class="container px-5 mx-5">
<?php
    include_once "../config/dbconnect.php";
	$ID=$_POST['record'];
	$qry=mysqli_query($conn, "SELECT * FROM travelschedule WHERE schedule_id='$ID'");
	$numberOfRow=mysqli_num_rows($qry);
	if($numberOfRow>0){
		while($row1=mysqli_fetch_array($qry)){
      $busID=$row1["bus_id"];
?>
<form id="update-Items" onsubmit="updateItems()" enctype='multipart/form-data' method="POST">
    <div class="form-group">
      <input type="text" class="form-control" id="schedule_id" value="<?=$row1['schedule_id']?>" hidden>
    </div>
    <div class="form-group">
      <label>Bus Name:</label>
      <select id="bus">
        <?php
          $sql="SELECT * from bus WHERE bus_id='$busID'";
          $result = $conn-> query($sql);
          if ($result-> num_rows > 0){
            while($row = $result-> fetch_assoc()){
              echo"<option value='". $row['bus_id'] ."'>" .$row['bus_name'] ."</option>";
            }
          }
        ?>
        <?php
          $sql="SELECT * from bus WHERE bus_id!='$busID'";
          $result = $conn-> query($sql);
          if ($result-> num_rows > 0){
            while($row = $result-> fetch_assoc()){
              echo"<option value='". $row['bus_id'] ."'>" .$row['bus_name'] ."</option>";
            }
          }
        ?>
      </select>
    </div>
	<div class="form-group">
      <label for="starting">Starting Point:</label>
      <input type="text" class="form-control" id="starting" value="<?=$row1['starting_point']?>" required>
    </div>
    <div class="form-group">
      <label for="destination">Destination:</label>
      <input type="text" class="form-control" id="destination" value="<?=$row1['destination']?>" required>
    </div>
     <div class="form-group">
      <label for="schedule">Schedule Date:</label>
      <input type="date" class="form-control" id="schedule" value="<?=$row1['schedule_date']?>" required>
    </div>
    <div class="form-group">
      <label for="departure">Departure Time:</label>
      <input type="text" class="form-control" id="departure" value="<?=$row1['departure_time'] ?>" required>
    </div>
     <div class="form-group">
      <label for="fare">Fare Amount:</label>
      <input type="number" class="form-control" id="fare" value="<?=$row1['fare_amount']?>" required>
    </div>
    <div class="form-group">
      <label for="available">Available Seats:</label>
      <input type="number" class="form-control" id="available" value="<?=$row1['available_seats']?>" required>
    </div>
    
    <div class="form-group">
      <button type="submit" style="height:40px" id="upload" class="btn btn-primary">Update Schedule</button>
    </div>
    <?php
    		}
    	}
    ?>
</form>

</div>