-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: db
-- Generation Time: Jun 07, 2024 at 06:59 PM
-- Server version: 8.3.0
-- PHP Version: 8.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `fdsd`
--
CREATE DATABASE IF NOT EXISTS `fdsd` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `fdsd`;

-- --------------------------------------------------------

--
-- Table structure for table `Іспит`
--

CREATE TABLE `Іспит` (
  `КодСтудента` bigint UNSIGNED NOT NULL,
  `Дата` date NOT NULL,
  `КодДисципліни` bigint UNSIGNED NOT NULL,
  `Оцінка` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `Іспит`
--

INSERT INTO `Іспит` (`КодСтудента`, `Дата`, `КодДисципліни`, `Оцінка`) VALUES
(1, '2024-06-04', 4, 78),
(1, '2024-06-12', 4, 98),
(1, '2024-06-13', 5, 78),
(2, '2024-06-19', 5, 78);

-- --------------------------------------------------------

--
-- Table structure for table `Дисципліна`
--

CREATE TABLE `Дисципліна` (
  `Код` bigint UNSIGNED NOT NULL,
  `Назва` varchar(50) NOT NULL
) ;

--
-- Dumping data for table `Дисципліна`
--

INSERT INTO `Дисципліна` (`Код`, `Назва`) VALUES
(5, 'Математика'),
(4, 'Програмування'),
(6, 'Фізика');

-- --------------------------------------------------------

--
-- Table structure for table `Замовлення`
--

CREATE TABLE `Замовлення` (
  `Номер` bigint UNSIGNED NOT NULL,
  `Дата` date NOT NULL,
  `ІдКлієнта` bigint UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `Замовлення`
--

INSERT INTO `Замовлення` (`Номер`, `Дата`, `ІдКлієнта`) VALUES
(2, '2024-06-06', 1);

--
-- Triggers `Замовлення`
--
DELIMITER $$
CREATE TRIGGER `UpdateOrderCountBeforeDelete` BEFORE DELETE ON `Замовлення` FOR EACH ROW BEGIN
    UPDATE Клієнт
    SET КількістьЗамовлень = КількістьЗамовлень - 1
    WHERE Ід = OLD.ІдКлієнта;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `Клієнт`
--

CREATE TABLE `Клієнт` (
  `Ід` bigint UNSIGNED NOT NULL,
  `Назва` varchar(255) NOT NULL,
  `КількістьЗамовлень` int DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `Клієнт`
--

INSERT INTO `Клієнт` (`Ід`, `Назва`, `КількістьЗамовлень`) VALUES
(1, 'Серг', 1);

-- --------------------------------------------------------

--
-- Table structure for table `Студент`
--

CREATE TABLE `Студент` (
  `Код` bigint UNSIGNED NOT NULL,
  `Прізвище` varchar(255) NOT NULL,
  `Група` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `Студент`
--

INSERT INTO `Студент` (`Код`, `Прізвище`, `Група`) VALUES
(1, 'Гончаров', 'АП989'),
(2, 'Локтев', 'Вп987');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Іспит`
--
ALTER TABLE `Іспит`
  ADD PRIMARY KEY (`КодСтудента`,`Дата`,`КодДисципліни`),
  ADD KEY `КодДисципліни` (`КодДисципліни`);

--
-- Indexes for table `Дисципліна`
--
ALTER TABLE `Дисципліна`
  ADD PRIMARY KEY (`Код`),
  ADD UNIQUE KEY `Назва` (`Назва`);

--
-- Indexes for table `Замовлення`
--
ALTER TABLE `Замовлення`
  ADD PRIMARY KEY (`Номер`),
  ADD KEY `ІдКлієнта` (`ІдКлієнта`);

--
-- Indexes for table `Клієнт`
--
ALTER TABLE `Клієнт`
  ADD PRIMARY KEY (`Ід`);

--
-- Indexes for table `Студент`
--
ALTER TABLE `Студент`
  ADD PRIMARY KEY (`Код`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Іспит`
--
ALTER TABLE `Іспит`
  MODIFY `КодСтудента` bigint UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `Дисципліна`
--
ALTER TABLE `Дисципліна`
  MODIFY `Код` bigint UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `Замовлення`
--
ALTER TABLE `Замовлення`
  MODIFY `Номер` bigint UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `Клієнт`
--
ALTER TABLE `Клієнт`
  MODIFY `Ід` bigint UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `Студент`
--
ALTER TABLE `Студент`
  MODIFY `Код` bigint UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Іспит`
--
ALTER TABLE `Іспит`
  ADD CONSTRAINT `Іспит_ibfk_1` FOREIGN KEY (`КодСтудента`) REFERENCES `Студент` (`Код`),
  ADD CONSTRAINT `Іспит_ibfk_2` FOREIGN KEY (`КодДисципліни`) REFERENCES `Дисципліна` (`Код`);

--
-- Constraints for table `Замовлення`
--
ALTER TABLE `Замовлення`
  ADD CONSTRAINT `Замовлення_ibfk_1` FOREIGN KEY (`ІдКлієнта`) REFERENCES `Клієнт` (`Ід`);
--
-- Database: `mydb`
--
CREATE DATABASE IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `mydb`;
--
-- Database: `PhotocopyPoint`
--
CREATE DATABASE IF NOT EXISTS `PhotocopyPoint` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `PhotocopyPoint`;

-- --------------------------------------------------------

--
-- Table structure for table `Client`
--

CREATE TABLE `Client` (
  `id` bigint UNSIGNED NOT NULL,
  `station_name` varchar(255) NOT NULL,
  `contact_info_id` bigint UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `Client`
--

INSERT INTO `Client` (`id`, `station_name`, `contact_info_id`) VALUES
(1, 'Київська станція', 1),
(14, 'Київ, вул. Хрещатик, 1', 20),
(15, 'Київ, вул. Хрещатик, 1', 21),
(16, 'Київ, вул. Хрещатик, 1', 22),
(17, 'Київ, вул. Хрещатик, 1', 23),
(18, 'Київ, вул. Хрещатик, 1', 24),
(19, 'Київ, вул. Хрещатик, 1', 25),
(20, 'Київ, вул. Хрещатик, 1', 26),
(21, 'Київ, вул. Хрещатик, 1', 27),
(22, 'Київ, вул. Хрещатик, 1', 28),
(23, 'Київ, вул. Хрещатик, 1', 29),
(24, 'Київ, вул. Хрещатик, 1', 30),
(25, 'Київ, вул. Хрещатик, 1', 31),
(26, 'Київ, вул. Хрещатик, 1', 32),
(27, 'Київ, вул. Хрещатик, 1', 33),
(28, 'Київ, вул. Хрещатик, 1', 34),
(29, 'Київ, вул. Хрещатик, 1', 35),
(30, 'Київ, вул. Хрещатик, 1', 36),
(31, 'Київ, вул. Хрещатик, 1', 37),
(32, 'Київ, вул. Хрещатик, 1', 38),
(33, 'Київ, вул. Хрещатик, 1', 39),
(34, 'Київ, вул. Хрещатик, 1', 40),
(35, 'Київ, вул. Хрещатик, 1', 41),
(36, 'Київ, вул. Хрещатик, 1', 42),
(37, 'Київ, вул. Хрещатик, 1', 43),
(38, 'Київ, вул. Хрещатик, 1', 44),
(39, 'Київ, вул. Хрещатик, 1', 45),
(40, 'Київ, вул. Хрещатик, 1', 46),
(41, 'Київ, вул. Хрещатик, 1', 47),
(42, 'Київ, вул. Хрещатик, 1', 48),
(43, 'Київ, вул. Хрещатик, 1', 49),
(44, 'Київ, вул. Хрещатик, 1', 50),
(45, 'Київ, вул. Хрещатик, 1', 51),
(46, 'Київ, вул. Хрещатик, 1', 52),
(47, 'Київ, вул. Хрещатик, 1', 53),
(48, 'Київ, вул. Хрещатик, 1', 54),
(49, 'Київ, вул. Хрещатик, 1', 55),
(50, 'Київ, вул. Хрещатик, 1', 56),
(51, 'Київ, вул. Хрещатик, 1', 57),
(52, 'Київ, вул. Хрещатик, 1', 58),
(53, 'Київ, вул. Хрещатик, 1', 59),
(54, 'Київ, вул. Хрещатик, 1', 60),
(55, 'Київ, вул. Хрещатик, 1', 61),
(56, 'Київ, вул. Хрещатик, 1', 62),
(57, 'Київ, вул. Хрещатик, 1', 63),
(58, 'Київ, вул. Хрещатик, 1', 64),
(59, 'Київ, вул. Хрещатик, 1', 65),
(60, 'Київ, вул. Хрещатик, 1', 66),
(61, 'Київ, вул. Хрещатик, 1', 67),
(62, 'Київ, вул. Хрещатик, 1', 68),
(63, 'Київ, вул. Хрещатик, 1', 69),
(64, 'Київ, вул. Хрещатик, 1', 70),
(65, 'Київ, вул. Хрещатик, 1', 71),
(66, 'Київ, вул. Хрещатик, 1', 72),
(67, 'Київ, вул. Хрещатик, 1', 73),
(68, 'Київ, вул. Хрещатик, 1', 74),
(69, 'Київ, вул. Хрещатик, 1', 75),
(70, 'Київ, вул. Хрещатик, 1', 76),
(71, 'Київ, вул. Хрещатик, 1', 77),
(72, 'Київ, вул. Хрещатик, 1', 78),
(73, 'Київ, вул. Хрещатик, 1', 79),
(74, 'Київ, вул. Хрещатик, 1', 80),
(75, 'Київ, вул. Хрещатик, 1', 81),
(76, 'Київ, вул. Хрещатик, 1', 82);

-- --------------------------------------------------------

--
-- Table structure for table `Contact_info`
--

CREATE TABLE `Contact_info` (
  `id` bigint UNSIGNED NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `phone_number` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `Contact_info`
--

INSERT INTO `Contact_info` (`id`, `full_name`, `phone_number`) VALUES
(1, 'Іван Іванов', '+380501234567'),
(2, 'Петро Петренко', '+380671234568'),
(3, 'Петро Петреченко', '+380444999321'),
(20, 'ТЕСТДорно ДованоТЕСТ', '+3809998748904'),
(21, 'ТЕСТДорно ДованоТЕСТ', '+3809998748904'),
(22, 'ТЕСТДорно ДованоТЕСТ', '+3809998748904'),
(23, 'ТЕСТДорно ДованоТЕСТ', '+3809998748904'),
(24, 'ТЕСТДорно ДованоТЕСТ', '+3809998748904'),
(25, 'ТЕСТДорно ДованоТЕСТ', '+3809998748904'),
(26, 'ТЕСТДорно ДованоТЕСТ', '+3809998748904'),
(27, 'ТЕСТДорно ДованоТЕСТ', '+3809998748904'),
(28, 'ТЕСТДорно ДованоТЕСТ', '+3809998748904'),
(29, 'ТЕСТДорно ДованоТЕСТ', '+3809998748904'),
(30, 'ТЕСТДорно ДованоТЕСТ', '+3809998748904'),
(31, 'ТЕСТДорно ДованоТЕСТ', '+3809998748904'),
(32, 'Сергій Сергійович Сержі', '+3809844678983'),
(33, 'ТЕСТДорно ДованоТЕСТ', '+3809998748904'),
(34, 'Сергій Сергійович Сержі', '+3809844678983'),
(35, 'ТЕСТДорно ДованоТЕСТ', '+3809998748904'),
(36, 'Сергій Сергійович Сержі', '+3809844678983'),
(37, 'ТЕСТДорно ДованоТЕСТ', '+3809998748904'),
(38, 'Сергій Сергійович Сержі', '+3809844678983'),
(39, 'ТЕСТДорно ДованоТЕСТ', '+3809998748904'),
(40, 'Сергій Сергійович Сержі', '+3809844678983'),
(41, 'ТЕСТДорно ДованоТЕСТ', '+3809998748904'),
(42, 'Сергій Сергійович Сержі', '+3809844678983'),
(43, 'ТЕСТДорно ДованоТЕСТ', '+3809998748904'),
(44, 'Сергій Сергійович Сержі', '+3809844678983'),
(45, 'ТЕСТДорно ДованоТЕСТ', '+3809998748904'),
(46, 'Сергій Сергійович Сержі', '+3809844678983'),
(47, 'ТЕСТДорно ДованоТЕСТ', '+3809998748904'),
(48, 'Сергій Сергійович Сержі', '+3809844678983'),
(49, 'ТЕСТДорно ДованоТЕСТ', '+3809998748904'),
(50, 'Сергій Сергійович Сержі', '+3809844678983'),
(51, 'ТЕСТДорно ДованоТЕСТ', '+3809998748904'),
(52, 'ТЕСТДорно ДованоТЕСТ', '+3809998748904'),
(53, 'Сергій Сергійович Сержі', '+3809844678983'),
(54, 'ТЕСТДорно ДованоТЕСТ', '+3809998748904'),
(55, 'Сергій Сергійович Сержі', '+3809844678983'),
(56, 'ТЕСТДорно ДованоТЕСТ', '+3809998748904'),
(57, 'Сергій Сергійович Сержі', '+3809844678983'),
(58, 'ТЕСТДорно ДованоТЕСТ', '+3809998748904'),
(59, 'Сергій Сергійович Сержі', '+3809844678983'),
(60, 'sd', '+4393899834'),
(61, 'hfffff', '+43543534534'),
(62, 'dsfdsa', '+324924324'),
(63, 'sfad', '+34234234'),
(64, 'dsfa', '+43534534'),
(65, 'dsfa', '+43534534'),
(66, 'sda', '+324823032'),
(67, 'sdfa', '+342343243'),
(68, 'sdfsdf', '+38023432423'),
(69, 'Сергій Малевич Торкенко', '+3804532849283'),
(70, 'іфвфів', '23'),
(71, 'dfsfsdfds', '+3542548763456'),
(72, 'sad', '+4444444444444'),
(73, 'dsga', '+3534543534543'),
(74, 'asd', '2131232131'),
(75, 'sdfdfs', '+432423423423'),
(76, 'ТЕСТДорно ДованоТЕСТ', '+3809998748904'),
(77, 'ТЕСТДорно ДованоТЕСТ', '+3809998748904'),
(78, 'ТЕСТДорно ДованоТЕСТ', '+3809998748904'),
(79, 'івфпфпвіпафпа', '+453243242334'),
(80, 'ТЕСТДорно ДованоТЕСТ', '+3809998748904'),
(81, 'ТЕСТДорно ДованоТЕСТ', '+3809998748904'),
(82, '1', '+38043234523');

-- --------------------------------------------------------

--
-- Table structure for table `Message`
--

CREATE TABLE `Message` (
  `id` bigint UNSIGNED NOT NULL,
  `text` varchar(1000) NOT NULL,
  `create_time` datetime NOT NULL,
  `order_id` bigint UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `Message`
--

INSERT INTO `Message` (`id`, `text`, `create_time`, `order_id`) VALUES
(1, 'Повідомлення для оператора принтера: додаткова інформація', '2024-05-27 11:48:24', 1);

-- --------------------------------------------------------

--
-- Table structure for table `Money`
--

CREATE TABLE `Money` (
  `id` bigint UNSIGNED NOT NULL,
  `count` decimal(19,4) NOT NULL,
  `unit` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `Money`
--

INSERT INTO `Money` (`id`, `count`, `unit`) VALUES
(1, 100.0000, 'UAH'),
(2, 200.0000, 'UAH'),
(3, 200.0000, 'USD'),
(18, 300.0000, 'UAH'),
(19, 300.0000, 'UAH'),
(20, 300.0000, 'UAH'),
(21, 300.0000, 'UAH'),
(22, 300.0000, 'UAH'),
(23, 300.0000, 'UAH'),
(24, 300.0000, 'UAH'),
(25, 300.0000, 'UAH'),
(26, 300.0000, 'UAH'),
(27, 300.0000, 'UAH'),
(28, 300.0000, 'UAH'),
(29, 300.0000, 'UAH'),
(30, 5700.0000, 'UAH'),
(31, 300.0000, 'UAH'),
(32, 5700.0000, 'UAH'),
(33, 300.0000, 'UAH'),
(34, 5700.0000, 'UAH'),
(35, 300.0000, 'UAH'),
(36, 5700.0000, 'UAH'),
(37, 300.0000, 'UAH'),
(38, 5700.0000, 'UAH'),
(39, 300.0000, 'UAH'),
(40, 5700.0000, 'UAH'),
(41, 300.0000, 'UAH'),
(42, 5700.0000, 'UAH'),
(43, 300.0000, 'UAH'),
(44, 5700.0000, 'UAH'),
(45, 300.0000, 'UAH'),
(46, 5700.0000, 'UAH'),
(47, 100.0000, 'UAH'),
(48, 300.0000, 'UAH'),
(49, 5700.0000, 'UAH'),
(50, 300.0000, 'UAH'),
(51, 300.0000, 'UAH'),
(52, 5700.0000, 'UAH'),
(53, 300.0000, 'UAH'),
(54, 5700.0000, 'UAH'),
(55, 300.0000, 'UAH'),
(56, 5700.0000, 'UAH'),
(57, 300.0000, 'UAH'),
(58, 5700.0000, 'UAH'),
(59, 900.0000, 'UAH'),
(60, 17600.0000, 'UAH'),
(61, 1200.0000, 'UAH'),
(62, 1500.0000, 'UAH'),
(63, 1700.0000, 'UAH'),
(64, 1700.0000, 'UAH'),
(65, 1200.0000, 'UAH'),
(66, 800.0000, 'UAH'),
(67, 100200.0000, 'UAH'),
(68, 7600.0000, 'UAH'),
(69, 600.0000, 'UAH'),
(70, 100.0000, 'UAH'),
(71, 200.0000, 'UAH'),
(72, 1400.0000, 'UAH'),
(73, 400.0000, 'UAH'),
(74, 800.0000, 'UAH'),
(75, 300.0000, 'UAH'),
(76, 300.0000, 'UAH'),
(77, 300.0000, 'UAH'),
(78, 400.0000, 'UAH'),
(79, 200.0000, 'UAH'),
(80, 300.0000, 'UAH'),
(81, 300.0000, 'UAH'),
(82, 400.0000, 'UAH'),
(83, 100.0000, 'UAH');

-- --------------------------------------------------------

--
-- Table structure for table `Order`
--

CREATE TABLE `Order` (
  `id` bigint UNSIGNED NOT NULL,
  `money_id` bigint UNSIGNED NOT NULL,
  `client_id` bigint UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `Order`
--

INSERT INTO `Order` (`id`, `money_id`, `client_id`) VALUES
(1, 1, 1),
(14, 18, 14),
(15, 19, 15),
(16, 20, 16),
(17, 21, 17),
(18, 22, 18),
(19, 23, 19),
(20, 24, 20),
(21, 25, 21),
(22, 26, 22),
(23, 27, 23),
(24, 28, 24),
(25, 29, 25),
(26, 30, 26),
(27, 31, 27),
(28, 32, 28),
(29, 33, 29),
(30, 34, 30),
(31, 35, 31),
(32, 36, 32),
(33, 37, 33),
(34, 38, 34),
(35, 39, 35),
(36, 40, 36),
(37, 41, 37),
(38, 42, 38),
(39, 43, 39),
(40, 44, 40),
(41, 45, 41),
(42, 46, 42),
(43, 48, 43),
(44, 49, 44),
(45, 50, 45),
(46, 51, 46),
(47, 52, 47),
(48, 53, 48),
(49, 54, 49),
(50, 55, 50),
(51, 56, 51),
(52, 57, 52),
(53, 58, 53),
(54, 59, 54),
(55, 60, 55),
(56, 61, 56),
(57, 62, 57),
(58, 63, 58),
(59, 64, 59),
(60, 65, 60),
(61, 66, 61),
(62, 67, 62),
(63, 68, 63),
(64, 69, 64),
(65, 70, 65),
(66, 71, 66),
(67, 72, 67),
(68, 73, 68),
(69, 74, 69),
(70, 75, 70),
(71, 76, 71),
(72, 77, 72),
(73, 78, 73),
(74, 80, 74),
(75, 81, 75),
(76, 83, 76);

-- --------------------------------------------------------

--
-- Table structure for table `OrderStaff`
--

CREATE TABLE `OrderStaff` (
  `id` bigint UNSIGNED NOT NULL,
  `order_id` bigint UNSIGNED NOT NULL,
  `staff_id` bigint UNSIGNED NOT NULL,
  `specialization` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `OrderStaff`
--

INSERT INTO `OrderStaff` (`id`, `order_id`, `staff_id`, `specialization`) VALUES
(1, 1, 1, 'Оператор принтера'),
(7, 14, 1, 'Оператор принтеру'),
(8, 15, 1, 'Оператор принтеру'),
(9, 16, 1, 'Оператор принтеру'),
(10, 17, 1, 'Оператор принтеру'),
(11, 18, 1, 'Оператор принтеру'),
(12, 19, 1, 'Оператор принтеру'),
(13, 20, 1, 'Оператор принтеру'),
(14, 21, 1, 'Оператор принтеру'),
(15, 22, 1, 'Оператор принтеру'),
(16, 23, 1, 'Оператор принтеру'),
(17, 24, 1, 'Оператор принтеру'),
(18, 25, 1, 'Оператор принтеру'),
(19, 26, 2, 'Оператор принтеру'),
(20, 27, 1, 'Оператор принтеру'),
(21, 28, 2, 'Оператор принтеру'),
(22, 29, 1, 'Оператор принтеру'),
(23, 30, 2, 'Оператор принтеру'),
(24, 31, 1, 'Оператор принтеру'),
(25, 32, 2, 'Оператор принтеру'),
(26, 33, 1, 'Оператор принтеру'),
(27, 34, 2, 'Оператор принтеру'),
(28, 35, 1, 'Оператор принтеру'),
(29, 36, 2, 'Оператор принтеру'),
(30, 37, 1, 'Оператор принтеру'),
(31, 38, 2, 'Оператор принтеру'),
(32, 39, 1, 'Оператор принтеру'),
(33, 40, 2, 'Оператор принтеру'),
(34, 41, 1, 'Оператор принтеру'),
(35, 42, 2, 'Оператор принтеру'),
(36, 43, 1, 'Оператор принтеру'),
(37, 44, 2, 'Оператор принтеру'),
(38, 46, 1, 'Оператор принтеру'),
(39, 47, 2, 'Оператор принтеру'),
(40, 48, 1, 'Оператор принтеру'),
(41, 49, 2, 'Оператор принтеру'),
(42, 50, 1, 'Оператор принтеру'),
(43, 51, 2, 'Оператор принтеру'),
(44, 52, 1, 'Оператор принтеру'),
(45, 53, 2, 'Оператор принтеру'),
(46, 54, 2, 'Оператор принтеру'),
(47, 55, 2, 'Оператор принтеру'),
(48, 56, 2, 'Оператор принтеру'),
(49, 57, 2, 'Оператор принтеру'),
(50, 58, 2, 'Оператор принтеру'),
(51, 59, 2, 'Оператор принтеру'),
(52, 60, 2, 'Оператор принтеру'),
(53, 61, 2, 'Оператор принтеру'),
(54, 62, 2, 'Оператор принтеру'),
(55, 63, 2, 'Оператор принтеру'),
(56, 64, 2, 'Оператор принтеру'),
(57, 65, 2, 'Оператор принтеру'),
(58, 66, 2, 'Оператор принтеру'),
(59, 67, 2, 'Оператор принтеру'),
(60, 68, 2, 'Оператор принтеру'),
(61, 69, 2, 'Оператор принтеру'),
(62, 70, 1, 'Оператор принтеру'),
(63, 71, 1, 'Оператор принтеру'),
(64, 72, 1, 'Оператор принтеру'),
(65, 73, 2, 'Оператор принтеру'),
(66, 74, 1, 'Оператор принтеру'),
(67, 75, 1, 'Оператор принтеру'),
(68, 76, 2, 'Оператор принтеру');

-- --------------------------------------------------------

--
-- Table structure for table `PickUpStation`
--

CREATE TABLE `PickUpStation` (
  `id` bigint UNSIGNED NOT NULL,
  `address` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `PickUpStation`
--

INSERT INTO `PickUpStation` (`id`, `address`) VALUES
(1, 'Київ, вул. Хрещатик, 1'),
(2, 'Львів, просп. Свободи, 45');

-- --------------------------------------------------------

--
-- Table structure for table `PickUpStationStaff`
--

CREATE TABLE `PickUpStationStaff` (
  `id` bigint UNSIGNED NOT NULL,
  `staff_id` bigint UNSIGNED DEFAULT NULL,
  `pickUpStation_id` bigint UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `PickUpStationStaff`
--

INSERT INTO `PickUpStationStaff` (`id`, `staff_id`, `pickUpStation_id`) VALUES
(1, 1, 1),
(2, 2, 2);

-- --------------------------------------------------------

--
-- Table structure for table `Price`
--

CREATE TABLE `Price` (
  `id` bigint UNSIGNED NOT NULL,
  `money_id` bigint UNSIGNED NOT NULL,
  `create_time` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `Price`
--

INSERT INTO `Price` (`id`, `money_id`, `create_time`) VALUES
(1, 1, '2024-05-27 11:48:24'),
(2, 2, '2024-05-27 11:48:24'),
(5, 47, '2024-05-27 11:48:27');

-- --------------------------------------------------------

--
-- Table structure for table `Staff`
--

CREATE TABLE `Staff` (
  `id` bigint UNSIGNED NOT NULL,
  `address` varchar(255) NOT NULL,
  `contact_info_id` bigint UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `Staff`
--

INSERT INTO `Staff` (`id`, `address`, `contact_info_id`) VALUES
(1, 'Київ, вул. Саксаганського, 12', 2),
(2, 'Одеса, вул. Мала Арнаульдская, 5', 3);

-- --------------------------------------------------------

--
-- Table structure for table `Ticket`
--

CREATE TABLE `Ticket` (
  `id` bigint UNSIGNED NOT NULL,
  `order_id` bigint UNSIGNED NOT NULL,
  `isReady` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `Ticket`
--

INSERT INTO `Ticket` (`id`, `order_id`, `isReady`) VALUES
(1, 1, 0),
(2, 1, 1),
(3, 1, 0),
(4, 1, 0),
(5, 1, 0),
(6, 1, 0),
(7, 1, 0),
(8, 1, 0),
(9, 1, 0),
(10, 1, 0),
(11, 1, 0),
(12, 26, 0),
(13, 1, 0),
(14, 28, 0),
(15, 1, 0),
(16, 30, 0),
(17, 1, 0),
(18, 32, 0),
(19, 1, 0),
(20, 34, 0),
(21, 1, 0),
(22, 36, 0),
(23, 1, 0),
(24, 38, 0),
(25, 1, 0),
(26, 40, 0),
(27, 1, 0),
(28, 42, 0),
(29, 1, 0),
(30, 44, 0),
(31, 1, 0),
(32, 47, 0),
(33, 1, 0),
(34, 49, 0),
(35, 1, 0),
(36, 51, 0),
(37, 1, 0),
(38, 53, 0),
(39, 54, 0),
(40, 55, 0),
(41, 56, 0),
(42, 57, 0),
(43, 58, 0),
(44, 59, 0),
(45, 60, 0),
(46, 61, 0),
(47, 62, 0),
(48, 63, 0),
(49, 64, 0),
(50, 65, 0),
(51, 66, 0),
(52, 67, 0),
(53, 68, 0),
(54, 69, 0),
(55, 1, 0),
(56, 73, 0),
(57, 1, 0),
(58, 1, 0),
(59, 76, 0);

-- --------------------------------------------------------

--
-- Table structure for table `TypeService`
--

CREATE TABLE `TypeService` (
  `id` bigint UNSIGNED NOT NULL,
  `name` varchar(255) NOT NULL,
  `info` varchar(255) NOT NULL,
  `term` time NOT NULL,
  `price_id` bigint UNSIGNED NOT NULL,
  `create_time` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `TypeService`
--

INSERT INTO `TypeService` (`id`, `name`, `info`, `term`, `price_id`, `create_time`) VALUES
(1, 'Друк', 'Чорно-білий друк', '00:01:00', 1, '2024-05-27 11:48:23'),
(2, 'Копіювання', 'Кольорове копіювання', '00:02:30', 2, '2024-05-27 11:48:24'),
(5, 'Друк', 'Чорно-білий друк', '00:01:00', 5, '2024-05-27 11:48:27');

-- --------------------------------------------------------

--
-- Table structure for table `TypeServiceOrder`
--

CREATE TABLE `TypeServiceOrder` (
  `id` bigint UNSIGNED NOT NULL,
  `order_id` bigint UNSIGNED NOT NULL,
  `term` datetime NOT NULL,
  `type_service_id` bigint UNSIGNED NOT NULL,
  `count` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `TypeServiceOrder`
--

INSERT INTO `TypeServiceOrder` (`id`, `order_id`, `term`, `type_service_id`, `count`) VALUES
(1, 1, '2024-05-27 11:49:24', 1, 10),
(8, 14, '2024-05-31 18:34:37', 1, 3),
(9, 15, '2024-05-31 19:24:56', 1, 3),
(10, 16, '2024-05-31 20:00:40', 1, 3),
(11, 17, '2024-06-01 08:02:08', 1, 3),
(12, 18, '2024-06-01 08:02:39', 1, 3),
(13, 19, '2024-06-01 08:05:33', 1, 3),
(14, 20, '2024-06-01 08:06:54', 1, 3),
(15, 21, '2024-06-01 08:09:29', 1, 3),
(16, 22, '2024-06-01 08:11:38', 1, 3),
(17, 23, '2024-06-01 08:12:15', 1, 3),
(18, 24, '2024-06-01 08:12:21', 1, 3),
(19, 25, '2024-06-01 09:16:24', 1, 3),
(20, 26, '2024-06-01 10:10:24', 1, 57),
(21, 27, '2024-06-01 09:17:10', 1, 3),
(22, 28, '2024-06-01 10:11:10', 1, 57),
(23, 29, '2024-06-01 10:01:49', 1, 3),
(24, 30, '2024-06-01 10:55:49', 1, 57),
(25, 31, '2024-06-01 10:11:28', 1, 3),
(26, 32, '2024-06-01 11:05:28', 1, 57),
(27, 33, '2024-06-01 10:14:59', 1, 3),
(28, 34, '2024-06-01 11:08:59', 1, 57),
(29, 35, '2024-06-01 10:18:28', 1, 3),
(30, 36, '2024-06-01 11:12:28', 1, 57),
(31, 37, '2024-06-01 10:19:47', 1, 3),
(32, 38, '2024-06-01 11:13:47', 1, 57),
(33, 39, '2024-06-01 10:26:51', 1, 3),
(34, 40, '2024-06-01 11:20:51', 1, 57),
(35, 41, '2024-06-01 10:48:08', 1, 3),
(36, 42, '2024-06-01 11:42:08', 1, 57),
(37, 43, '2024-06-02 14:32:36', 5, 3),
(38, 44, '2024-06-02 15:26:36', 5, 57),
(39, 46, '2024-06-02 19:43:51', 5, 3),
(40, 47, '2024-06-02 20:37:51', 5, 57),
(41, 48, '2024-06-02 20:15:08', 5, 3),
(42, 49, '2024-06-02 21:09:08', 5, 57),
(43, 50, '2024-06-02 22:08:29', 5, 3),
(44, 51, '2024-06-02 23:02:30', 5, 57),
(45, 52, '2024-06-03 06:07:06', 5, 3),
(46, 53, '2024-06-03 07:01:06', 5, 57),
(47, 54, '2024-06-03 13:10:24', 5, 5),
(48, 54, '2024-06-03 13:10:24', 2, 2),
(49, 55, '2024-06-03 16:43:29', 2, 87),
(50, 55, '2024-06-03 16:43:29', 5, 2),
(51, 56, '2024-06-03 13:19:21', 2, 5),
(52, 56, '2024-06-03 13:19:21', 5, 2),
(53, 57, '2024-06-03 13:23:34', 5, 6),
(54, 57, '2024-06-03 13:23:34', 2, 2),
(55, 57, '2024-06-03 13:23:34', 2, 2),
(56, 57, '2024-06-03 13:23:34', 5, 1),
(57, 58, '2024-06-03 13:28:23', 5, 5),
(58, 58, '2024-06-03 13:28:23', 2, 3),
(59, 58, '2024-06-03 13:28:23', 2, 3),
(60, 59, '2024-06-03 13:28:28', 5, 5),
(61, 59, '2024-06-03 13:28:28', 2, 3),
(62, 59, '2024-06-03 13:28:28', 2, 3),
(63, 60, '2024-06-03 13:28:52', 2, 5),
(64, 60, '2024-06-03 13:28:52', 5, 2),
(65, 61, '2024-06-03 14:45:32', 5, 6),
(66, 61, '2024-06-03 14:45:32', 2, 1),
(67, 62, '2024-06-04 11:59:18', 2, 500),
(68, 62, '2024-06-04 11:59:18', 5, 2),
(69, 63, '2024-06-03 16:45:58', 2, 3),
(70, 63, '2024-06-03 16:45:58', 5, 70),
(71, 64, '2024-06-03 15:36:44', 2, 3),
(72, 65, '2024-06-03 15:52:29', 5, 1),
(73, 66, '2024-06-03 15:55:11', 2, 1),
(74, 67, '2024-06-04 06:42:24', 2, 7),
(75, 68, '2024-06-04 06:34:10', 5, 4),
(76, 69, '2024-06-04 16:45:09', 2, 4),
(77, 70, '2024-06-04 16:57:17', 5, 3),
(78, 71, '2024-06-04 16:57:31', 5, 3),
(79, 72, '2024-06-04 16:57:45', 5, 3),
(80, 73, '2024-06-05 21:30:45', 5, 4),
(81, 74, '2024-06-07 18:39:43', 5, 3),
(82, 75, '2024-06-07 18:39:44', 5, 3),
(83, 76, '2024-06-07 18:40:30', 5, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Client`
--
ALTER TABLE `Client`
  ADD PRIMARY KEY (`id`),
  ADD KEY `contact_info_id` (`contact_info_id`);

--
-- Indexes for table `Contact_info`
--
ALTER TABLE `Contact_info`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `Message`
--
ALTER TABLE `Message`
  ADD PRIMARY KEY (`id`),
  ADD KEY `order_id` (`order_id`);

--
-- Indexes for table `Money`
--
ALTER TABLE `Money`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `Order`
--
ALTER TABLE `Order`
  ADD PRIMARY KEY (`id`),
  ADD KEY `money_id` (`money_id`),
  ADD KEY `client_id` (`client_id`);

--
-- Indexes for table `OrderStaff`
--
ALTER TABLE `OrderStaff`
  ADD PRIMARY KEY (`id`),
  ADD KEY `order_id` (`order_id`),
  ADD KEY `staff_id` (`staff_id`);

--
-- Indexes for table `PickUpStation`
--
ALTER TABLE `PickUpStation`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `PickUpStationStaff`
--
ALTER TABLE `PickUpStationStaff`
  ADD PRIMARY KEY (`id`),
  ADD KEY `staff_id` (`staff_id`),
  ADD KEY `pickUpStation_id` (`pickUpStation_id`);

--
-- Indexes for table `Price`
--
ALTER TABLE `Price`
  ADD PRIMARY KEY (`id`),
  ADD KEY `money_id` (`money_id`);

--
-- Indexes for table `Staff`
--
ALTER TABLE `Staff`
  ADD PRIMARY KEY (`id`),
  ADD KEY `contact_info_id` (`contact_info_id`);

--
-- Indexes for table `Ticket`
--
ALTER TABLE `Ticket`
  ADD PRIMARY KEY (`id`),
  ADD KEY `order_id` (`order_id`);

--
-- Indexes for table `TypeService`
--
ALTER TABLE `TypeService`
  ADD PRIMARY KEY (`id`),
  ADD KEY `price_id` (`price_id`);

--
-- Indexes for table `TypeServiceOrder`
--
ALTER TABLE `TypeServiceOrder`
  ADD PRIMARY KEY (`id`),
  ADD KEY `order_id` (`order_id`),
  ADD KEY `type_service_id` (`type_service_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Client`
--
ALTER TABLE `Client`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=77;

--
-- AUTO_INCREMENT for table `Contact_info`
--
ALTER TABLE `Contact_info`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=83;

--
-- AUTO_INCREMENT for table `Message`
--
ALTER TABLE `Message`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `Money`
--
ALTER TABLE `Money`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=84;

--
-- AUTO_INCREMENT for table `Order`
--
ALTER TABLE `Order`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=77;

--
-- AUTO_INCREMENT for table `OrderStaff`
--
ALTER TABLE `OrderStaff`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=69;

--
-- AUTO_INCREMENT for table `PickUpStation`
--
ALTER TABLE `PickUpStation`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `PickUpStationStaff`
--
ALTER TABLE `PickUpStationStaff`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `Price`
--
ALTER TABLE `Price`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `Staff`
--
ALTER TABLE `Staff`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `Ticket`
--
ALTER TABLE `Ticket`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=60;

--
-- AUTO_INCREMENT for table `TypeService`
--
ALTER TABLE `TypeService`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `TypeServiceOrder`
--
ALTER TABLE `TypeServiceOrder`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=84;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Client`
--
ALTER TABLE `Client`
  ADD CONSTRAINT `Client_ibfk_1` FOREIGN KEY (`contact_info_id`) REFERENCES `Contact_info` (`id`);

--
-- Constraints for table `Message`
--
ALTER TABLE `Message`
  ADD CONSTRAINT `Message_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `Order` (`id`);

--
-- Constraints for table `Order`
--
ALTER TABLE `Order`
  ADD CONSTRAINT `Order_ibfk_1` FOREIGN KEY (`money_id`) REFERENCES `Money` (`id`),
  ADD CONSTRAINT `Order_ibfk_2` FOREIGN KEY (`client_id`) REFERENCES `Client` (`id`);

--
-- Constraints for table `OrderStaff`
--
ALTER TABLE `OrderStaff`
  ADD CONSTRAINT `OrderStaff_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `Order` (`id`),
  ADD CONSTRAINT `OrderStaff_ibfk_2` FOREIGN KEY (`staff_id`) REFERENCES `Staff` (`id`);

--
-- Constraints for table `PickUpStationStaff`
--
ALTER TABLE `PickUpStationStaff`
  ADD CONSTRAINT `PickUpStationStaff_ibfk_1` FOREIGN KEY (`staff_id`) REFERENCES `Staff` (`id`),
  ADD CONSTRAINT `PickUpStationStaff_ibfk_2` FOREIGN KEY (`pickUpStation_id`) REFERENCES `PickUpStation` (`id`);

--
-- Constraints for table `Price`
--
ALTER TABLE `Price`
  ADD CONSTRAINT `Price_ibfk_1` FOREIGN KEY (`money_id`) REFERENCES `Money` (`id`);

--
-- Constraints for table `Staff`
--
ALTER TABLE `Staff`
  ADD CONSTRAINT `Staff_ibfk_1` FOREIGN KEY (`contact_info_id`) REFERENCES `Contact_info` (`id`);

--
-- Constraints for table `Ticket`
--
ALTER TABLE `Ticket`
  ADD CONSTRAINT `Ticket_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `Order` (`id`);

--
-- Constraints for table `TypeService`
--
ALTER TABLE `TypeService`
  ADD CONSTRAINT `TypeService_ibfk_1` FOREIGN KEY (`price_id`) REFERENCES `Price` (`id`);

--
-- Constraints for table `TypeServiceOrder`
--
ALTER TABLE `TypeServiceOrder`
  ADD CONSTRAINT `TypeServiceOrder_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `Order` (`id`),
  ADD CONSTRAINT `TypeServiceOrder_ibfk_2` FOREIGN KEY (`type_service_id`) REFERENCES `TypeService` (`id`);
--
-- Database: `sadas`
--
CREATE DATABASE IF NOT EXISTS `sadas` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `sadas`;

-- --------------------------------------------------------

--
-- Table structure for table `Замовлення`
--

CREATE TABLE `Замовлення` (
  `Номер` bigint UNSIGNED NOT NULL,
  `Дата` date NOT NULL,
  `ІдКлієнта` bigint UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Клієнт`
--

CREATE TABLE `Клієнт` (
  `Ід` bigint UNSIGNED NOT NULL,
  `Назва` varchar(255) NOT NULL,
  `КількістьЗамовлень` int DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `ФізичніКлієнти`
--

CREATE TABLE `ФізичніКлієнти` (
  `Ід` bigint UNSIGNED NOT NULL,
  `ПІБ` varchar(255) NOT NULL,
  `Адреса` varchar(255) NOT NULL,
  `Паспорт` varchar(255) NOT NULL,
  `Філія` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `ЮридичніОсоби`
--

CREATE TABLE `ЮридичніОсоби` (
  `Ід` bigint UNSIGNED NOT NULL,
  `Назва` varchar(255) NOT NULL,
  `Адреса` varchar(255) NOT NULL,
  `Факс` varchar(255) NOT NULL,
  `Філія` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Замовлення`
--
ALTER TABLE `Замовлення`
  ADD PRIMARY KEY (`Номер`),
  ADD KEY `ІдКлієнта` (`ІдКлієнта`);

--
-- Indexes for table `Клієнт`
--
ALTER TABLE `Клієнт`
  ADD PRIMARY KEY (`Ід`);

--
-- Indexes for table `ФізичніКлієнти`
--
ALTER TABLE `ФізичніКлієнти`
  ADD PRIMARY KEY (`Ід`);

--
-- Indexes for table `ЮридичніОсоби`
--
ALTER TABLE `ЮридичніОсоби`
  ADD PRIMARY KEY (`Ід`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Замовлення`
--
ALTER TABLE `Замовлення`
  MODIFY `Номер` bigint UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `Клієнт`
--
ALTER TABLE `Клієнт`
  MODIFY `Ід` bigint UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `ФізичніКлієнти`
--
ALTER TABLE `ФізичніКлієнти`
  MODIFY `Ід` bigint UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `ЮридичніОсоби`
--
ALTER TABLE `ЮридичніОсоби`
  MODIFY `Ід` bigint UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Замовлення`
--
ALTER TABLE `Замовлення`
  ADD CONSTRAINT `Замовлення_ibfk_1` FOREIGN KEY (`ІдКлієнта`) REFERENCES `Клієнт` (`Ід`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
