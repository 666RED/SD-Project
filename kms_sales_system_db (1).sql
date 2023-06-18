-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 18, 2023 at 02:51 PM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `kms_sales_system_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE DATABASE kms_sales_system_db;

USE kms_sales_system_db;

CREATE TABLE `product` (
  `product_barcode` varchar(255) NOT NULL,
  `product_name` varchar(255) NOT NULL,
  `product_cost` decimal(5,2) NOT NULL,
  `product_price` decimal(5,2) NOT NULL,
  `product_category` varchar(255) NOT NULL,
  `product_quantity` int(11) NOT NULL,
  `product_notification_quantity` int(11) NOT NULL,
  `product_unit` varchar(255) NOT NULL,
  `supplier_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`product_barcode`, `product_name`, `product_cost`, `product_price`, `product_category`, `product_quantity`, `product_notification_quantity`, `product_unit`, `supplier_id`) VALUES
('1029102919292', 'Clothes', '4.00', '6.00', 'Daily Supplies', 45, 5, 'Bootle', 2),
('1111111211111', 'Apple', '3.00', '4.00', 'Food', 33, 2, 'Bootle', 2),
('1231231231231', 'Coke', '1.00', '2.00', 'Beverage', 11, 5, 'Box', 2),
('1231233123444', 'Eclipse', '4.00', '5.00', 'Daily Supplies', 32, 1, 'Box', 2),
('1929129199191', 'Badminton', '12.00', '14.50', 'Daily Supplies', 8, 1, 'Bootle', 3),
('4152524212423', 'cookie', '2.00', '3.30', 'Beverage', 50, 2, 'Box', 2),
('5156565656565', 'Computer', '50.00', '60.00', 'Daily Supplies', 17, 4, 'Bootle', 2),
('5515151511111', 'rubik', '1.00', '2.00', 'Food', 45, 1, 'Bootle', 3),
('5555555555555', 'Nasi Lemak', '2.00', '3.00', 'Food', 10, 2, 'Bootle', 3),
('9102910292929', 'Bag', '2.50', '5.00', 'Daily Supplies', 16, 5, 'Bootle', 2);

-- --------------------------------------------------------

--
-- Table structure for table `product_receipt`
--

