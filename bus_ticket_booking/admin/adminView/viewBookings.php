

  <h3>Booking History</h3>
  <table class="table ">
    <thead>
      <tr>
        <th>S.N.</th>
        <th>Booked By</th>
        <th>Contact</th>
        <th>Booked Date</th>
        <th>Booking Status</th>
        <th>Payment Method</th>
        <th>Payment Status</th>
        <th>More Details</th>
      </tr>
    </thead>
    <?php
      include_once "../config/dbconnect.php";
      $sql="SELECT * from bookings, users where bookings.user_id=users.user_id";
      $result=$conn-> query($sql);
      $count=1;
      if ($result-> num_rows > 0){
        while ($row=$result-> fetch_assoc()) {
    ?>
    <tr>
      <td><?=$count?></td>
      <td><?=$row["full_name"]?></td>   
      <td><?=$row["contact_no"]?></td>   
      <td><?=$row["booking_date"]?></td>  
      <?php 
            if($row["booking_status"]==0){
                        
        ?>
            <td><h6 class="btn-danger">Cancelled </h6></td>
        <?php
                    
            }else{
        ?>
            <td><h6 class="btn-success">Booked </h6></td>
    
        <?php
        }
        ?>
      <td><?=$row["pay_method"]?></td>   
      
      <?php
            if($row["pay_status"]==0){
        ?>
            <td><button class="btn btn-danger"  onclick="ChangePay('<?=$row['booking_id']?>')">Unpaid</button></td>
        <?php
                    
        }else if($row["pay_status"]==1){
        ?>
            <td><button class="btn btn-success" onclick="ChangePay('<?=$row['booking_id']?>')">Paid </button></td>
        <?php
            }
        ?>
        <td><a class="btn btn-primary openPopup" data-href="./adminView/viewEachBooking.php?ID=<?=$row['booking_id']?>" href="javascript:void(0);">View</a></td>
        
      <?php
            $count=$count+1;
          }
        }
      ?>
  </table>
  
  
  <!-- Modal -->
<div class="modal fade" id="viewModal" role="dialog">
    <div class="modal-dialog modal-lg">
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          
          <h4 class="modal-title">Booking Details</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        <div class="order-view-modal modal-body">
        
        </div>
      </div><!--/ Modal content-->
    </div><!-- /Modal dialog-->
  </div>
<script>
     //for view order modal  
    $(document).ready(function(){
      $('.openPopup').on('click',function(){
        var dataURL = $(this).attr('data-href');
    
        $('.order-view-modal').load(dataURL,function(){
          $('#viewModal').modal({show:true});
        });
      });
    });
 </script>
  
<!--<th>Schedule Date</th>-->
<!--        <th>Starting Point</th>-->
<!--        <th>Destination</th>-->
<!--        <th>Booked Seats</th>-->
<!--        <th>Price</th>-->


   