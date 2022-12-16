<?php

/**
 * Class to handle all db operations
 * This class will have CRUD methods for database tables
 *
 * @author Ravi Tamada
 * @link URL Tutorial link
 */
class DbHandler {

    private $conn;

    function __construct() {
        require_once dirname(__FILE__) . '/DbConnect.php';
        // opening db connection
        $db = new DbConnect();
        $this->conn = $db->connect();
    }

    /* ------------- `users` table method ------------------ */

    /**
     * Creating new user
     * @param String $Name User full name
     * @param String $Email User login email id
     * @param String $Password User login password
     */
    public function createUser($full_name, $email, $password, $contact_no) {
        require_once 'PassHash.php'; 
        $response = array();

        // First check if user already existed in db
        if (!$this->isUserExists($email)) {
            // Generating password hash
            $Password_hash = PassHash::hash($password);

            // Generating API key
            $apiKey = $this->generateApiKey();

            // insert query
            $stmt = $this->conn->prepare("INSERT INTO users(full_name, email, password, contact_no, api_key) values(?,?,?,?,?)");
            $stmt->bind_param("sssss", $full_name, $email, $Password_hash, $contact_no, $apiKey);

            $result = $stmt->execute();

            $stmt->close();

            // Check for successful insertion
            if ($result) {
                // User successfully inserted
                return USER_CREATED_SUCCESSFULLY;
            } else {
                // Failed to create user
                return USER_CREATE_FAILED;
            }
        } else {
            // User with same email already existed in the db
            return USER_ALREADY_EXISTED;
        }

        return $response;
    }

   
     /**
     * Checking user login
     * @param String $Email User login email id
     * @param String $Password User login password
     * @return boolean User login status success/fail
     */
    public function checkUserLogin($email, $password) {
        // fetching user by email
        $stmt = $this->conn->prepare("SELECT password FROM users WHERE email = ?");
    
        $stmt->bind_param("s", $email);
    
        $stmt->execute();
    
        $stmt->bind_result($Password_hash);
    
        $stmt->store_result();
    
        if ($stmt->num_rows > 0) {
            // Found user with the email
            // Now verify the password
    
            $stmt->fetch();
    
            $stmt->close();
    
            if (PassHash::check_Password($Password_hash, $password)) {
                // User password is correct
                return TRUE;
            } else {
                // user password is incorrect
                return FALSE;
            }
        } else {
            $stmt->close();
    
            // user not existed with the email
            return FALSE;
        }
    }

   ////////////////
   
   
   
    function getDirectory(){
        $dir = "../../profilePicture/";
    
        $year = date("Y");
        $month = date("m");
        $yearDirectory = $dir . $year;
        $folderDirectory = $dir . $year . "/" . $month ."/";
        if (file_exists($yearDirectory)) {
            if (file_exists($folderDirectory) == false) {
                mkdir($folderDirectory, 0777);
                return $folderDirectory ;
            }
        } else {
            mkdir($yearDirectory, 0777);
            mkdir($folderDirectory, 0777);
            return $folderDirectory ;
        }
        return $folderDirectory ;
    }
   
    public function editUser($UserId, $full_name,$email_id, $phone_number, $fileSize, $fileTmpName, $fileType, $fileExtension , $filename ,$finalImageLink, $has_pp){
        if($has_pp){
        $currentDir = getcwd();
        $errors = [];
        $uploadDirectory ="/". $this->getDirectory();
        $uploadPath = $currentDir . $uploadDirectory . basename($filename);
        $fileExtensions = ['jpeg', 'jpg', 'png', 'JPG', 'PNG', 'JPEG'];
    
        if (!in_array($fileExtension, $fileExtensions)) {
            return 2;
        } elseif ($fileSize > 14256000) {
            return 3;
        } else {
    
            if (file_exists($uploadPath)) {
    
                unlink($uploadPath);
    
            }
    
            $didUpload = move_uploaded_file($fileTmpName, $uploadPath);
            if ($didUpload) {
                $success=$this->updateUserInfo($UserId, $full_name, $email_id, $phone_number, $finalImageLink);
                if($success){
                    return 1;
                    
                }else{
                    return 2;
                    
                }
            } else {
                return 4;
            }
        }
        
        }
        else{
            $success=$this->updateUserInfo($UserId, $full_name, $email_id, $phone_number, $finalImageLink);
            if($success){
                return 1;
                    
            }else{
                return 2;
                    
            }
        }
        
    }


public function updateUserInfo($UserId, $full_name, $email_id, $phone_number, $imageLink){
            
    $stmt = $this->conn->prepare( "UPDATE users set full_name=?,  email=?, contact_no=?, profile_pic=? where user_id=?");
    $stmt->bind_param("ssssi", $full_name, $email_id, $phone_number, $imageLink, $UserId);
    $result = $stmt->execute();
    $stmt->close();      

    if ($result) {
            return TRUE;
        } else {
            return FALSE;
        }
        
    }


////////////////


public function editPass($UserId,$password){

    require_once 'PassHash.php';
    
    $newPassword = PassHash::hash($password);

    $stmt = $this->conn->prepare( "UPDATE users set password = ? where user_id = ?");
    $stmt->bind_param("si", $newPassword, $UserId);
    $result = $stmt->execute();
    $stmt->close();      

    if ($result) {
        return TRUE;
    } else {
        return FALSE;
    }
   
}
   
