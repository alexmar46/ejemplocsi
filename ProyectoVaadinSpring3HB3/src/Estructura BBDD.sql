DROP TABLE IF EXISTS `test`.`usuario`;
CREATE TABLE  `test`.`usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `apellidos` varchar(255) NOT NULL,
  `clave` varchar(255) NOT NULL,
  `correo` varchar(255) NOT NULL,
  `fecha_creacion` datetime DEFAULT NULL,
  `fechainactivado` datetime DEFAULT NULL,
  `llave_cambio_clave` varchar(255) DEFAULT NULL,
  `nombres` varchar(255) NOT NULL,
  `rol` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;