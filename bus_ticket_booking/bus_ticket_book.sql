-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 14, 2022 at 05:02 PM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 8.0.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bus_ticket_book`
--

-- --------------------------------------------------------

--
-- Table structure for table `bookings`
--

CREATE TABLE `bookings` (
  `booking_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `booking_date` date NOT NULL DEFAULT current_timestamp(),
  `booking_status` int(11) NOT NULL DEFAULT 1,
  `pay_method` varchar(100) NOT NULL,
  `pay_status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `bookings`
--

INSERT INTO `bookings` (`booking_id`, `user_id`, `booking_date`, `booking_status`, `pay_method`, `pay_status`) VALUES
(111, 4, '2022-12-14', 1, 'Instant', 1),
(112, 4, '2022-12-14', 1, 'Cash', 0),
(113, 4, '2022-12-14', 1, 'Instant', 1),
(114, 8, '2022-12-14', 1, 'Instant', 1),
(115, 8, '2022-12-14', 1, 'Cash', 0);

-- --------------------------------------------------------

--
-- Table structure for table `booking_details`
--

CREATE TABLE `booking_details` (
  `detail_id` int(11) NOT NULL,
  `booking_id` int(11) NOT NULL,
  `schedule_id` int(11) NOT NULL,
  `no_of_seats` int(11) NOT NULL,
  `price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `booking_details`
--

INSERT INTO `booking_details` (`detail_id`, `booking_id`, `schedule_id`, `no_of_seats`, `price`) VALUES
(111, 111, 18, 1, 90000),
(112, 112, 21, 2, 500000),
(113, 113, 15, 1, 60000),
(114, 114, 17, 3, 75000),
(115, 115, 18, 1, 90000);

-- --------------------------------------------------------

--
-- Table structure for table `bus`
--

