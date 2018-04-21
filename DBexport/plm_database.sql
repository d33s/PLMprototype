-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 21 Kwi 2018, 12:05
-- Wersja serwera: 10.1.28-MariaDB
-- Wersja PHP: 7.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `plm_data_git`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `assemblies`
--

CREATE TABLE `assemblies` (
  `idAssembly` int(11) NOT NULL,
  `assemblyName` varchar(255) COLLATE utf8_polish_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `assemblies`
--

INSERT INTO `assemblies` (`idAssembly`, `assemblyName`) VALUES
(1, 'hydraulic press #1'),
(2, 'electrical engine #1'),
(3, 'hydraulic press #2'),
(4, 'gearbox #1');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `customers`
--

CREATE TABLE `customers` (
  `idCustomer` int(11) NOT NULL,
  `customerName` varchar(255) COLLATE utf8_polish_ci DEFAULT NULL,
  `address` varchar(255) COLLATE utf8_polish_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `customers`
--

INSERT INTO `customers` (`idCustomer`, `customerName`, `address`) VALUES
(1, 'RoboTech', 'Lodz, Piotrkowska 8'),
(2, 'MetalTrans', 'Konstantynów, Zielona 10'),
(3, 'CuttingEdge', 'Aleksandrów, Czerwona 8');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `orders`
--

CREATE TABLE `orders` (
  `idOrder` int(11) NOT NULL,
  `customerID` int(11) DEFAULT NULL,
  `assemblyID` int(11) DEFAULT NULL,
  `orderDate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `orders`
--

INSERT INTO `orders` (`idOrder`, `customerID`, `assemblyID`, `orderDate`) VALUES
(1, 1, 1, '2018-04-21'),
(2, 1, 1, '2018-04-15'),
(3, 2, 2, '2018-04-15'),
(4, 3, 4, '2018-04-15'),
(5, 4, 444, '2018-04-15'),
(6, 5, 5, '2018-04-15'),
(7, 6, 6, '2018-04-15'),
(8, 66, 66, '2018-05-12');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `parts`
--

CREATE TABLE `parts` (
  `idPart` int(10) NOT NULL,
  `partName` varchar(20) COLLATE utf8_polish_ci NOT NULL,
  `assemblyName` varchar(20) COLLATE utf8_polish_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `parts`
--

INSERT INTO `parts` (`idPart`, `partName`, `assemblyName`) VALUES
(1, 'washer M10', NULL),
(2, 'bolt M10x25', NULL),
(3, 'ball bearing 3306', '');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indexes for table `assemblies`
--
ALTER TABLE `assemblies`
  ADD PRIMARY KEY (`idAssembly`);

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`idCustomer`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`idOrder`);

--
-- Indexes for table `parts`
--
ALTER TABLE `parts`
  ADD PRIMARY KEY (`idPart`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT dla tabeli `assemblies`
--
ALTER TABLE `assemblies`
  MODIFY `idAssembly` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT dla tabeli `customers`
--
ALTER TABLE `customers`
  MODIFY `idCustomer` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT dla tabeli `orders`
--
ALTER TABLE `orders`
  MODIFY `idOrder` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT dla tabeli `parts`
--
ALTER TABLE `parts`
  MODIFY `idPart` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
