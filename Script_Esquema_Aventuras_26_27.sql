-- ============================================================================
-- PRACTICA 2026-2027 - EMPRESA DE AVENTURAS TURISTICAS
-- LDD para MariaDB
--
-- El script crea las cinco tablas del modelo relacional:
--   CLIENTE, GUIA, AVENTURA, TURNO y RESERVA.
--
-- Se usa InnoDB porque soporta claves ajenas en MariaDB.
-- ============================================================================

-- ----------------------------------------------------------------------------
-- OPCIONAL: permitir volver a ejecutar el script desde cero.
-- Las tablas se eliminan en orden inverso al de sus dependencias.
-- ATENCIÓN: estas sentencias borrarían todos los datos existentes.
-- ----------------------------------------------------------------------------
-- DROP TABLE IF EXISTS RESERVA;
-- DROP TABLE IF EXISTS TURNO;
-- DROP TABLE IF EXISTS AVENTURA;
-- DROP TABLE IF EXISTS GUIA;
-- DROP TABLE IF EXISTS CLIENTE;


-- ============================================================================
-- 1. CLIENTE
-- ============================================================================
CREATE TABLE CLIENTE (
    idCliente          CHAR(5),  -- ID del cliente (formato fijo, ej: 'CL001')
    nombre             VARCHAR(50)  NOT NULL,
    apellidos          VARCHAR(100) NOT NULL,
    telefono           VARCHAR(15),
    fechaNacimiento    DATE         NOT NULL,
    email              VARCHAR(100),
    idiomaPreferido    CHAR(2)      NOT NULL,   -- Código de idioma: ES, EN, FR o DE.
    categoria          VARCHAR(20)  NOT NULL,   -- Categoría declarada por el cliente.

    CONSTRAINT CP_Cliente PRIMARY KEY (idCliente),
    CONSTRAINT unico_cliente_email UNIQUE (email),
    -- Dominio controlado para el idioma preferido.
    CONSTRAINT chk_cliente_idioma CHECK (idiomaPreferido IN ('ES', 'EN', 'FR', 'DE')),

    -- Dominio controlado para la categoría.
    CONSTRAINT chk_cliente_categoria CHECK (categoria IN ('GENERAL', 'INFANTIL', 'ESTUDIANTE', 'SENIOR'))

    -- Regla del modelo: fechaNacimiento < fecha actual.
    -- No se incluye como CHECK porque CURRENT_DATE es una expresión
    -- no determinista y MariaDB limita los CHECK a expresiones deterministas.
    -- Esta validación se realizará en la aplicación o con un disparador.
);


-- ============================================================================
-- 2. GUIA
-- ============================================================================
CREATE TABLE GUIA (
    idGuia             CHAR(4),  -- ID del guía (formato fijo, ej: 'G001')
    nombre             VARCHAR(50)  NOT NULL,
    apellidos          VARCHAR(100) NOT NULL,
    telefono           VARCHAR(15)  NOT NULL,
    email              VARCHAR(100) NOT NULL,
    fechaAlta          DATE         NOT NULL,

    CONSTRAINT CP_Guia PRIMARY KEY (idGuia),
    CONSTRAINT unico_guia_email UNIQUE (email)

    -- Regla del modelo: fechaAlta < fecha actual.
    -- Por el mismo motivo indicado en CLIENTE, no se expresa mediante CHECK.
    -- Se validará en la aplicación o mediante un disparador.
);