   //////////////////////////
   
    
  public function getScheduleById($s_id)
    {
        $stmt = $this->conn->prepare("SELECT schedule_id, t.bus_id,bus_image,bus_name,bus_number,capacity,starting_point,destination,schedule_date,departure_time, fare_amount,available_seats FROM travelschedule t, bus b where t.bus_id=b.bus_id AND schedule_id = ?");
        $stmt->bind_param("i", $s_id);
        if ($stmt->execute()) {
            $product = array();
            $stmt->bind_result($id, $bus_id,$bus_image,$bus_name,$bus_number,$capacity, $starting_point, $destination, $schedule_date,$departure_time,$fare_amount, $available_seats);
            $stmt->fetch();
            $stmt->close();
            $product["schedule_id"]=$id;
            $product["bus_id"] = $bus_id;
            $product["bus_image"] = $bus_image;
            $product["bus_name"] = $bus_name;
            $product["bus_number"] = $bus_number;
            $product["capacity"] = $capacity;
            $product["starting_point"] = $starting_point;
            $product["destination"] = $destination;
            $product["schedule_date"] = $schedule_date;
            $product["departure_time"] = $departure_time;
            $product["fare_amount"] = $fare_amount;
            $product["available_seats"] = $available_seats;
         
        return $product;
        } else {
            return NULL;
        }
       
    }
    
    
    public function getAllSchedule()
    {
        $products = array();
        $stmt = $this->conn->prepare("SELECT schedule_id, t.bus_id,bus_image,bus_name,bus_number,capacity,starting_point,destination,schedule_date,departure_time, fare_amount,available_seats FROM travelschedule t, bus b where t.bus_id=b.bus_id");
        if ($stmt->execute()) {
            $stmt->bind_result($id, $bus_id,$bus_image,$bus_name,$bus_number,$capacity, $starting_point, $destination, $schedule_date,$departure_time,$fare_amount, $available_seats);
            while ($stmt->fetch()) {
                $product = array();
                $product["schedule_id"]=$id;
                $product["bus_id"] = $bus_id;
                $product["bus_image"] = $bus_image;
                $product["bus_name"] = $bus_name;
                $product["bus_number"] = $bus_number;
                $product["capacity"] = $capacity;
                $product["starting_point"] = $starting_point;
                $product["destination"] = $destination;
                $product["schedule_date"] = $schedule_date;
                $product["departure_time"] = $departure_time;
                $product["fare_amount"] = $fare_amount;
                $product["available_seats"] = $available_seats;
                array_push($products, $product);
            }
            $stmt->close();    
        } else {
            return NULL;
        }

        return $products;
    }
    
    
    public function bookedBy($UserId,$pay_method,$pay_status,$schedule_id,$no_of_seats,$price){
        
        $stmt = $this->conn->prepare("INSERT INTO bookings (user_id,pay_method,pay_status) values(?,?,?)");
        $stmt->bind_param("isi", $UserId,$pay_method,$pay_status);
        $result = $stmt->execute();
        $stmt->close();
        $last_id = mysqli_insert_id($this->conn);
      
      if($result){
        $stmt2 = $this->conn->prepare("INSERT INTO `booking_details` (`booking_id`, `schedule_id`, `no_of_seats`, `price`) values( ?, ?, ?,?)");
        $stmt2->bind_param("iiii", $last_id,$schedule_id,$no_of_seats,$price);
        $result = $stmt2->execute();
        $stmt2->close();
        $q=$this -> getAvailableScheduleSeats($schedule_id);
        $qty=$q-$no_of_seats;
        $stmt3 = $this->conn->prepare("UPDATE travelschedule SET available_seats=? WHERE schedule_id=?");
        $stmt3->bind_param("ii", $qty, $schedule_id);
        $res = $stmt3->execute();
        $stmt3->close();
      }
      return 1;
      
     }
     
    
   public function getAvailableScheduleSeats($schedule_id) {
        $stmt = $this->conn->prepare("SELECT available_seats FROM travelschedule WHERE schedule_id = ?");
        $stmt->bind_param("i", $schedule_id);
        if ($stmt->execute()) {
            $stmt->bind_result($q);
            $stmt->fetch();
            // TODO
            // $user_id = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $q;
        } else {
            return NULL;
        }
    } 

   
    //show booking history
    public function showBookings($UserId){
        $products = array();
            $stmt = $this->conn->prepare("SELECT booking_date, pay_method, pay_status, booking_status,no_of_seats, price,d.schedule_id,starting_point,destination,schedule_date,departure_time, bus_name,bus_number FROM bookings b, booking_details d, travelschedule t, bus WHERE b.booking_id=d.booking_id AND d.schedule_id=t.schedule_id AND t.bus_id=bus.bus_id AND user_id=?");
            $stmt->bind_param("i", $UserId);
            $stmt->execute();   
            if ($stmt->execute()) {
                $product = array();
                $stmt->bind_result($booking_date,$pay_method,$pay_status,$booking_status,$no_of_seats,$price,$schedule_id,$starting_point,$destination,$schedule_date,$departure_time, $bus_name,$bus_number);
                 while ($stmt->fetch()) {
                     $product = array();
               
                    $product["booking_date"] = $booking_date;
                    $product["booking_status"] = $booking_status;
                    $product["pay_method"] = $pay_method;
                    $product["pay_status"] = $pay_status;
                    $product["no_of_seats"] = $no_of_seats;
                    $product["price"] = $price;
                    $product["d.schedule_id"] = $schedule_id;
                    $product["starting_point"] = $starting_point;
                    $product["destination"] = $destination;
                    $product["schedule_date"] = $schedule_date;
                    $product["departure_time"] = $departure_time;
                    $product["bus_name"] = $bus_name;
                    $product["bus_number"] = $bus_number;
                    array_push($products, $product);
                 }
                  $stmt->close();
            return $products;
            } else {
                return NULL;
            }
        }
        
        
        
        
    public function getBusForReserveById($s_id)
    {
        $stmt = $this->conn->prepare("SELECT r_id, r.bus_id,bus_image,bus_name,bus_number,capacity,starting_point,destination, per_day_price FROM busfor_reservation r, bus b where r.bus_id=b.bus_id AND r_id = ?");
        $stmt->bind_param("i", $s_id);
        if ($stmt->execute()) {
            $product = array();
            $stmt->bind_result($id, $bus_id,$bus_image,$bus_name,$bus_number,$capacity, $starting_point, $destination,$fare_amount);
            $stmt->fetch();
            $stmt->close();
            $product["r_id"]=$id;
            $product["bus_id"] = $bus_id;
            $product["bus_image"] = $bus_image;
            $product["bus_name"] = $bus_name;
            $product["bus_number"] = $bus_number;
            $product["capacity"] = $capacity;
            $product["starting_point"] = $starting_point;
            $product["destination"] = $destination;
            $product["per_day_price"] = $fare_amount;
         
        return $product;
        } else {
            return NULL;
        }
       
    }
    
    
    public function getAllBusForReserve()
    {
        $products = array();
        $stmt = $this->conn->prepare("SELECT r_id, r.bus_id,bus_image,bus_name,bus_number,capacity,starting_point,destination, per_day_price FROM busfor_reservation r, bus b where r.bus_id=b.bus_id");
        if ($stmt->execute()) {
            $stmt->bind_result($id, $bus_id,$bus_image,$bus_name,$bus_number,$capacity, $starting_point, $destination,$fare_amount);
            while ($stmt->fetch()) {
                $product = array();
                $product["r_id"]=$id;
                $product["bus_id"] = $bus_id;
                $product["bus_image"] = $bus_image;
                $product["bus_name"] = $bus_name;
                $product["bus_number"] = $bus_number;
                $product["capacity"] = $capacity;
                $product["starting_point"] = $starting_point;
                $product["destination"] = $destination;
                $product["per_day_price"] = $fare_amount;
                
                array_push($products, $product);
            }
            $stmt->close();    
        } else {
            return NULL;
        }

        return $products;
    }
        
        
        
          
    public function reservedBy($UserId,$pay_method,$pay_status,$r_id,$no_of_days,$start_date,$end_date,$price){
        
        $stmt = $this->conn->prepare("INSERT INTO reservedBuses (user_id,pay_method,pay_status) values(?,?,?)");
        $stmt->bind_param("isi", $UserId,$pay_method,$pay_status);
        $result = $stmt->execute();
        $stmt->close();
        $last_id = mysqli_insert_id($this->conn);
      
      if($result){
        $stmt2 = $this->conn->prepare("INSERT INTO `reserved_details` (`reserved_id`, `r_id`,`start_date`,`end_date`, `no_of_days`, `price`) values( ?, ?, ?,?,?,?)");
        $stmt2->bind_param("iissii", $last_id,$r_id,$start_date,$end_date,$no_of_days,$price);
        $result = $stmt2->execute();
        $stmt2->close();
       
      }
      return 1;
      
     }
        
        
        
