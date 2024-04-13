-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 13-04-2024 a las 18:30:27
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `proyectoderecho`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `audiencias`
--

CREATE TABLE `audiencias` (
  `id_audiencia` int(11) NOT NULL,
  `dpi` int(11) NOT NULL,
  `ubicacion_de_la_audiencia` varchar(220) DEFAULT NULL,
  `fecha_de_audiencia` varchar(20) DEFAULT NULL,
  `hora_de_la_audiencia` varchar(30) DEFAULT NULL,
  `detalles` varchar(1000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `dpi` int(11) NOT NULL,
  `nombre` varchar(200) DEFAULT NULL,
  `apellidos` varchar(200) DEFAULT NULL,
  `estado_civil` varchar(50) DEFAULT NULL,
  `numero_de_telefono` int(11) DEFAULT NULL,
  `fecha_de_nacimiento` varchar(18) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`dpi`, `nombre`, `apellidos`, `estado_civil`, `numero_de_telefono`, `fecha_de_nacimiento`) VALUES
(123, 'Antony Ramiro', 'Santos Lopez', 'soltero', 53248271, '28/03/2005');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `registro_de_casos`
--

CREATE TABLE `registro_de_casos` (
  `id_caso` int(11) NOT NULL,
  `tipo` varchar(70) NOT NULL,
  `costo` float NOT NULL,
  `detalles` varchar(500) DEFAULT NULL,
  `saldo_pendiente` float NOT NULL,
  `estado_del_proceso` varchar(100) NOT NULL,
  `id_cliente` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `registro_de_casos`
--

INSERT INTO `registro_de_casos` (`id_caso`, `tipo`, `costo`, `detalles`, `saldo_pendiente`, `estado_del_proceso`, `id_cliente`) VALUES
(1, 'divorcio', 1000, 'detalles del divorcio', 500, 'Terminado', 123);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id_user` int(11) NOT NULL,
  `usuario` varchar(200) DEFAULT NULL,
  `contrasena` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id_user`, `usuario`, `contrasena`) VALUES
(1, 'admin', 'adminPasword');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `audiencias`
--
ALTER TABLE `audiencias`
  ADD PRIMARY KEY (`id_audiencia`),
  ADD KEY `dpi` (`dpi`);

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`dpi`);

--
-- Indices de la tabla `registro_de_casos`
--
ALTER TABLE `registro_de_casos`
  ADD PRIMARY KEY (`id_caso`),
  ADD KEY `id_cliente` (`id_cliente`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `audiencias`
--
ALTER TABLE `audiencias`
  MODIFY `id_audiencia` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `registro_de_casos`
--
ALTER TABLE `registro_de_casos`
  MODIFY `id_caso` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `audiencias`
--
ALTER TABLE `audiencias`
  ADD CONSTRAINT `audiencias_ibfk_1` FOREIGN KEY (`dpi`) REFERENCES `clientes` (`dpi`);

--
-- Filtros para la tabla `registro_de_casos`
--
ALTER TABLE `registro_de_casos`
  ADD CONSTRAINT `registro_de_casos_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `clientes` (`dpi`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
