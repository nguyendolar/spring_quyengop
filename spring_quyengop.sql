-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th1 04, 2023 lúc 02:30 PM
-- Phiên bản máy phục vụ: 10.4.11-MariaDB
-- Phiên bản PHP: 7.2.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `spring_quyengop`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `donation`
--

CREATE TABLE `donation` (
  `id` int(11) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end_date` varchar(255) DEFAULT NULL,
  `money` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `organization_name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `start_date` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `donation`
--

INSERT INTO `donation` (`id`, `code`, `created`, `description`, `end_date`, `money`, `name`, `organization_name`, `phone_number`, `start_date`, `status`) VALUES
(1, 'QG001', '2023-01-01', 'Giúp đỡ trẻ em nghèo', '2023-01-04', 100000, 'Giúp đỡ trẻ em nghèo', 'Hội từ thiện', '0123456789', '2023-01-01', 2),
(19, 'CAO1', '2023-01-01', 'Hỗ trợ mùa lũ cho đồng bào miền trung', '2023-01-04', 0, 'Từ thiện lũ lụt', 'Hội hỗ trợ mùa lũ', '0394072568', '2023-01-01', 2);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `role`
--

CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `role_name` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `role`
--

INSERT INTO `role` (`id`, `role_name`) VALUES
(1, 'User'),
(2, 'Admin');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `password` varchar(128) NOT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `created` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`id`, `address`, `email`, `full_name`, `note`, `password`, `phone_number`, `status`, `user_name`, `role_id`, `created`) VALUES
(3, 'Việt Nam', 'admin@gmail.com', 'Quản trị', NULL, 'e10adc3949ba59abbe56e057f20f883e', '0394073759', 1, 'admin', 2, '2021-12-26'),
(10, 'Hà Nội', 'levanquang@gmail.com', 'Lê Văn A', NULL, 'e10adc3949ba59abbe56e057f20f883e', '0394073744', 1, 'levanquang', 1, '2023-01-04');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user_donation`
--

CREATE TABLE `user_donation` (
  `id` int(11) NOT NULL,
  `created` varchar(255) DEFAULT NULL,
  `money` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `donation_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `user_donation`
--

INSERT INTO `user_donation` (`id`, `created`, `money`, `name`, `status`, `donation_id`, `user_id`, `text`) VALUES
(9, '2023-01-04', 100000, 'Lê Văn A', 1, 1, 10, 'ủng hộ'),
(10, '2023-01-04', 200000, 'Lê Văn A', 2, 1, 10, 'ủng hộ');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `donation`
--
ALTER TABLE `donation`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKn82ha3ccdebhokx3a8fgdqeyy` (`role_id`);

--
-- Chỉ mục cho bảng `user_donation`
--
ALTER TABLE `user_donation`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKo6fstupjtrgax6hbbpvnge7w4` (`donation_id`),
  ADD KEY `FKaogyiuu1jj92bp58y9p9e7h5v` (`user_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `donation`
--
ALTER TABLE `donation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT cho bảng `role`
--
ALTER TABLE `role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT cho bảng `user_donation`
--
ALTER TABLE `user_donation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