-- ============================================================================
-- 3. AVENTURA
-- ============================================================================
CREATE TABLE AVENTURA (
    idAventura         CHAR(5),  -- ID de la aventura (formato fijo, ej: 'AV001')
    nombre             VARCHAR(100) NOT NULL,
    descripcion        TEXT,
    tipo               VARCHAR(30)  NOT NULL,
    dificultad         VARCHAR(10)  NOT NULL,
    duracion           SMALLINT     NOT NULL,  -- Duración expresada en minutos.
    precio             DECIMAL(8,2) NOT NULL,  -- Precio base de la aventura.
    edadMinima         TINYINT      NOT NULL,  -- Edad mínima exigida para participar.

    CONSTRAINT CP_Aventura PRIMARY KEY (idAventura),
    -- Dominio de tipos de aventura.
    CONSTRAINT chk_aventura_tipo
        CHECK (tipo IN (
            'SENDERISMO',
            'CICLOTURISMO',
            'KAYAK',
            'ESCALADA',
            'BARRANQUISMO',
            'RUTA_ECUESTRE',
            'VISITA_URBANA',
            'VISITA_MUSEO',
            'VISITA_MONUMENTO',
            'RUTA_GASTRONOMICA'
        )),

    CONSTRAINT chk_aventura_duracion CHECK (duracion > 0),
    CONSTRAINT chk_aventura_precio CHECK (precio >= 0),
    CONSTRAINT chk_aventura_edad_minima CHECK (edadMinima >= 0),
    -- Dominio del nivel de dificultad.
    CONSTRAINT chk_aventura_dificultad CHECK (dificultad IN ('BAJA', 'MEDIA', 'ALTA'))
);

-- ============================================================================
-- 4. TURNO
-- ============================================================================
CREATE TABLE TURNO (
    -- TURNO tiene una CP simple e independiente.
    idTurno            CHAR(5),

    -- idAventura es CAjena de AVENTURA.
    idAventura         CHAR(5)     NOT NULL,
    fechaHora          DATETIME    NOT NULL,
    capacidad          SMALLINT    NOT NULL,  -- Número máximo de clientes admitidos en este turno concreto.
    idGuia             CHAR(4)     NOT NULL,  -- Guía que realiza el turno.


    CONSTRAINT CP_Turno PRIMARY KEY (idTurno),

    -- Se conserva como regla de negocio la unicidad derivada
    -- de la CP compuesta que surge de la transformación.
    CONSTRAINT unico_turno_aventura_fecha UNIQUE (idAventura, fechaHora),

    CONSTRAINT CAj_turno_aventura FOREIGN KEY (idAventura)
        REFERENCES AVENTURA (idAventura) ON DELETE CASCADE,
    CONSTRAINT CAj_turno_guia FOREIGN KEY (idGuia)
        REFERENCES GUIA (idGuia),

    CONSTRAINT chk_turno_capacidad CHECK (capacidad > 0)

    -- Reglas que NO se expresan mediante CHECK:
    --   1) Dos turnos de la misma aventura no pueden solaparse teniendo
    --      en cuenta AVENTURA.duracion.
    --   2) Un guía no puede realizar dos turnos que se solapen.
    -- Estas reglas requieren consultar otras tablas y se implementarán
    -- mediante lógica de aplicacion o con disparadores.
);

