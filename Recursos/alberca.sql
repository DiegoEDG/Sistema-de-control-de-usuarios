-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 06-04-2020 a las 21:30:19
-- Versión del servidor: 5.7.26
-- Versión de PHP: 7.3.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `alberca`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
CREATE TABLE IF NOT EXISTS `usuarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `edad` int(3) DEFAULT NULL,
  `telefono` int(11) DEFAULT NULL,
  `direccion` varchar(50) DEFAULT NULL,
  `nivel` varchar(20) DEFAULT NULL,
  `dias` varchar(20) DEFAULT NULL,
  `horario` varchar(10) DEFAULT NULL,
  `profesor` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `nombre`, `edad`, `telefono`, `direccion`, `nivel`, `dias`, `horario`, `profesor`) VALUES
(1, 'Ricardo Perez Alregria ', 25, 442438754, 'Lomas de Casa Blanca', 'Avanzado', 'Sabados', '9-10', 'Santiago'),
(6, 'Daniel Paredes', 20, 1546546, 'Av Pastores #55', 'Intermedio', 'Domingos', '10-11', 'Manuel'),
(10, 'Mauricio Hernandez', 28, 4654321, 'Av San Martin #115', 'Basico', 'Sabados y Domingos', '7-8', 'Juan'),
(13, 'Samuel', 12, 54544984, 'Calle Los Zapotecas #952', 'Basico', 'Domingos', '12-13', 'Juan'),
(16, 'Nestor', 55, 4456874, 'Av Murcielago #99', 'Intermedio', 'Domingos', '10-11', 'Manuel');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