CREATE TABLE `product_receipt` (
  `product_barcode` varchar(255) DEFAULT NULL,
  `receipt_id` int(11) DEFAULT NULL,
  `purchased_product_quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product_receipt`
--

INSERT INTO `product_receipt` (`product_barcode`, `receipt_id`, `purchased_product_quantity`) VALUES
('1029102919292', 24, 2),
('1111111211111', 24, 2),
('1231233123444', 24, 3),
('1029102919292', 25, 3),
('1111111211111', 25, 2),
('1231231231231', 25, 3),
('1231233123444', 25, 2),
('5555555555555', 25, 3),
('5515151511111', 25, 3),
('9102910292929', 25, 4),
('1111111211111', 26, 1),
('1029102919292', 26, 1),
('1231231231231', 27, 3),
('1929129199191', 27, 2),
('1029102919292', 27, 2),
('1029102919292', 28, 2),
('1111111211111', 28, 2),
('9102910292929', 28, 1),
('5555555555555', 28, 1),
('1929129199191', 29, 2),
('1029102919292', 29, 2),
('5156565656565', 29, 4),
('9102910292929', 29, 2),
('1929129199191', 30, 2),
('1029102919292', 30, 2),
('1231233123444', 30, 3),
('5156565656565', 30, 2),
('1029102919292', 31, 1),
('1929129199191', 31, 3),
('5555555555555', 31, 1),
('1929129199191', 32, 2),
('1231233123444', 32, 3),
('5156565656565', 32, 1),
('5555555555555', 33, 1),
('5156565656565', 33, 3),
('9102910292929', 34, 3),
('5555555555555', 34, 1),
('1929129199191', 34, 1),
('1029102919292', 35, 1),
('1929129199191', 35, 1),
('1231233123444', 35, 1),
('1029102919292', 36, 1),
('1929129199191', 36, 1),
('1231231231231', 36, 1),
('5555555555555', 36, 1),
('5156565656565', 37, 3),
('5555555555555', 37, 2),
('5515151511111', 37, 2),
('5156565656565', 38, 1),
('5555555555555', 38, 3),
('1111111211111', 38, 1),
('1231231231231', 38, 3),
('1029102919292', 38, 1),
('1029102919292', 39, 2),
('1231231231231', 39, 2),
('1929129199191', 39, 3),
('1231233123444', 39, 3),
('1111111211111', 40, 1),
('1231231231231', 40, 3),
('1029102919292', 40, 3),
('5555555555555', 40, 1),
('1111111211111', 41, 1),
('1231231231231', 41, 3),
('1029102919292', 41, 2),
('9102910292929', 41, 1),
('5555555555555', 42, 1),
('5156565656565', 42, 3),
('9102910292929', 42, 1),
('1929129199191', 42, 1),
('1231231231231', 43, 1),
('1029102919292', 43, 3),
('1929129199191', 43, 1),
('1029102919292', 44, 3),
('1111111211111', 44, 2),
('1231231231231', 44, 2),
('1231233123444', 44, 2),
('1231231231231', 45, 1),
('1029102919292', 45, 1),
('1929129199191', 45, 3),
('1231233123444', 45, 1),
('5156565656565', 45, 1),
('1029102919292', 46, 1),
('1929129199191', 46, 1),
('1231233123444', 46, 1),
('5555555555555', 46, 1),
('5156565656565', 47, 1),
('1111111211111', 47, 1),
('1029102919292', 47, 1),
('1231233123444', 47, 1),
('5156565656565', 48, 1),
('5555555555555', 48, 1),
('1111111211111', 48, 1),
('1029102919292', 49, 2),
('1929129199191', 49, 2),
('5555555555555', 49, 3),
('9102910292929', 49, 1),
('1231233123444', 50, 2),
('1929129199191', 50, 3),
('1111111211111', 50, 3),
('1029102919292', 50, 2),
('1929129199191', 51, 1),
('1029102919292', 51, 3),
('1111111211111', 51, 1),
('1929129199191', 52, 2),
('1029102919292', 52, 3),
('1231231231231', 52, 2),
('1929129199191', 53, 2),
('1111111211111', 53, 3),
('1231231231231', 53, 3),
('1929129199191', 54, 3),
('1029102919292', 54, 3),
('1231231231231', 54, 2),
('1231233123444', 54, 3),
('5156565656565', 55, 1),
('1111111211111', 55, 1),
('1029102919292', 55, 1),
('1231231231231', 55, 1),
('1029102919292', 56, 1),
('1929129199191', 56, 1),
('1231231231231', 56, 1);

-- --------------------------------------------------------

--
-- Table structure for table `receipt`
--

CREATE TABLE `receipt` (
  `receipt_id` int(11) NOT NULL,
  `receipt_date` date NOT NULL,
  `receipt_time` time NOT NULL,
  `receipt_total` decimal(5,2) NOT NULL,
  `receipt_payment` decimal(5,2) NOT NULL,
  `receipt_change` decimal(5,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `receipt`
--

INSERT INTO `receipt` (`receipt_id`, `receipt_date`, `receipt_time`, `receipt_total`, `receipt_payment`, `receipt_change`) VALUES
(24, '2023-06-18', '14:11:05', '35.00', '35.00', '0.00'),
(25, '2023-06-18', '14:11:41', '77.00', '78.00', '1.00'),
(26, '2023-06-18', '14:19:17', '10.00', '10.00', '0.00'),
(27, '2023-06-01', '15:56:17', '47.00', '50.00', '3.00'),
(28, '2023-06-02', '15:56:44', '28.00', '30.00', '2.00'),
(29, '2023-06-03', '15:57:06', '291.00', '300.00', '9.00'),
(30, '2023-06-04', '15:57:27', '176.00', '200.00', '24.00'),
(31, '2023-06-05', '15:57:43', '52.50', '60.00', '7.50'),
(32, '2023-06-06', '15:58:01', '104.00', '200.00', '96.00'),
(33, '2023-06-07', '15:58:15', '183.00', '200.00', '17.00'),
(34, '2023-06-08', '15:58:32', '32.50', '40.00', '7.50'),
(35, '2023-06-09', '15:58:46', '25.50', '30.00', '4.50'),
(36, '2023-06-10', '15:58:59', '25.50', '30.00', '4.50'),
(37, '2023-06-11', '15:59:14', '190.00', '200.00', '10.00'),
(38, '2023-06-12', '15:59:31', '85.00', '100.00', '15.00'),
(39, '2023-06-13', '15:59:47', '74.50', '80.00', '5.50'),
(40, '2023-06-14', '16:00:21', '31.00', '31.00', '0.00'),
(41, '2023-06-15', '16:00:38', '27.00', '30.00', '3.00'),
(42, '2023-06-16', '16:00:57', '202.50', '250.00', '47.50'),
(43, '2023-06-17', '16:01:25', '34.50', '35.00', '0.50'),
(44, '2023-06-19', '16:05:03', '40.00', '40.00', '0.00'),
(45, '2023-06-20', '16:06:05', '116.50', '120.00', '3.50'),
(46, '2023-06-21', '16:06:36', '28.50', '30.00', '1.50'),
(47, '2023-06-22', '16:06:58', '75.00', '80.00', '5.00'),
(48, '2023-06-23', '16:07:19', '67.00', '80.00', '13.00'),
(49, '2023-06-24', '16:07:48', '55.00', '60.00', '5.00'),
(50, '2023-06-25', '16:08:13', '77.50', '80.00', '2.50'),
(51, '2023-06-26', '16:08:35', '36.50', '40.00', '3.50'),
(52, '2023-06-27', '16:09:13', '51.00', '51.00', '0.00'),
(53, '2023-06-28', '16:09:37', '47.00', '50.00', '3.00'),
(54, '2023-06-29', '16:10:05', '80.50', '90.00', '9.50'),
(55, '2023-06-30', '16:10:27', '72.00', '80.00', '8.00'),
(56, '2023-07-01', '16:10:47', '22.50', '30.00', '7.50');

-- --------------------------------------------------------

--
-- Table structure for table `restock`
--

CREATE TABLE `restock` (
  `invoice_no` int(11) NOT NULL,
  `invoice_placed_date` date NOT NULL,
  `invoice_received_date` date NOT NULL,
  `invoice_restock_fee` decimal(6,2) NOT NULL,
  `supplier_name` varchar(255) NOT NULL,
  `supplier_location` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `restock`
--

INSERT INTO `restock` (`invoice_no`, `invoice_placed_date`, `invoice_received_date`, `invoice_restock_fee`, `supplier_name`, `supplier_location`) VALUES
(1111, '2023-06-05', '2023-06-16', '82.00', 'Ling Chii Sung', 'No 50, Jalan Crystal'),
(1235, '2023-06-01', '2023-06-16', '8.00', 'Ling Chii Sung', 'No 50, Jalan Crystal'),
(2222, '2023-06-01', '2023-06-16', '71.60', 'See Hong Chen', 'No13, Kampung Segenting, Minyak Beku, 83000 Batu Pahat, Johor'),
(9102, '2023-06-06', '2023-06-18', '84.00', 'Ling Chii Sung', 'No 50, Jalan Crystal'),
(9999, '2023-06-01', '2023-06-16', '393.50', 'See Hong Chen', 'No13, Kampung Segenting, Minyak Beku, 83000 Batu Pahat, Johor');

-- --------------------------------------------------------

--
-- Table structure for table `restock_product`
--

CREATE TABLE `restock_product` (
  `product_barcode` varchar(255) NOT NULL,
  `invoice_no` int(11) NOT NULL,
  `restock_product_name` varchar(255) NOT NULL,
  `restock_product_unit` varchar(255) NOT NULL,
  `restock_product_quantity_per_unit` int(11) NOT NULL,
  `restock_product_quantity` int(11) NOT NULL,
  `restock_product_single_price` decimal(5,2) NOT NULL,
  `restock_product_total_price` decimal(5,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `restock_product`
--

INSERT INTO `restock_product` (`product_barcode`, `invoice_no`, `restock_product_name`, `restock_product_unit`, `restock_product_quantity_per_unit`, `restock_product_quantity`, `restock_product_single_price`, `restock_product_total_price`) VALUES
('5555555555555', 1235, 'Nasi Lemak', 'Box', 3, 4, '2.00', '8.00'),
('5555555555555', 1111, 'Nasi Lemak', 'Box', 4, 5, '2.00', '10.00'),
('1929129199191', 1111, 'Badminton', 'Crate', 3, 6, '12.00', '72.00'),
('9102910291027', 2222, 'Calculator', 'Box', 3, 3, '21.00', '63.00'),
('1231231231231', 2222, 'Coke', 'Crate', 2, 2, '1.00', '2.00'),
('1902120912092', 2222, 'Watermelon', 'Bundle', 4, 3, '2.20', '6.60'),
('1111111211111', 9999, 'Apple', 'Carton', 2, 3, '3.00', '9.00'),
('1029102919292', 9999, 'Clothes', 'Box', 2, 3, '4.00', '12.00'),
('1231231231231', 9999, 'Coke', 'Crate', 2, 3, '1.00', '3.00'),
('1231233123444', 9999, 'Eclipse', 'Bundle', 4, 3, '4.00', '12.00'),
('1902120912092', 9999, 'Watermelon', 'Bundle', 2, 3, '2.20', '6.60'),
('5132456125252', 9999, 'Baju', 'Box', 3, 3, '5.00', '15.00'),
('5156565656565', 9999, 'Computer', 'Bundle', 3, 4, '50.00', '200.00'),
('5142152525252', 9999, 'Door', 'Box', 3, 4, '15.00', '60.00'),
('5151515151515', 9999, 'Tissue', 'Crate', 4, 2, '0.20', '0.40'),
('9102910292929', 9999, 'Bag', 'Box', 3, 5, '2.50', '12.50'),
('9102910291027', 9999, 'Calculator', 'Carton', 4, 3, '21.00', '63.00'),
('1929129199191', 9102, 'Badminton', 'Box', 6, 7, '12.00', '84.00');

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `staff_id` int(11) NOT NULL,
  `staff_password` varchar(255) NOT NULL,
  `staff_phone_number` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`staff_id`, `staff_password`, `staff_phone_number`) VALUES
(1, 'qwer', '0111-0789940'),
(2, 'qqqq', '0111-0909940');

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE `supplier` (
  `supplier_id` int(11) NOT NULL,
  `supplier_name` varchar(255) NOT NULL,
  `supplier_phone_number` varchar(255) NOT NULL,
  `supplier_location` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`supplier_id`, `supplier_name`, `supplier_phone_number`, `supplier_location`) VALUES
(1, 'Unknown', 'Unknown', 'Unknown'),
(2, 'See Hong Chen', '0111-0789940', 'No13, Kampung Segenting, Minyak Beku, 83000 Batu Pahat, Johor'),
(3, 'Ling Chii Sung', '012-3456789', 'No 50, Jalan Crystal');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`product_barcode`),
  ADD KEY `supplier_id` (`supplier_id`);

--
-- Indexes for table `product_receipt`
--
ALTER TABLE `product_receipt`
  ADD KEY `receipt_id` (`receipt_id`);

--
-- Indexes for table `receipt`
--
ALTER TABLE `receipt`
  ADD PRIMARY KEY (`receipt_id`);

--
-- Indexes for table `restock`
--
ALTER TABLE `restock`
  ADD PRIMARY KEY (`invoice_no`);

--
-- Indexes for table `restock_product`
--
ALTER TABLE `restock_product`
  ADD KEY `invoice_no` (`invoice_no`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`staff_id`);

--
-- Indexes for table `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`supplier_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `receipt`
--
ALTER TABLE `receipt`
  MODIFY `receipt_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=57;

--
-- AUTO_INCREMENT for table `staff`
--
ALTER TABLE `staff`
  MODIFY `staff_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `supplier`
--
ALTER TABLE `supplier`
  MODIFY `supplier_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `product_ibfk_1` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`supplier_id`);

--
-- Constraints for table `product_receipt`
--
ALTER TABLE `product_receipt`
  ADD CONSTRAINT `product_receipt_ibfk_2` FOREIGN KEY (`receipt_id`) REFERENCES `receipt` (`receipt_id`);

--
-- Constraints for table `restock_product`
--
ALTER TABLE `restock_product`
  ADD CONSTRAINT `restock_product_ibfk_2` FOREIGN KEY (`invoice_no`) REFERENCES `restock` (`invoice_no`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