-- ============================================================================
-- 5. RESERVA
-- ============================================================================
CREATE TABLE RESERVA (
    -- RESERVA tiene una CP simple e independiente.
    idReserva          CHAR(5),

    -- Las dos entidades relacionadas se representan mediante CAjenas simples.
    idCliente          CHAR(5)      NOT NULL,
    idTurno            CHAR(5)      NOT NULL,

    -- Momento en que se crea la reserva.
    fechaReserva       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    -- Importe realmente aplicado al efectuar la reserva.
    -- Se conserva aunque AVENTURA.precio cambie posteriormente.
    importe            DECIMAL(8,2) NOT NULL,
    estado             VARCHAR(15)  NOT NULL,
    valoracion         TINYINT,     -- Valoracion opcional entre 1 y 5.

        CONSTRAINT CP_Reserva PRIMARY KEY (idReserva),

    -- Un cliente solo puede realizar una reserva para un turno concreto.
    -- Esta restricción representa la CP que surge de la transformación
    CONSTRAINT unico_reserva_cliente_turno UNIQUE (idCliente, idTurno),

    CONSTRAINT CAj_reserva_cliente FOREIGN KEY (idCliente)
        REFERENCES CLIENTE (idCliente),
    CONSTRAINT CAj_reserva_turno FOREIGN KEY (idTurno)
        REFERENCES TURNO (idTurno),

    CONSTRAINT chk_reserva_importe CHECK (importe >= 0),

    -- Dominio del estado de una reserva.
    CONSTRAINT chk_reserva_estado CHECK (estado IN ('PENDIENTE', 'CONFIRMADA', 'CANCELADA')),
    -- Mientras no exista valoracion se permite NULL.
    CONSTRAINT chk_reserva_valoracion CHECK (valoracion IS NULL OR valoracion BETWEEN 1 AND 5)

    -- Reglas que requieren consultar otras tablas/filas:
    --   1) La reserva debe realizarse antes de la fecha del turno.
    --   2) El cliente debe cumplir AVENTURA.edadMinima.
    --   3) No se puede superar TURNO.capacidad.
    -- Se implementarán mediante lógica de aplicación o disparadores.
);


-- ============================================================================
-- DATOS INICIALES - PRACTICA 2026-2027 - EMPRESA DE AVENTURAS TURÍSTICAS
--
-- Se insertan datos sintéticos en CLIENTE, GUIA, AVENTURA, TURNO y RESERVA.
-- Los lugares que aparecen en las aventuras son reales o verosímiles,
-- pero los registros, nombres comerciales y datos de los guías son ficticios.
--
-- Casos didácticos intencionados:
--   * G010 no tiene ningún turno asignado.
--   * AV025 no tiene ningún turno programado.
--   * Una misma aventura aparece con capacidades distintas segun el turno.
--   * No existen solapamientos entre turnos de una misma aventura.
--   * No existen solapamientos entre turnos realizados por un mismo guía.
-- ============================================================================

START TRANSACTION;

-- =========================================================================
-- CLIENTE
-- =========================================================================
INSERT INTO CLIENTE
    (idCliente, nombre, apellidos, telefono, fechaNacimiento,
     email, idiomaPreferido, categoria)
VALUES
    ('CL001', 'Ana',   'Tomía Pérez',      '610000001', '1990-04-12', 'ana.tomia@example.com', 'ES', 'GENERAL'),
    ('CL002', 'Paz',   'Ciencia López',    NULL,        '2003-07-20', 'paz.ciencia@example.com', 'EN', 'ESTUDIANTE'),
    ('CL003', 'Mar',   'Ciana Ruiz',       '610000003', '2012-05-11',  NULL, 'ES', 'INFANTIL'),
    ('CL004', 'Leo',   'Nardo Gómez',      '610000004', '1958-11-03', 'leo.nardo@example.com', 'DE', 'SENIOR'),
    ('CL005', 'Inés',  'Perada Martín',    '610000005', '1985-01-19', 'ines.perada@example.com', 'FR', 'GENERAL'),
    ('CL006', 'Eva',   'Cuada Sánchez',    '610000006', '2001-09-15', 'eva.cuada@example.com', 'EN', 'ESTUDIANTE'),
    ('CL007', 'Nora',  'Mal Vega',         '610000007', '2014-02-25', 'nora.mal@example.com', 'ES', 'INFANTIL'),
    ('CL008', 'Elsa',  'Lero Díaz',        NULL,        '1961-06-02', 'elsa.lero@example.com', 'DE', 'SENIOR'),
    ('CL009', 'Sol',   'Edad Romero',      '610000009', '1995-12-08',  NULL, 'FR', 'GENERAL'),
    ('CL010', 'Elena', 'Gante Molina',     '610000010', '2004-03-30', 'elena.gante@example.com', 'EN', 'ESTUDIANTE');