     //show reservation history
    public function showReservations($UserId){
        $products = array();
            $stmt = $this->conn->prepare("SELECT reserved_date, pay_method, pay_status, reserved_status,no_of_days, price,d.r_id,starting_point,destination,start_date,end_date, bus_name,bus_number FROM reservedBuses b, reserved_details d, busfor_reservation t, bus WHERE b.reserved_id=d.reserved_id AND d.r_id=t.r_id AND t.bus_id=bus.bus_id AND user_id=?");
            $stmt->bind_param("i", $UserId);
            $stmt->execute();   
            if ($stmt->execute()) {
                $product = array();
                $stmt->bind_result($reserved_date,$pay_method,$pay_status,$reserved_status,$no_of_days,$price,$r_id,$starting_point,$destination,$start_date,$end_date, $bus_name,$bus_number);
                 while ($stmt->fetch()) {
                     $product = array();
               
                    $product["reserved_date"] = $reserved_date;
                    $product["reserved_status"] = $reserved_status;
                    $product["pay_method"] = $pay_method;
                    $product["pay_status"] = $pay_status;
                    $product["no_of_days"] = $no_of_days;
                    $product["price"] = $price;
                    $product["d.r_id"] = $r_id;
                    $product["starting_point"] = $starting_point;
                    $product["destination"] = $destination;
                    $product["start_date"] = $start_date;
                    $product["end_date"] = $end_date;
                    $product["bus_name"] = $bus_name;
                    $product["bus_number"] = $bus_number;
                    array_push($products, $product);
                 }
                  $stmt->close();
            return $products;
            } else {
                return NULL;
            }
        }
        
        
    
