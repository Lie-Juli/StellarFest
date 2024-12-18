-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 18, 2024 at 11:16 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `stellarfest`
--

-- --------------------------------------------------------

--
-- Table structure for table `events`
--

DROP TABLE IF EXISTS `events`;
CREATE TABLE `events` (
  `event_id` int(11) NOT NULL,
  `event_name` varchar(100) NOT NULL,
  `event_date` varchar(100) NOT NULL,
  `event_location` varchar(100) NOT NULL,
  `event_description` varchar(250) NOT NULL,
  `organizer_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `events`
--

INSERT INTO `events` (`event_id`, `event_name`, `event_date`, `event_location`, `event_description`, `organizer_id`) VALUES
(1, 'Gaming Event', '2024-12-14', 'Jakarta', 'We Are Gaming Tonight', 2),
(2, 'Sport Event', '2025-03-11', 'Bogor', 'This is A Sport Event', 2),
(3, 'Study Event', '2025-06-18', 'Jakarta', 'Nerd Event HeHe', 2),
(4, 'Crazy Valorant Event', '2024-12-26', 'Jakarta', 'Valorant Pew Pew', 4),
(5, 'Party Event', '2024-12-27', 'Bekasi', 'Hehe', 2),
(6, 'Reading Party', '2025-01-03', 'Jakarta', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum lectus elit, vulputate sed duis.', 2),
(7, 'Car Party', '2024-12-27', 'Tokyo', 'Drifttt', 2),
(8, 'Food King Festival', '2024-12-31', 'Jakarta', 'All kind of food from western, indonesian, chinese and many more', 10);

-- --------------------------------------------------------

--
-- Table structure for table `invitations`
--

DROP TABLE IF EXISTS `invitations`;
CREATE TABLE `invitations` (
  `invitation_id` int(11) NOT NULL,
  `event_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `invitation_status` varchar(100) NOT NULL,
  `invitation_role` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `invitations`
--

INSERT INTO `invitations` (`invitation_id`, `event_id`, `user_id`, `invitation_status`, `invitation_role`) VALUES
(1, 1, 6, 'not accepted', 'vendor'),
(2, 2, 6, 'not accepted', 'vendor'),
(3, 5, 6, 'not accepted', 'vendor'),
(4, 5, 7, 'not accepted', 'vendor'),
(5, 5, 5, 'accepted', 'guest'),
(6, 5, 8, 'accepted', 'guest'),
(7, 3, 8, 'not accepted', 'guest'),
(8, 3, 5, 'accepted', 'guest'),
(9, 7, 8, 'not accepted', 'guest'),
(10, 7, 6, 'not accepted', 'vendor'),
(14, 1, 5, 'not accepted', 'guest'),
(15, 1, 9, 'not accepted', 'guest'),
(16, 1, 7, 'not accepted', 'vendor'),
(17, 2, 5, 'not accepted', 'guest'),
(18, 2, 9, 'not accepted', 'guest'),
(19, 3, 3, 'not accepted', 'vendor'),
(20, 3, 7, 'not accepted', 'vendor'),
(21, 7, 5, 'not accepted', 'guest'),
(22, 6, 6, 'not accepted', 'vendor'),
(23, 7, 7, 'not accepted', 'vendor'),
(30, 8, 5, 'not accepted', 'guest'),
(31, 8, 8, 'not accepted', 'guest'),
(32, 8, 6, 'not accepted', 'vendor');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `email` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `role` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `email`, `username`, `password`, `role`) VALUES
(1, 'admin', 'admin', 'admin', 'admin'),
(2, 'julius@gmail.com', 'julius', 'julius', 'event organizer'),
(3, 'delon@gmail.com', 'delon', 'delon', 'vendor'),
(4, 'william@gmail.com', 'william', 'williams', 'event organizer'),
(5, 'jeremy@gmail.com', 'jeremy', 'jeremy', 'guest'),
(6, 'vendor1', 'vendor1', 'vendor1', 'vendor'),
(7, 'vendor2', 'vendor2', 'vendor2', 'vendor'),
(8, 'guest1', 'guest1', 'guest1', 'guest'),
(9, 'guest2', 'guest2', 'guest2', 'guest'),
(10, 'organizer1@gmail.com', 'eventorganizer1', 'eventorganizer1', 'event organizer');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `events`
--
ALTER TABLE `events`
  ADD PRIMARY KEY (`event_id`),
  ADD KEY `organizer_id` (`organizer_id`);

--
-- Indexes for table `invitations`
--
ALTER TABLE `invitations`
  ADD PRIMARY KEY (`invitation_id`),
  ADD KEY `event_id` (`event_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `events`
--
ALTER TABLE `events`
  MODIFY `event_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `invitations`
--
ALTER TABLE `invitations`
  MODIFY `invitation_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `events`
--
ALTER TABLE `events`
  ADD CONSTRAINT `events_ibfk_1` FOREIGN KEY (`organizer_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `invitations`
--
ALTER TABLE `invitations`
  ADD CONSTRAINT `invitations_ibfk_1` FOREIGN KEY (`event_id`) REFERENCES `events` (`event_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `invitations_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