-- =========================================================================
-- GUIA
-- =========================================================================
INSERT INTO GUIA
    (idGuia, nombre, apellidos, telefono, email, fechaAlta)
VALUES
    ('G001', 'Paco',  'Mérselo Entero',  '600000001', 'paco.merselo@example.com',  '2018-03-12'),
    ('G002', 'Lola',  'Mento Mucho',     '600000002', 'lola.mento@example.com',    '2019-06-03'),
    ('G003', 'Aitor', 'Menta Pérez',     '600000003', 'aitor.menta@example.com',   '2020-01-20'),
    ('G004', 'Clara', 'Mente Salas',     '600000004', 'clara.mente@example.com',   '2017-09-18'),
    ('G005', 'Elsa',  'Pato Ruiz',       '600000005', 'elsa.pato@example.com',     '2021-04-05'),
    ('G006', 'Alba',  'Ñil Sánchez',     '600000006', 'alba.anil@example.com',     '2022-02-14'),
    ('G007', 'Pere',  'Gil Molina',      '600000007', 'pere.gil@example.com',      '2023-05-08'),
    ('G008', 'Elba',  'Lazo Ruiz',       '600000008', 'elba.lazo@example.com',     '2020-10-26'),
    ('G009', 'Eva',   'Sión Fernández',  '600000009', 'eva.sion@example.com',      '2024-01-15'),
    ('G010', 'Alma',  'Cenada Gómez',    '600000010', 'alma.cenada@example.com',   '2025-02-03');


-- =========================================================================
-- AVENTURA
-- =========================================================================
INSERT INTO AVENTURA
    (idAventura, nombre, descripcion, tipo, dificultad, duracion, precio, edadMinima)