    ////////////

    /**
     * Checking for duplicate user by email address
     * @param String $Email email to check in db
     * @return boolean
     */
    private function isUserExists($Email) {
        $stmt = $this->conn->prepare("SELECT user_id from users WHERE email= ?");
        $stmt->bind_param("s", $Email);
        $stmt->execute();
        $stmt->store_result();
        $num_rows = $stmt->num_rows;
        $stmt->close();
        return $num_rows > 0;
    }
    
    /**
     * Fetching user by Email
     * @param String $Email_id User Email id
     */
    public function getUserByEmail($Email) {
        $stmt = $this->conn->prepare("SELECT user_id, full_name, email,contact_no, api_key, created_at, isAdmin,profile_pic  FROM users WHERE email = ?");
        $stmt->bind_param("s", $Email);
        if ($stmt->execute()) {
            // $user = $stmt->get_result()->fetch_assoc();
            $stmt->bind_result($UserId, $Name, $Email, $Phone_No, $apiKey, $created_at,$isAdmin,$profile_pic);
            $stmt->fetch();
            $User = array();
            $User["user_id"] =$UserId;
            $User["full_name"] = $Name;
            $User["email"] = $Email;
            $User["contact_no"] =$Phone_No;
            $User["api_key"] = $apiKey;
            $User["created_at"] = $created_at;
            $User["isAdmin"] = $isAdmin;
             $User["profile_pic"] = $profile_pic;
            $stmt->close();
            return $User;
        } else {
            return NULL;
        }
    }
   
    /**
     * Fetching user api key
     * @param String $user_id user id primary key in user table
     */
    public function getApiKeyById($UserId) {
        $stmt = $this->conn->prepare("SELECT api_key FROM users WHERE user_id = ?");
        $stmt->bind_param("i", $UserId);
        if ($stmt->execute()) {
            // $api_key = $stmt->get_result()->fetch_assoc();
            // TODO
            $stmt->bind_result($apiKey);
            $stmt->close();
            return $apiKey;
        } else {
            return NULL;
        }
    }


    /**
     * Fetching user id by api key
     * @param String $api_key user api key
     */
    public function getUserId($apiKey) {
        $stmt = $this->conn->prepare("SELECT user_id FROM users WHERE api_key = ?");
        $stmt->bind_param("s", $apiKey);
        if ($stmt->execute()) {
            $stmt->bind_result($UserId);
            $stmt->fetch();
            // TODO
            // $user_id = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $UserId;
        } else {
            return NULL;
        }
    }


    /**
     * Validating user api key
     * If the api key is there in db, it is a valid key
     * @param String $api_key user api key
     * @return boolean
     */
    public function isValidApiKeyUser($apiKey) {
        $stmt = $this->conn->prepare("SELECT user_id from users WHERE api_key = ?");
        $stmt->bind_param("s", $apiKey);
        $stmt->execute();
        $stmt->store_result();
        $num_rows = $stmt->num_rows;
        $stmt->close();
        return $num_rows > 0;
    }



    /**
     * Generating random Unique MD5 String for user Api key
     */
    private function generateApiKey() {
        return md5(uniqid(rand(), true));
    }
    

}

?>
