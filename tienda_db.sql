CREATE TABLE CARRITO_DROP (
  Id int(11) NOT NULL,
  Id_Producto int(11) DEFAULT NULL,
  Cantidad int(11) NOT NULL,
  Nombre_producto varchar(100) NOT NULL,
  Precio double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE CLIENTE (
  Id int(11) NOT NULL,
  NombreCompleto varchar(100) NOT NULL,
  CorreoElectronico varchar(100) NOT NULL,
  Contrasena varchar(255) NOT NULL,
  Cedula varchar(20) NOT NULL,
  Direccion varchar(255) NOT NULL,
  Rol varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE FACTURAS (
  Id int(11) NOT NULL,
  Nombre varchar(100) NOT NULL,
  Id_cliente int(11) DEFAULT NULL,
  CorreoElectronico varchar(30) NOT NULL,
  Id_Producto int(10) NOT NULL,
  Cantidad int(10) NOT NULL,
  Nombre_producto varchar(255) NOT NULL,
  Precio double NOT NULL,
  Fecha date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE PAGOS (
  Id int(11) NOT NULL,
  Fecha datetime DEFAULT CURRENT_TIMESTAMP,
  Nombre varchar(100) NOT NULL,
  Valor decimal(10,2) NOT NULL,
  Id_factura int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE PRODUCTOS (
  Id int(11) NOT NULL,
  Nombre varchar(100) NOT NULL,
  Precio decimal(10,2) NOT NULL,
  Marca varchar(50) NOT NULL,
  Descripcion text,
  Categoria varchar(50) DEFAULT NULL,
  Imagen blob,
  Stock int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


