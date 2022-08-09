-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Ago 09, 2022 alle 19:57
-- Versione del server: 10.4.24-MariaDB
-- Versione PHP: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sistemaelettoraleingsw`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `candidate`
--

CREATE TABLE `candidate` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `factionId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `candidate`
--

INSERT INTO `candidate` (`id`, `name`, `surname`, `factionId`) VALUES
(1, 'Matteo', 'Salvini', 1),
(2, 'Marco', 'Palla', 1),
(3, 'Gianni', 'Rizzo', 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `credential`
--

CREATE TABLE `credential` (
  `idUser` int(11) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `credential`
--

INSERT INTO `credential` (`idUser`, `email`, `password`) VALUES
(1, 'fm', '3ceddb5912534bc18ea3362253ccb29fafed909c7ba01c7d11ef3c7347830475'),
(3, 'user1', '3ceddb5912534bc18ea3362253ccb29fafed909c7ba01c7d11ef3c7347830475'),
(4, 'user2', '3ceddb5912534bc18ea3362253ccb29fafed909c7ba01c7d11ef3c7347830475');

-- --------------------------------------------------------

--
-- Struttura della tabella `faction`
--

CREATE TABLE `faction` (
  `id` int(11) NOT NULL,
  `name` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `faction`
--

INSERT INTO `faction` (`id`, `name`) VALUES
(1, 'Lega'),
(3, 'Forza Italia');

-- --------------------------------------------------------

--
-- Struttura della tabella `faction_session`
--

CREATE TABLE `faction_session` (
  `idFaction` int(11) NOT NULL,
  `idSession` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `faction_session`
--

INSERT INTO `faction_session` (`idFaction`, `idSession`) VALUES
(1, 9),
(1, 10),
(1, 11),
(1, 12),
(1, 13);

-- --------------------------------------------------------

--
-- Struttura della tabella `session`
--

CREATE TABLE `session` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `isActive` tinyint(1) NOT NULL,
  `idVoteType` int(11) NOT NULL,
  `quorum` int(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `session`
--

INSERT INTO `session` (`id`, `name`, `isActive`, `idVoteType`, `quorum`) VALUES
(9, 'Presidente degli stati uniti', 0, 1, 0),
(10, 'Senato', 1, 2, 0),
(11, 'Consiglieri comunali', 0, 3, 0),
(12, 'Sei favorevole all\'uso di armi per la legittima difesa?', 0, 4, 1),
(13, 'Abrogazione legge contro la consumazione di sostanze stupefacenti leggere', 1, 4, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `cf` varchar(20) NOT NULL,
  `type` int(11) NOT NULL DEFAULT 0 COMMENT '0 -> elettore\r\n1 -> admin'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `user`
--

INSERT INTO `user` (`id`, `name`, `surname`, `cf`, `type`) VALUES
(1, 'Fede', 'Mille', 'Mlfer324fd34lkj', 1),
(3, 'Chiara', 'Lazzi', 'aaabbb', 0),
(4, 'Giuseppe', 'Simone', 'giusesi', 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `vervote`
--

CREATE TABLE `vervote` (
  `id` int(11) NOT NULL,
  `idUser` int(11) NOT NULL,
  `idSession` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `vervote`
--

INSERT INTO `vervote` (`id`, `idUser`, `idSession`) VALUES
(1, 1, 9),
(2, 3, 9),
(3, 3, 10),
(4, 1, 11),
(5, 1, 12),
(6, 1, 10),
(7, 1, 13);

-- --------------------------------------------------------

--
-- Struttura della tabella `vote`
--

CREATE TABLE `vote` (
  `id` int(11) NOT NULL,
  `val` int(11) NOT NULL DEFAULT 1,
  `candidateId` int(11) DEFAULT NULL,
  `idSession` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `vote`
--

INSERT INTO `vote` (`id`, `val`, `candidateId`, `idSession`) VALUES
(1, 2, 2, 9),
(2, 1, 3, 9),
(3, 0, 1, 9),
(4, 2, 1, 9),
(5, 1, 3, 9),
(6, 0, 2, 9),
(7, 1, 3, 10),
(8, 1, 2, 11),
(9, 1, 3, 11),
(11, 1, NULL, 12),
(12, 1, 2, 10),
(13, 1, NULL, 13);

-- --------------------------------------------------------

--
-- Struttura della tabella `votetype`
--

CREATE TABLE `votetype` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `referendum` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `votetype`
--

INSERT INTO `votetype` (`id`, `name`, `referendum`) VALUES
(1, 'votoOrdinale', 0),
(2, 'votoCategorico', 0),
(3, 'votoCategoricoPref', 0),
(4, 'referendum', 1);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `candidate`
--
ALTER TABLE `candidate`
  ADD PRIMARY KEY (`id`),
  ADD KEY `Faction_per_candidate` (`factionId`);

--
-- Indici per le tabelle `credential`
--
ALTER TABLE `credential`
  ADD PRIMARY KEY (`email`),
  ADD KEY `idUser` (`idUser`);

--
-- Indici per le tabelle `faction`
--
ALTER TABLE `faction`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `faction_session`
--
ALTER TABLE `faction_session`
  ADD PRIMARY KEY (`idFaction`,`idSession`),
  ADD KEY `idSession` (`idSession`);

--
-- Indici per le tabelle `session`
--
ALTER TABLE `session`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idVoteType` (`idVoteType`);

--
-- Indici per le tabelle `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `vervote`
--
ALTER TABLE `vervote`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idSession` (`idSession`),
  ADD KEY `idUser` (`idUser`);

--
-- Indici per le tabelle `vote`
--
ALTER TABLE `vote`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idSession` (`idSession`),
  ADD KEY `candidate` (`candidateId`);

--
-- Indici per le tabelle `votetype`
--
ALTER TABLE `votetype`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `candidate`
--
ALTER TABLE `candidate`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT per la tabella `faction`
--
ALTER TABLE `faction`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT per la tabella `session`
--
ALTER TABLE `session`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT per la tabella `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT per la tabella `vervote`
--
ALTER TABLE `vervote`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT per la tabella `vote`
--
ALTER TABLE `vote`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT per la tabella `votetype`
--
ALTER TABLE `votetype`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `candidate`
--
ALTER TABLE `candidate`
  ADD CONSTRAINT `Faction_per_candidate` FOREIGN KEY (`factionId`) REFERENCES `faction` (`id`);

--
-- Limiti per la tabella `credential`
--
ALTER TABLE `credential`
  ADD CONSTRAINT `credential_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `user` (`id`);

--
-- Limiti per la tabella `faction_session`
--
ALTER TABLE `faction_session`
  ADD CONSTRAINT `faction_session_ibfk_1` FOREIGN KEY (`idFaction`) REFERENCES `faction` (`id`),
  ADD CONSTRAINT `faction_session_ibfk_2` FOREIGN KEY (`idSession`) REFERENCES `session` (`id`);

--
-- Limiti per la tabella `session`
--
ALTER TABLE `session`
  ADD CONSTRAINT `session_ibfk_1` FOREIGN KEY (`idVoteType`) REFERENCES `votetype` (`id`);

--
-- Limiti per la tabella `vervote`
--
ALTER TABLE `vervote`
  ADD CONSTRAINT `vervote_ibfk_1` FOREIGN KEY (`idSession`) REFERENCES `session` (`id`),
  ADD CONSTRAINT `vervote_ibfk_2` FOREIGN KEY (`idUser`) REFERENCES `user` (`id`);

--
-- Limiti per la tabella `vote`
--
ALTER TABLE `vote`
  ADD CONSTRAINT `candidate` FOREIGN KEY (`candidateId`) REFERENCES `candidate` (`id`),
  ADD CONSTRAINT `vote_ibfk_1` FOREIGN KEY (`idSession`) REFERENCES `session` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
