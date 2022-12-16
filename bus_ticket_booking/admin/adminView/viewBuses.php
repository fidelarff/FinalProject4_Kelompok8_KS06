
<div class="container-fluid pt-1 pb-4">
  
  <h2>All Buses</h2>
  <table class="table ">
    <thead>
      <tr>
        <th>S.N.</th>
        <th>Bus Image</th>
        <th>Bus Name</th>
        <th>Bus Number</th>
        <th>Capacity</th>
        <th colspan="2">Action</th>
      </tr>
    </thead>
    <?php
      include_once "../config/dbconnect.php";
      $sql="SELECT * from bus";
      $result=$conn-> query($sql);
      $count=1;
      if ($result-> num_rows > 0){
        while ($row=$result-> fetch_assoc()) {
    ?>
    <tr>
      <td><?=$count?></td>
      <td><img height='100px' src='../<?=$row["bus_image"]?>'></td>
      <td><?=$row["bus_name"]?></td>
      <td><?=$row["bus_number"]?></td>      
      <td><?=$row["capacity"]?></td>   
      <td><button class="btn btn-primary" style="height:40px" onclick="busEditForm('<?=$row['bus_id']?>')">Edit</button></td>
      <td><button class="btn btn-danger" style="height:40px" onclick="busDelete('<?=$row['bus_id']?>')">Delete</button></td>
      </tr>
      <?php
            $count=$count+1;
          }
        }
      ?>
  </table>

<!-- Trigger the modal with a button -->
  <button type="button" class="btn btn-info" style="height:40px" data-toggle="modal" data-target="#myModal">
    Add Bus
  </button>
  
  <!-- Modal -->
  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title">New Bus</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        <div class="modal-body">
          <form  enctype='multipart/form-data' onsubmit="addBuses()" method="POST">
            <div class="form-group">
              <label for="name">Bus Name:</label>
              <input type="text" class="form-control" id="p_name" required>
            </div>
            
            <div class="form-group">
                <label for="b_number">Bus Number:</label>
                <input type="number" class="form-control" id="b_number" required>
            </div>
            <div class="form-group">
                <label for="capacity">Capacity:</label>
                <input type="number" class="form-control" id="capacity" required>
            </div>
           
            <div class="form-group">
                <label for="file">Choose Image:</label>
                <input type="file" class="form-control-file" id="file">
            </div>
            
            <div class="form-group">
              <button type="submit" class="btn btn-secondary" id="upload" style="height:40px">Add Bus</button>
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
   