VALUES
    ('AV001', 'Senderos de Aracena sin Prisa', 'Ruta de senderismo por caminos de la Sierra de Aracena, con paradas interpretativas.', 'SENDERISMO', 'MEDIA', 240, 28.00, 10),
    ('AV002', 'Castaños, Dehesas y Buen Paso', 'Recorrido a pie entre dehesas y castañares de la sierra onubense.', 'SENDERISMO', 'MEDIA', 180, 24.00, 8),
    ('AV003', 'Pedales por Doñana', 'Ruta cicloturista por el entorno de Doñana y sus paisajes de transición.', 'CICLOTURISMO', 'MEDIA', 210, 35.00, 12),
    ('AV004', 'Riotinto sobre Ruedas', 'Recorrido en bicicleta por paisajes mineros y caminos del entorno de Riotinto.', 'CICLOTURISMO', 'ALTA', 270, 42.00, 14),
    ('AV005', 'Kayak por las Marismas del Odiel', 'Travesía guiada en kayak por canales y paisajes de las Marismas del Odiel.', 'KAYAK', 'MEDIA', 150, 32.00, 12),
    ('AV006', 'Remando hacia el Guadiana', 'Ruta de kayak tranquila por el entorno fluvial próximo a Ayamonte.', 'KAYAK', 'MEDIA', 180, 36.00, 10),
    ('AV007', 'Mi Primera Pared', 'Actividad de iniciación a la escalada con ejercicios básicos de seguridad y progresión.', 'ESCALADA', 'MEDIA', 180, 39.00, 12),
    ('AV008', 'Vertical pero con Calma', 'Jornada de escalada para participantes con cierta experiencia previa.', 'ESCALADA', 'ALTA', 240, 52.00, 16),
    ('AV009', 'Barranco y Buen Humor', 'Descenso guiado de barranco con progresión mediante técnicas básicas de barranquismo.', 'BARRANQUISMO', 'ALTA', 300, 58.00, 16),
    ('AV010', 'El Rocío a Caballo', 'Ruta ecuestre por caminos del entorno de El Rocío.', 'RUTA_ECUESTRE', 'BAJA', 120, 40.00, 8),
    ('AV011', 'Pinares a Paso de Caballo', 'Ruta ecuestre de media duración por caminos y pinares del litoral onubense.', 'RUTA_ECUESTRE', 'MEDIA', 180, 48.00, 10),
    ('AV012', 'Huelva: del Barrio Inglés a la Ría', 'Visita urbana guiada por enclaves históricos y portuarios de la ciudad de Huelva.', 'VISITA_URBANA', 'BAJA', 150, 16.00, 6),
    ('AV013', 'Aracena entre Plazas y Leyendas', 'Paseo guiado por el casco urbano de Aracena y sus principales espacios históricos.', 'VISITA_URBANA', 'BAJA', 120, 15.00, 6),
    ('AV014', 'Ayamonte al Atardecer', 'Recorrido urbano por el centro histórico de Ayamonte al final de la tarde.', 'VISITA_URBANA', 'BAJA', 120, 14.00, 6),
    ('AV015', 'Moguer de Juan Ramón', 'Ruta urbana por lugares vinculados a la historia y cultura de Moguer.', 'VISITA_URBANA', 'BAJA', 120, 14.00, 8),
    ('AV016', 'Museo de Huelva sin Bostezos', 'Visita guiada a las colecciones arqueológicas y artísticas del Museo de Huelva.', 'VISITA_MUSEO', 'BAJA', 90, 10.00, 6),
    ('AV017', 'Riotinto: Minas, Museo y Marte', 'Visita interpretativa centrada en la historia minera y el paisaje de Riotinto.', 'VISITA_MUSEO', 'BAJA', 150, 18.00, 8),
    ('AV018', 'Jamón de Jabugo sin Prisas', 'Ruta gastronómica dedicada al jamón, la dehesa y la tradición serrana.', 'RUTA_GASTRONOMICA', 'BAJA', 180, 45.00, 12),
    ('AV019', 'Sabores del Condado', 'Recorrido gastronómico por productos y vinos tradicionales del Condado de Huelva.', 'RUTA_GASTRONOMICA', 'BAJA', 210, 49.00, 18),
    ('AV020', 'Huelva de Tapas y Gamba Blanca', 'Paseo gastronómico por sabores representativos de la costa onubense.', 'RUTA_GASTRONOMICA', 'BAJA', 180, 46.00, 18),
    ('AV021', 'Muelle de las Carabelas: Rumbo a 1492', 'Visita guiada al entorno colombino y al Muelle de las Carabelas.', 'VISITA_MONUMENTO', 'BAJA', 120, 13.00, 6),
    ('AV022', 'Niebla entre Murallas', 'Visita interpretativa al patrimonio histórico y las murallas de Niebla.', 'VISITA_MONUMENTO', 'BAJA', 120, 12.00, 6),
    ('AV023', 'Doñana a Pie: Dunas y Pinares', 'Senderismo interpretativo por paisajes característicos del entorno de Doñana.', 'SENDERISMO', 'MEDIA', 240, 31.00, 12),
    ('AV024', 'Puesta de Sol en el Rompido', 'Ruta en kayak al atardecer por el entorno de la Flecha del Rompido.', 'KAYAK', 'MEDIA', 150, 38.00, 12),
    ('AV025', 'Roca, Vistas y Estreno', 'Nueva aventura de escalada avanzada pendiente de programación de turnos.', 'ESCALADA', 'ALTA', 270, 55.00, 16);

