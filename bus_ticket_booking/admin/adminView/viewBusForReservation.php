
<div class="container-fluid pt-1 pb-4">
  
  <h2>Buses For Reservation</h2>
  <table class="table ">
    <thead>
      <tr>
        <th>S.N.</th>
        <th>Bus Image</th>
        <th>Bus Name</th>
        <th>Starting Point</th>
        <th>Destination</th>
        <th>Price Per Day</th>
        <th>Total Seats</th>
        <th colspan="2">Action</th>
      </tr>
    </thead>
    <?php
      include_once "../config/dbconnect.php";
      $sql="SELECT * from bus as b, busfor_reservation as r WHERE b.bus_id=r.bus_id";
      $result=$conn-> query($sql);
      $count=1;
      if ($result-> num_rows > 0){
        while ($row=$result-> fetch_assoc()) {
    ?>
    <tr>
      <td><?=$count?></td>
      <td><img height='100px' src='../<?=$row["bus_image"]?>'></td>
      <td><?=$row["bus_name"]?></td>
      <td><?=$row["starting_point"]?></td>      
      <td><?=$row["destination"]?></td>    
      <td><?=$row["per_day_price"]?></td>     
      <td><?=$row["capacity"]?></td>     
      <td><button class="btn btn-primary" style="height:40px" onclick="reserveEditForm('<?=$row['r_id']?>')">Edit</button></td>
      <td><button class="btn btn-danger" style="height:40px" onclick="reserveDelete('<?=$row['r_id']?>')">Delete</button></td>
      </tr>
      <?php
            $count=$count+1;
          }
        }
      ?>
  </table>

<!-- Trigger the modal with a button -->
  <button type="button" class="btn btn-info " style="height:40px" data-toggle="modal" data-target="#myModal">
    Add Bus For Reservations
  </button>
  
  <!-- Modal -->
  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title">New Bus in Reservation</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        <div class="modal-body">
          <form  enctype='multipart/form-data' onsubmit="addBustoReserve()" method="POST">
              
            <div class="form-group">
              <label>Bus Name:</label>
              <select id="bus" >
                <option disabled selected>Select Bus</option>
                <?php

                  $sql="SELECT * from bus where isReserved!=2";
                  $result = $conn-> query($sql);

                  if ($result-> num_rows > 0){
                    while($row = $result-> fetch_assoc()){
                      echo"<option value='".$row['bus_id']."'>".$row['bus_name'] ."</option>";
                    }
                  }
                ?>
              </select>
            </div>
            <div class="form-group">
              <label for="starting">Starting Point:</label>
              <input type="text" class="form-control" id="starting" required>
            </div>
            <div class="form-group">
              <label for="destination">Destination:</label>
              <input type="text" class="form-control" id="destination" required>
            </div>
             <div class="form-group">
              <label for="fare">Price Per Day:</label>
              <input type="number" class="form-control" id="fare" required>
            </div>
           
            
            <div class="form-group">
              <button type="submit" class="btn btn-primary" id="upload" style="height:40px">Add To Reservation</button>
            </div>
          </form>

        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal" style="height:40px">Close</button>
        </div>
      </div>
      
    </div>
  </div>

  
</div>
   