CREATE TABLE `bus` (
  `bus_id` int(11) NOT NULL,
  `bus_number` int(11) NOT NULL,
  `bus_name` varchar(255) NOT NULL,
  `capacity` int(11) NOT NULL,
  `bus_image` varchar(255) NOT NULL,
  `isReserved` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `bus`
--

INSERT INTO `bus` (`bus_id`, `bus_number`, `bus_name`, `capacity`, `bus_image`, `isReserved`) VALUES
(1, 892231, 'Rosalia Indah', 40, 'BusImages/Rosalia_Indah.png', 2),
(2, 347687, 'PO Haryanto', 50, 'BusImages/PO_Haryanto.png', 2),
(3, 342434, 'DAMRI', 50, 'BusImages/DAMRI.png', 1),
(4, 678564, 'Bongkotan Jati Utama', 50, 'BusImages/Bongkotan_Jati_Utama.png', 2),
(5, 674364, 'Agra Mas', 50, 'BusImages/Agra_Mas.png', 1),
(6, 456753, 'Harapan Jaya', 50, 'BusImages/Harapan_Jaya.png', 2),
(7, 354635, 'Sinar Jaya', 50, 'BusImages/Sinar_Jaya.png', 1),
(8, 443324, 'Efisiensi', 39, 'BusImages/Efisiensi.png', 2);

-- --------------------------------------------------------

--
-- Table structure for table `busfor_reservation`
--

CREATE TABLE `busfor_reservation` (
  `r_id` int(11) NOT NULL,
  `bus_id` int(11) NOT NULL,
  `starting_point` varchar(150) NOT NULL,
  `destination` varchar(150) NOT NULL,
  `per_day_price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `busfor_reservation`
--

INSERT INTO `busfor_reservation` (`r_id`, `bus_id`, `starting_point`, `destination`, `per_day_price`) VALUES
(1, 5, 'Jakarta', 'Bandung', 60000),
(3, 3, 'Bandung', 'Jakarta', 60000),
(4, 5, 'Jakarta', 'Bekasi', 20000),
(5, 7, 'Bekasi', 'Jakarta', 20000),
(7, 5, 'Jakarta', 'Banten', 90000),
(8, 3, 'Banten', 'Jakarta', 90000),
(9, 7, 'Jakarta', 'Yogyakarta', 250000);

-- --------------------------------------------------------

--
-- Table structure for table `reservedbuses`
--

CREATE TABLE `reservedbuses` (
  `reserved_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `reserved_date` date NOT NULL DEFAULT current_timestamp(),
  `reserved_status` int(11) NOT NULL DEFAULT 1,
  `pay_method` varchar(50) NOT NULL,
  `pay_status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `reservedbuses`
--

INSERT INTO `reservedbuses` (`reserved_id`, `user_id`, `reserved_date`, `reserved_status`, `pay_method`, `pay_status`) VALUES
(29, 4, '2022-12-14', 1, 'Cash', 0),
(30, 4, '2022-12-14', 1, 'Instant', 1),
(31, 8, '2022-12-14', 1, 'Cash', 0),
(32, 8, '2022-12-14', 1, 'Instant', 1);

-- --------------------------------------------------------

--
-- Table structure for table `reserved_details`
--

CREATE TABLE `reserved_details` (
  `detail_id` int(11) NOT NULL,
  `reserved_id` int(11) NOT NULL,
  `r_id` int(11) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `no_of_days` int(11) NOT NULL,
  `price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `reserved_details`
--

INSERT INTO `reserved_details` (`detail_id`, `reserved_id`, `r_id`, `start_date`, `end_date`, `no_of_days`, `price`) VALUES
(29, 29, 1, '2022-12-14', '2022-12-31', 17, 1020000),
(30, 30, 5, '2022-12-23', '2022-12-26', 3, 60000),
(31, 31, 5, '2022-12-30', '2022-12-31', 1, 20000),
(32, 32, 8, '2022-12-28', '2022-12-31', 3, 270000);

-- --------------------------------------------------------

--
-- Table structure for table `travelschedule`
--

CREATE TABLE `travelschedule` (
  `schedule_id` int(11) NOT NULL,
  `bus_id` int(11) NOT NULL,
  `starting_point` varchar(100) NOT NULL,
  `destination` varchar(100) NOT NULL,
  `schedule_date` date NOT NULL,
  `departure_time` varchar(50) NOT NULL,
  `fare_amount` int(11) NOT NULL,
  `available_seats` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `travelschedule`
--

INSERT INTO `travelschedule` (`schedule_id`, `bus_id`, `starting_point`, `destination`, `schedule_date`, `departure_time`, `fare_amount`, `available_seats`) VALUES
(14, 2, 'Jakarta', 'Bandung', '2022-12-15', '08:30 AM', 60000, 30),
(15, 1, 'Bandung', 'Jakarta', '2022-12-16', '07:45 AM', 60000, 38),
(16, 4, 'Jakarta', 'Bekasi', '2022-12-17', '06:00 PM', 25000, 41),
(17, 8, 'Bekasi', 'Jakarta', '2022-12-17', '08:00 AM', 25000, 40),
(18, 6, 'Jakarta', 'Banten', '2022-12-18', '07:30 AM', 90000, 46),
(19, 2, 'Banten', 'Jakarta', '2022-12-18', '06:15 PM', 90000, 29),
(20, 8, 'Jakarta', 'Yogyakarta', '2022-09-19', '04:00 PM', 250000, 20),
(21, 2, 'Yogyakarta', 'Jakarta', '2022-12-19', '08:00 AM', 250000, 7),
(22, 4, 'Jakarta', 'Bekasi', '2022-12-19', '05:00 PM', 25000, 13);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(200) NOT NULL,
  `contact_no` varchar(14) NOT NULL,
  `api_key` varchar(255) NOT NULL,
  `created_at` date NOT NULL DEFAULT current_timestamp(),
  `isAdmin` int(11) NOT NULL DEFAULT 0,
  `profile_pic` varchar(255) NOT NULL DEFAULT 'https://avatars.githubusercontent.com/u/55025382?v=4'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `full_name`, `email`, `password`, `contact_no`, `api_key`, `created_at`, `isAdmin`, `profile_pic`) VALUES
(4, 'Kelompok 8', 'admin@gmail.com', '$2a$10$56a6375566428986fa0beuTw.Ku0RE/U5c2.Leba46wItVLEGogOe', '081286379999', '0dfbd99ab5de3db093463fd857f1c805', '2022-12-14', 1, 'https://avatars.githubusercontent.com/u/55025382?v=4'),
(8, 'Faaris Muda Dwi Nugraha', 'faaris@upi.edu', '$2a$10$430e030ab737fbadec677O5SbYVWdGTqpOcFf0ANlYic1FFVIdl8a', '081286379999', 'f371df8b0954ab9a0aa39e1526415530', '2022-12-14', 0, 'https://avatars.githubusercontent.com/u/55025382?v=4');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bookings`
--
ALTER TABLE `bookings`
  ADD PRIMARY KEY (`booking_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `booking_details`
--
ALTER TABLE `booking_details`
  ADD PRIMARY KEY (`detail_id`),
  ADD KEY `booking_id` (`booking_id`),
  ADD KEY `schedule_id` (`schedule_id`);

--
-- Indexes for table `bus`
--
ALTER TABLE `bus`
  ADD PRIMARY KEY (`bus_id`),
  ADD UNIQUE KEY `bus_number` (`bus_number`);

--
-- Indexes for table `busfor_reservation`
--
ALTER TABLE `busfor_reservation`
  ADD PRIMARY KEY (`r_id`);

--
-- Indexes for table `reservedbuses`
--
ALTER TABLE `reservedbuses`
  ADD PRIMARY KEY (`reserved_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `reserved_details`
--
ALTER TABLE `reserved_details`
  ADD PRIMARY KEY (`detail_id`),
  ADD KEY `reserved_id` (`reserved_id`),
  ADD KEY `r_id` (`r_id`);

--
-- Indexes for table `travelschedule`
--
ALTER TABLE `travelschedule`
  ADD PRIMARY KEY (`schedule_id`),
  ADD UNIQUE KEY `uc_Schedule` (`bus_id`,`starting_point`,`destination`,`schedule_date`,`departure_time`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bookings`
--
ALTER TABLE `bookings`
  MODIFY `booking_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=116;

--
-- AUTO_INCREMENT for table `booking_details`
--
ALTER TABLE `booking_details`
  MODIFY `detail_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=116;

--
-- AUTO_INCREMENT for table `bus`
--
ALTER TABLE `bus`
  MODIFY `bus_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `busfor_reservation`
--
ALTER TABLE `busfor_reservation`
  MODIFY `r_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `reservedbuses`
--
ALTER TABLE `reservedbuses`
  MODIFY `reserved_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT for table `reserved_details`
--
ALTER TABLE `reserved_details`
  MODIFY `detail_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT for table `travelschedule`
--
ALTER TABLE `travelschedule`
  MODIFY `schedule_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bookings`
--
ALTER TABLE `bookings`
  ADD CONSTRAINT `bookings_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `booking_details`
--
ALTER TABLE `booking_details`
  ADD CONSTRAINT `booking_details_ibfk_1` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`booking_id`),
  ADD CONSTRAINT `booking_details_ibfk_2` FOREIGN KEY (`schedule_id`) REFERENCES `travelschedule` (`schedule_id`);

--
-- Constraints for table `reservedbuses`
--
ALTER TABLE `reservedbuses`
  ADD CONSTRAINT `reservedBuses_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `reserved_details`
--
ALTER TABLE `reserved_details`
  ADD CONSTRAINT `reserved_details_ibfk_1` FOREIGN KEY (`reserved_id`) REFERENCES `reservedbuses` (`reserved_id`),
  ADD CONSTRAINT `reserved_details_ibfk_2` FOREIGN KEY (`r_id`) REFERENCES `busfor_reservation` (`r_id`);

--
-- Constraints for table `travelschedule`
--
ALTER TABLE `travelschedule`
  ADD CONSTRAINT `travelschedule_ibfk_1` FOREIGN KEY (`bus_id`) REFERENCES `bus` (`bus_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