-- =========================================================================
-- TURNO
-- =========================================================================
INSERT INTO TURNO (idTurno, idAventura, fechaHora, capacidad, idGuia) VALUES
    ('T0001', 'AV001', '2026-09-19 09:00:00', 14, 'G005'),
    ('T0002', 'AV002', '2026-09-19 17:00:00', 16, 'G003'),
    ('T0003', 'AV003', '2026-09-20 10:00:00', 14, 'G008'),
    ('T0004', 'AV004', '2026-10-03 09:00:00', 16, 'G002'),
    ('T0005', 'AV005', '2026-10-03 17:00:00', 9, 'G002'),
    ('T0006', 'AV006', '2026-10-04 10:00:00', 11, 'G003'),
    ('T0007', 'AV007', '2026-10-17 09:00:00', 6, 'G008'),
    ('T0008', 'AV008', '2026-10-17 17:00:00', 8, 'G009'),
    ('T0009', 'AV009', '2026-10-18 10:00:00', 10, 'G004'),
    ('T0010', 'AV010', '2026-10-31 09:00:00', 14, 'G003'),
    ('T0011', 'AV011', '2026-10-31 17:00:00', 9, 'G001'),
    ('T0012', 'AV012', '2026-11-01 10:00:00', 26, 'G005'),
    ('T0013', 'AV013', '2026-11-14 09:00:00', 23, 'G004'),
    ('T0014', 'AV014', '2026-11-14 17:00:00', 25, 'G006'),
    ('T0015', 'AV015', '2026-11-15 10:00:00', 27, 'G004'),
    ('T0016', 'AV016', '2026-11-28 09:00:00', 24, 'G007'),
    ('T0017', 'AV017', '2026-11-28 17:00:00', 19, 'G001'),
    ('T0018', 'AV018', '2026-11-29 10:00:00', 19, 'G003'),
    ('T0019', 'AV019', '2026-12-12 09:00:00', 16, 'G007'),
    ('T0020', 'AV020', '2026-12-12 17:00:00', 18, 'G001'),
    ('T0021', 'AV021', '2026-12-13 10:00:00', 27, 'G004'),
    ('T0022', 'AV022', '2026-12-26 09:00:00', 29, 'G002'),
    ('T0023', 'AV023', '2026-12-26 17:00:00', 15, 'G009'),
    ('T0024', 'AV024', '2026-12-27 10:00:00', 11, 'G003'),
    ('T0025', 'AV001', '2027-01-09 09:00:00', 14, 'G005'),
    ('T0026', 'AV002', '2027-01-09 17:00:00', 16, 'G003'),
    ('T0027', 'AV003', '2027-01-10 10:00:00', 14, 'G001'),
    ('T0028', 'AV004', '2027-01-23 09:00:00', 16, 'G006'),
    ('T0029', 'AV005', '2027-01-23 17:00:00', 9, 'G003'),
    ('T0030', 'AV006', '2027-01-24 10:00:00', 11, 'G007'),
    ('T0031', 'AV007', '2027-02-06 09:00:00', 6, 'G002'),
    ('T0032', 'AV008', '2027-02-06 17:00:00', 8, 'G004'),
    ('T0033', 'AV009', '2027-02-07 10:00:00', 10, 'G005'),
    ('T0034', 'AV010', '2027-02-20 09:00:00', 14, 'G003'),
    ('T0035', 'AV011', '2027-02-20 17:00:00', 9, 'G007'),
    ('T0036', 'AV012', '2027-02-21 10:00:00', 26, 'G002'),
    ('T0037', 'AV013', '2027-03-06 09:00:00', 23, 'G005'),
    ('T0038', 'AV014', '2027-03-06 17:00:00', 25, 'G006'),
    ('T0039', 'AV015', '2027-03-07 10:00:00', 27, 'G004'),
    ('T0040', 'AV016', '2027-03-20 09:00:00', 24, 'G008'),
    ('T0041', 'AV017', '2027-03-20 17:00:00', 19, 'G002'),
    ('T0042', 'AV018', '2027-03-21 10:00:00', 19, 'G006'),
    ('T0043', 'AV019', '2027-04-03 09:00:00', 16, 'G009'),
    ('T0044', 'AV020', '2027-04-03 17:00:00', 18, 'G001'),
    ('T0045', 'AV021', '2027-04-04 10:00:00', 27, 'G004'),
    ('T0046', 'AV022', '2027-04-17 09:00:00', 29, 'G005'),
    ('T0047', 'AV023', '2027-04-17 17:00:00', 15, 'G001'),
    ('T0048', 'AV024', '2027-04-18 10:00:00', 11, 'G004'),
    ('T0049', 'AV001', '2027-05-01 09:00:00', 14, 'G007'),
    ('T0050', 'AV002', '2027-05-01 17:00:00', 16, 'G006'),
    ('T0051', 'AV003', '2027-05-02 10:00:00', 14, 'G001'),
    ('T0052', 'AV004', '2027-05-15 09:00:00', 16, 'G008'),
    ('T0053', 'AV005', '2027-05-15 17:00:00', 9, 'G001'),
    ('T0054', 'AV006', '2027-05-16 10:00:00', 11, 'G002'),
    ('T0055', 'AV007', '2027-05-29 09:00:00', 6, 'G002'),
    ('T0056', 'AV008', '2027-05-29 17:00:00', 8, 'G002'),
    ('T0057', 'AV009', '2027-05-30 10:00:00', 10, 'G006'),
    ('T0058', 'AV010', '2027-06-12 09:00:00', 14, 'G001'),
    ('T0059', 'AV011', '2027-06-12 17:00:00', 9, 'G001'),
    ('T0060', 'AV012', '2027-06-13 10:00:00', 26, 'G005');

    -- =========================================================================
    -- RESERVA
    -- =========================================================================
    INSERT INTO RESERVA
        (idReserva, idCliente, idTurno, fechaReserva, importe, estado, valoracion)
    VALUES

        -- Ana Tomía: tres reservas y repite AV001 en otro turno.
        ('R0001', 'CL001', 'T0001', '2026-09-01 10:15:00', 28.00, 'CONFIRMADA', 5),
        ('R0002', 'CL001', 'T0012', '2026-09-12 17:30:00', 16.00, 'CONFIRMADA', NULL),
        ('R0003', 'CL001', 'T0025', '2026-09-22 09:10:00', 28.00, 'PENDIENTE', NULL),

        -- Paz Ciencia
        ('R0004', 'CL002', 'T0003', '2026-09-08 12:20:00', 35.00, 'CONFIRMADA', 3),
        ('R0005', 'CL002', 'T0016', '2026-09-14 16:45:00', 10.00, 'CANCELADA', NULL),

        -- Mar Ciana
        ('R0006', 'CL003', 'T0001', '2026-09-03 18:00:00', 28.00, 'CONFIRMADA', 4),
        ('R0007', 'CL003', 'T0005', '2026-09-10 11:40:00', 32.00, 'CONFIRMADA', NULL),

        -- Leo Nardo
        ('R0008', 'CL004', 'T0018', '2026-09-15 10:05:00', 45.00, 'CONFIRMADA', NULL),

        -- Inés Perada
        ('R0009', 'CL005', 'T0004', '2026-09-11 19:15:00', 42.00, 'CONFIRMADA', NULL),
        ('R0010', 'CL005', 'T0018', '2026-09-16 09:30:00', 45.00, 'CANCELADA', NULL),

        -- Eva Cuada
        ('R0011', 'CL006', 'T0005', '2026-09-13 13:25:00', 32.00, 'CONFIRMADA', NULL),

        -- Nora Mal
        ('R0012', 'CL007', 'T0016', '2026-09-18 17:10:00', 10.00, 'CONFIRMADA', NULL),
        ('R0013', 'CL007', 'T0031', '2026-09-24 10:30:00', 39.00, 'PENDIENTE', NULL),

        -- Elsa Lero
        ('R0014', 'CL008', 'T0019', '2026-09-20 18:45:00', 49.00, 'CONFIRMADA', NULL),

        -- Sol Edad
        ('R0015', 'CL009', 'T0044', '2026-09-23 12:00:00', 46.00, 'PENDIENTE', NULL);


COMMIT;
