/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package Aplicacion;

import Config.HibernateUtil;
import Modelo.Aventura;
import Modelo.Cliente;
import Modelo.Guia;
import Modelo.Reserva;
import Modelo.Turno;
import jakarta.persistence.NamedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import jakarta.persistence.NoResultException; // excepción que lanza getSingleResult() si no hay ninguna fila
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;

/**
 *
 * @author Laura
 */
public class Practica1 {

    public static void main(String[] args) {
        try {
            // Se obtiene la SessionFactory definida en HibernateUtil
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            System.out.println("Hibernate inicializado correctamente");
        } catch (ExceptionInInitializerError e) {
            Throwable cause = e.getCause();
            System.out.println("Error al inicializar Hibernate. Revise el fichero .cfg.xml: " + cause.getMessage());
            return;
        }
        Scanner teclado = new Scanner(System.in);

        int opcion;
        do {
            System.out.println();
            System.out.println("=========== MENÚ ===========");
            System.out.println(" 1. Información de los guías (HQL)");
            System.out.println(" 2. Información de los guías (SQL nativo)");
            System.out.println(" 3. Información de los guías (Consulta nombrada)");
            System.out.println(" 4. Nombre y teléfono de los guías");
            System.out.println(" 5. Nombre y dificultad de las aventuras de un tipo");
            System.out.println(" 6. Nombre y apellidos de un guía por teléfono");
            System.out.println(" 7. Información de aventura por nombre");
            System.out.println(" 8. Turnos de una aventura por capacidad mínima");
            System.out.println(" 9. Información de reservas por estado (HQL)");
            System.out.println("10. Información de clientes por idioma preferido (SQL nativo)");
            System.out.println("11. insercionCliente");
            System.out.println("12. borrarCliente");
            System.out.println("13. turnosGuia");
            System.out.println("14. aventurasCliente");
            System.out.println("15. clientesConReserva");
            System.out.println("16. reservaClienteEnTurno");
            System.out.println("17. cancelarReserva");
            System.out.println("18. agendaGuia");
            System.out.println("19. reservasImportes");
            System.out.println("20. clientesMayores");
            System.out.println(" 0. Salir");

            opcion = teclado.nextInt();

            switch (opcion) {
                case 1:
                    guiasHQL();
                    break;
                case 2:
                    guiasSQLNativo();
                    break;
                case 3:
                    guiasConsultaNombrada();
                    break;
                case 4:
                    nombreYTelefonoGuias();
                    break;
                case 5:
                    aventurasPorTipo();
                    break;
                case 6:
                    guiaPorTelefono();
                    break;
                case 7:
                    aventuraPorNombre();
                    break;
                case 8:
                    turnosPorCapacidadMinima();
                    break;
                case 9:
                    reservasPorEstado();
                    break;
                case 10:
                    clientesPorIdioma();
                    break;
                case 11:
                    insercionCliente();
                    break;
                case 12:
                    borrarCliente();
                    break;
                case 13:
                    turnosGuia();
                    break;
                case 14:
                    aventurasCliente();
                    break;
                case 15:
                    clientesConReserva();
                    break;
                case 16:
                    reservaClienteEnTurno();
                    break;
                case 17:
                    cancelarReserva();
                    break;
                case 18:
                    agendaGuia();
                    break;
                case 19:
                    reservasImportes();
                    break;
                case 20:
                    clientesMayores();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }

        } while (opcion != 0);

        HibernateUtil.close();

        /*
        //ESTO PARA TODAS
        Session sesion = null;
        Transaction tr = null;     
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            sesion = sessionFactory.openSession();
            tr = sesion.beginTransaction();
            //lo que haga
            tr.commit();
        } catch (Exception e) {
            if (tr != null && tr.isActive()) {
                tr.rollback();
            }
            System.out.println("Error en la recuperación "
                    + e.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
         */
    }

    //EJERCICIOS
    private static void guiasHQL() {
        Session sesion = null;
        Transaction tr = null;
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            sesion = sessionFactory.openSession();
            tr = sesion.beginTransaction();

            Query<Guia> guias = sesion.createQuery("SELECT g FROM Guia g", Guia.class);
            imprimirGuias(guias.getResultList());

            tr.commit();
        } catch (Exception e) {
            if (tr != null && tr.isActive()) {
                tr.rollback();
            }
            System.out.println("Error en la recuperación "
                    + e.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }

    }

    private static void guiasSQLNativo() {
        Session sesion = null;
        Transaction tr = null;
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            sesion = sessionFactory.openSession();
            tr = sesion.beginTransaction();

            Query<Guia> guias = sesion.createNativeQuery("SELECT * FROM GUIA", Guia.class);
            imprimirGuias(guias.getResultList());

            tr.commit();
        } catch (Exception e) {
            if (tr != null && tr.isActive()) {
                tr.rollback();
            }
            System.out.println("Error en la recuperación "
                    + e.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }

    }

    private static void guiasConsultaNombrada() {
        Session sesion = null;
        Transaction tr = null;
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            sesion = sessionFactory.openSession();
            tr = sesion.beginTransaction();

            Query<Guia> guias = sesion.createNamedQuery("Guia.findAll", Guia.class);
            imprimirGuias(guias.getResultList());

            tr.commit();
        } catch (Exception e) {
            if (tr != null && tr.isActive()) {
                tr.rollback();
            }
            System.out.println("Error en la recuperación "
                    + e.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }

    }

    private static void nombreYTelefonoGuias() {
        Session sesion = null;
        Transaction tr = null;
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            sesion = sessionFactory.openSession();
            tr = sesion.beginTransaction();

            List<Object[]> guias = sesion.createQuery("SELECT g.nombre, g.telefono FROM Guia g", Object[].class).getResultList();
            //imprimirGuias(guias);
            for (Object[] fila : guias) {
                // fila[0] = nombre, fila[1] = teléfono (mismo orden que en el SELECT)
                System.out.println("Nombre: " + fila[0] + " | Teléfono: " + fila[1]);
            }

            tr.commit();
        } catch (Exception e) {
            if (tr != null && tr.isActive()) {
                tr.rollback();
            }
            System.out.println("Error en la recuperación "
                    + e.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }

    private static void aventurasPorTipo() {
        System.out.print("Tipo de aventura: ");
        Scanner teclado = new Scanner(System.in);
        String tipo = teclado.nextLine();
        Session sesion = null;
        Transaction tr = null;
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            sesion = sessionFactory.openSession();
            tr = sesion.beginTransaction();

            List<Object[]> aventuras = sesion.createQuery("SELECT a.nombre, a.dificultad FROM Aventura a WHERE a.tipo = :tipo", Object[].class).setParameter("tipo", tipo).getResultList();
            //imprimirGuias(guias);
            if (aventuras.isEmpty()) {
                System.out.println("No hay aventuras de ese tipo");
            }
            for (Object[] fila : aventuras) {
                // fila[0] = nombre, fila[1] = teléfono (mismo orden que en el SELECT)
                System.out.println("Nombre: " + fila[0] + " | Dificultad: " + fila[1]);
            }

            tr.commit();
        } catch (Exception e) {
            if (tr != null && tr.isActive()) {
                tr.rollback();
            }
            System.out.println("Error en la recuperación "
                    + e.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }

    private static void guiaPorTelefono() {
        System.out.print("Telefono: ");
        Scanner teclado = new Scanner(System.in);
        String telefono = teclado.nextLine();
        Session sesion = null;
        Transaction tr = null;
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            sesion = sessionFactory.openSession();
            tr = sesion.beginTransaction();

            //solo espera uno, por eso en lugar de List<Object[]> y getResultList uso Object[] y getSingleResult
            Object[] guia = sesion.createQuery("SELECT g.nombre, g.apellidos FROM Guia g WHERE g.telefono = :telefono", Object[].class).setParameter("telefono", telefono).getSingleResult();
            //imprimirGuias(guias);

            System.out.println("Nombre: " + guia[0] + " | Apellido: " + guia[1]);

            tr.commit();
        } catch (Exception e) {
            if (tr != null && tr.isActive()) {
                tr.rollback();
            }
            System.out.println("Error en la recuperación "
                    + e.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }

    private static void aventuraPorNombre() {
        System.out.print("Nombre: ");
        Scanner teclado = new Scanner(System.in);
        String nombre = teclado.nextLine();

        Session sesion = null;
        Transaction tr = null;
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            sesion = sessionFactory.openSession();
            tr = sesion.beginTransaction();

            //AQUI
            Query<Aventura> consulta = sesion.createNativeQuery("SELECT * FROM AVENTURA a WHERE a.nombre= :nombre", Aventura.class).setParameter("nombre", nombre);
            List<Aventura> aventuras = consulta.getResultList();
            for (Aventura aventura : aventuras) {
                imprimirAventura(aventura);
            }

            tr.commit();
        } catch (Exception e) {
            if (tr != null && tr.isActive()) {
                tr.rollback();
            }
            System.out.println("Error en la recuperación "
                    + e.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }

    private static void turnosPorCapacidadMinima() {
        System.out.print("Identificador de la aventura: ");
        Scanner teclado = new Scanner(System.in);
        String idAventura = teclado.nextLine();
        System.out.print("capacidad: ");
        String capacidad = teclado.nextLine();
        Session sesion = null;
        Transaction tr = null;
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            sesion = sessionFactory.openSession();
            tr = sesion.beginTransaction();

            //AQUI
            Query<Turno> consulta = sesion.createNativeQuery("SELECT * FROM TURNO t WHERE t.idAventura= :idAventura AND t.capacidad>= :capacidad", Turno.class).setParameter("idAventura", idAventura).setParameter("capacidad", capacidad);
            List<Turno> turnos = consulta.getResultList();
            for (Turno turno : turnos) {
                imprimirTurno(turno);
            }

            tr.commit();
        } catch (Exception e) {
            if (tr != null && tr.isActive()) {
                tr.rollback();
            }
            System.out.println("Error en la recuperación "
                    + e.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }

    private static void reservasPorEstado() {
        System.out.print("Estado reserva: ");
        Scanner teclado = new Scanner(System.in);
        String estado = teclado.nextLine();
        Session sesion = null;
        Transaction tr = null;
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            sesion = sessionFactory.openSession();
            tr = sesion.beginTransaction();

            //AQUI
            List<Reserva> reservas = sesion.createNamedQuery("Reserva.findByEstado", Reserva.class).setParameter("estado", estado).getResultList();
            for (Reserva reserva : reservas) {
                imprimirReserva(reserva);
            }

            tr.commit();
        } catch (Exception e) {
            if (tr != null && tr.isActive()) {
                tr.rollback();
            }
            System.out.println("Error en la recuperación "
                    + e.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }

    private static void clientesPorIdioma() {
        System.out.print("Idioma preferido: ");
        Scanner teclado = new Scanner(System.in);
        String idioma = teclado.nextLine();
        Session sesion = null;
        Transaction tr = null;
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            sesion = sessionFactory.openSession();
            tr = sesion.beginTransaction();

            //AQUI
            List<Cliente> clientes = sesion.createNamedQuery("Cliente.findByIdiomaNativo", Cliente.class)
                    .setParameter("idioma", idioma)
                    .getResultList();
            for (Cliente cliente : clientes) {
                imprimirCliente(cliente);
            }

            tr.commit();
        } catch (Exception e) {
            if (tr != null && tr.isActive()) {
                tr.rollback();
            }
            System.out.println("Error en la recuperación "
                    + e.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }

    //MOSTRAR DATOS 
    private static void imprimirGuias(List<Guia> guias) {
        if (guias.isEmpty()) {
            System.out.println("No hay guías.");
        }
        for (Guia g : guias) {
            System.out.println("ID: " + g.getIdGuia()
                    + " | Nombre: " + g.getNombre()
                    + " | Apellidos: " + g.getApellidos()
                    + " | Teléfono: " + g.getTelefono()
                    + " | Email: " + g.getEmail()
                    + " | Fecha alta: " + g.getFechaAlta());
        }
    }

    private static void imprimirAventura(Aventura a) {
        System.out.println("ID: " + a.getIdAventura()
                + " | Nombre: " + a.getNombre()
                + " | Descripción: " + a.getDescripcion()
                + " | Tipo: " + a.getTipo()
                + " | Dificultad: " + a.getDificultad()
                + " | Duración: " + a.getDuracion()
                + " | Precio: " + a.getPrecio()
                + " | Edad mínima: " + a.getEdadMinima());
    }

    private static void imprimirTurno(Turno t) {
        System.out.println("ID: " + t.getIdTurno()
                + " | Fecha y hora: " + t.getFechaHora()
                + " | Capacidad: " + t.getCapacidad()
                + " | Aventura: " + t.getAventura().getIdAventura() // objeto relacionado -> su id
                + " | Guía: " + t.getGuia().getIdGuia());
    }

    private static void imprimirReserva(Reserva r) {
        System.out.println("ID: " + r.getIdReserva()
                + " | Fecha reserva: " + r.getFechaReserva()
                + " | Importe: " + r.getImporte()
                + " | Estado: " + r.getEstado()
                + " | Valoración: " + (r.getValoracion() != null ? r.getValoracion() : "sin valorar")
                + " | Cliente: " + r.getCliente().getIdCliente()
                + " | Turno: " + r.getTurno().getIdTurno());
    }

    private static void imprimirCliente(Cliente c) {
        System.out.println("ID: " + c.getIdCliente()
                + " | Nombre: " + c.getNombre()
                + " | Apellidos: " + c.getApellidos()
                + " | Teléfono: " + c.getTelefono()
                + " | Fecha nacimiento: " + c.getFechaNacimiento()
                + " | Email: " + c.getEmail()
                + " | Idioma: " + c.getIdiomaPreferido()
                + " | Categoría: " + c.getCategoria());
    }

    private static void insercionCliente() {

        Scanner teclado = new Scanner(System.in);

        //Pedir los datos
        System.out.print("Nombre: ");
        String nombre = teclado.nextLine();
        System.out.print("Apellidos: ");
        String apellidos = teclado.nextLine();
        System.out.print("Teléfono (Enter para dejarlo vacío): ");
        String telefono = teclado.nextLine();
        if (telefono.isBlank()) { //isBlank(): true si está vacío o solo tiene espacios
            telefono = null; //el teléfono es opcional
        }
        System.out.print("Fecha de nacimiento: ");
        String textoFecha = teclado.nextLine();
        System.out.print("Email (Enter para dejarlo vacío): ");
        String email = teclado.nextLine();
        if (email.isBlank()) {
            email = null;
        }
        System.out.print("Idioma preferido (ES, EN, FR, DE): ");
        String idioma = teclado.nextLine().toUpperCase();
        System.out.print("Categoría (GENERAL, INFANTIL, ESTUDIANTE, SENIOR): ");
        String categoria = teclado.nextLine().toUpperCase();

        //Validaciones
        LocalDate fechaNacimiento;
        try {
            fechaNacimiento = LocalDate.parse(textoFecha); //convierte en un LocalDate
        } catch (DateTimeParseException e) {
            System.out.println("Fecha no válida. Use el formato aaaa-mm-dd");
            return;
        }

        // R1: la fecha de nacimiento debe ser anterior a hoy
        if (!fechaNacimiento.isBefore(LocalDate.now())) {
            System.out.println("La fecha de nacimiento debe ser anterior a la fecha actual (R1)");
            return;
        }

        // R2: la categoría debe ser coherente con la edad (años cumplidos a día de hoy)
        int edad = Period.between(fechaNacimiento, LocalDate.now()).getYears();
        if (categoria.equals("INFANTIL") && edad > 14) {
            System.out.println("La categoría INFANTIL exige 14 años o menos (R2)");
            return;
        }
        if (categoria.equals("SENIOR") && edad < 65) {
            System.out.println("La categoría SENIOR exige 65 años o más (R2)");
            return;
        }

        // Valores permitidos (los CHECK de la BD)
        if (!idioma.equals("ES") && !idioma.equals("EN") && !idioma.equals("FR") && !idioma.equals("DE")) {
            System.out.println("Idioma no válido");
            return;
        }
        if (!categoria.equals("GENERAL") && !categoria.equals("INFANTIL")
                && !categoria.equals("ESTUDIANTE") && !categoria.equals("SENIOR")) {
            System.out.println("Categoría no válida");
            return;
        }
        Session sesion = null;
        Transaction tr = null;

        //generar idCliente
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            sesion = sessionFactory.openSession();
            tr = sesion.beginTransaction();

            // R13: el siguiente id libre con formato CLnnn
            String max = sesion.createQuery("SELECT MAX(c.idCliente) FROM Cliente c", String.class).getSingleResult();
            int siguiente = (max == null) ? 1 : Integer.parseInt(max.substring(2)) + 1;
            // substring(2) quita "CL" y deja "010"; parseInt lo convierte en 10; +1 da 11
            String idCliente = String.format("CL%03d", siguiente); // %03d: 3 cifras con ceros delante -> "CL011"

            Cliente nuevo = new Cliente(idCliente, nombre, apellidos, telefono,
                    fechaNacimiento, email, idioma, categoria);
            sesion.persist(nuevo);
            System.out.println("Cliente insertado con id " + idCliente);

            tr.commit();
        } catch (Exception e) {
            if (tr != null && tr.isActive()) {
                tr.rollback();
            }
            System.out.println("Error en la recuperación " + e.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }

    private static void borrarCliente() {
        Scanner teclado = new Scanner(System.in);

        //Pedir los datos
        System.out.print("Email del cliente a eliminar: ");
        String email = teclado.nextLine();

        Session sesion = null;
        Transaction tr = null;
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            sesion = sessionFactory.openSession();
            tr = sesion.beginTransaction();

            List<Cliente> clientes = sesion.createNamedQuery("Cliente.findByEmail", Cliente.class)
                    .setParameter("email", email).getResultList();

            if (clientes.isEmpty()) {
                System.out.println("No existe ningún cliente con ese email");
            } else {
                Cliente cliente = clientes.get(0); //solo puede haber uno porque el email es UNIQUE
                sesion.remove(cliente); //programa el DELETE
            }

            tr.commit(); //aquí se ejecuta el DELETE; si tiene reservas, la clave ajena lo rechaza y salta una excepción
            if (!clientes.isEmpty()) {
                System.out.println("Cliente eliminado"); //solo llega aquí si el commit ha ido bien
            }
        } catch (ConstraintViolationException e) {
            //se ha incumplido una restricción de la BD: aquí, la clave ajena de RESERVA hacia CLIENTE
            if (tr != null && tr.isActive()) {
                tr.rollback();
            }
            System.out.println("No se puede eliminar el cliente porque tiene reservas");
        } catch (Exception e) {
            if (tr != null && tr.isActive()) {
                tr.rollback();
            }
            System.out.println("Error en la recuperación "
                    + e.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }

    private static void turnosGuia() {
        Scanner teclado = new Scanner(System.in);

        //Pedir los datos
        System.out.print("Email del guia: ");
        String email = teclado.nextLine();

        Session sesion = null;
        Transaction tr = null;
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            sesion = sessionFactory.openSession();
            tr = sesion.beginTransaction();
            //lo que haga

            //turno tiene id guia
            //quiero los turnos asignados al guia del email que me pasan
            List<Guia> guias = sesion.createNamedQuery("Guia.findByEmail", Guia.class)
                    .setParameter("email", email).getResultList();

            if (guias.isEmpty()) {
                System.out.println("No existe ningún guía con ese email");
            } else {
                Guia guia = guias.get(0);
                Set<Turno> turnos = guia.getTurnos();  // navegación 1:N: los turnos del guía (Hibernate los carga ahora)

                if (turnos.isEmpty()) {
                    System.out.println("El guía no tiene turnos asignados");
                } else {
                    for (Turno turno : turnos) {
                        System.out.println("Turno: " + turno.getIdTurno()
                                + " fecha y hora: " + turno.getFechaHora()
                                + " capacidad: " + turno.getCapacidad()
                                + "Aventura: " + turno.getAventura().getNombre());
                    }

                }
            }

            tr.commit();
        } catch (Exception e) {
            if (tr != null && tr.isActive()) {
                tr.rollback();
            }
            System.out.println("Error en la recuperación "
                    + e.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }

    private static void aventurasCliente() {
        Scanner teclado = new Scanner(System.in);

        //Pedir los datos
        System.out.print("Email del cliente: ");
        String email = teclado.nextLine();
        Session sesion = null;
        Transaction tr = null;
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            sesion = sessionFactory.openSession();
            tr = sesion.beginTransaction();
            //lo que haga

            List<Cliente> clientes = sesion.createNamedQuery("Cliente.findByEmail", Cliente.class)
                    .setParameter("email", email).getResultList();

            if (clientes.isEmpty()) {
                System.out.println("No existe ningún cliente con ese email");
            } else {
                Cliente cliente = clientes.get(0);
                Set<Aventura> aventuras = new HashSet<>();
                for (Reserva reserva : cliente.getReservas()) {
                    if (!reserva.getEstado().equals("CANCELADA")) {
                        aventuras.add(reserva.getTurno().getAventura());
                    }
                }

                //tengo cliente quiero mostrar aventuras
                //cliente cojo: reservas
                //turno cojo: getaventura, getreservas
                //reserva cojo: getturno, getcliente
                if (aventuras.isEmpty()) {
                    System.out.println("El cliente no tiene reservas no canceladas");
                }
                for (Aventura aventura : aventuras) {
                    imprimirAventura(aventura);
                }
            }

            tr.commit();
        } catch (Exception e) {
            if (tr != null && tr.isActive()) {
                tr.rollback();
            }
            System.out.println("Error en la recuperación "
                    + e.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }

    private static void clientesConReserva() {
        Scanner teclado = new Scanner(System.in);

        //Pedir los datos
        System.out.print("Nombre de la aventura: ");
        String nombre = teclado.nextLine();
        Session sesion = null;
        Transaction tr = null;
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            sesion = sessionFactory.openSession();
            tr = sesion.beginTransaction();
            //lo que haga

            List<Aventura> aventuras = sesion.createNamedQuery("Aventura.findByNombre", Aventura.class)
                    .setParameter("nombre", nombre).getResultList();

            if (aventuras.isEmpty()) {
                System.out.println("No existe ninguna aventura con ese nombre");
            } else {
                Set<Cliente> clientes = new HashSet<>(); //Set: un cliente no sale repetido aunque tenga varias reservas

                for (Aventura aventura : aventuras) { // por si hay varias aventuras con ese nombre
                    for (Turno turno : aventura.getTurnos()) {
                        for (Reserva reserva : turno.getReservas()) {
                            if (!reserva.getEstado().equals("CANCELADA")) {
                                clientes.add(reserva.getCliente());
                            }
                        }
                    }
                }
                if (clientes.isEmpty()) {
                    System.out.println("Ningún cliente tiene reservas no canceladas en esa aventura");
                }
                for (Cliente cliente : clientes) {
                    imprimirCliente(cliente);
                }
            }
            tr.commit();
        } catch (Exception e) {
            if (tr != null && tr.isActive()) {
                tr.rollback();
            }
            System.out.println("Error en la recuperación "
                    + e.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }

    
    private static void reservaClienteEnTurno() {
        Scanner teclado = new Scanner(System.in);

        //Pedir los datos
        System.out.print("Identificador del cliente: ");
        String idCliente = teclado.nextLine();
        System.out.print("Identificador del turno: ");
        String idTurno = teclado.nextLine();
        Session sesion = null;
        Transaction tr = null;
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            sesion = sessionFactory.openSession();
            tr = sesion.beginTransaction();
            //lo que haga

            Cliente cliente = sesion.find(Cliente.class, idCliente);
            Turno turno = sesion.find(Turno.class, idTurno);

            if (cliente == null || turno == null) {
                System.out.println("El cliente o el turno no existen");
            } else {
                //el cliente tiene reserva en el turno?
                boolean yaReservado = false;
                int confirmada = 0;
                for (Reserva reserva : turno.getReservas()) {
                    if (reserva.getCliente().getIdCliente().equals(idCliente)) {
                        yaReservado = true;
                    }
                    if (reserva.getEstado().equals("CONFIRMADA")) {

                        confirmada++;
                    }
                }
                int edadEnTurno = Period.between(cliente.getFechaNacimiento(),
                        turno.getFechaHora().toLocalDate()).getYears(); // toLocalDate(): quita la hora; getYears(): años cumplidos

                if (yaReservado) {
                    System.out.println("El cliente ya tiene una reserva en ese turno");
                } else if (!LocalDateTime.now().isBefore(turno.getFechaHora())) {        // R6
                    System.out.println("El turno ya ha empezado o ha pasado (R6)");
                } else if (edadEnTurno < turno.getAventura().getEdadMinima()) {          // R7
                    System.out.println("El cliente no tiene la edad mínima de la aventura (R7)");
                } else if (confirmada >= turno.getCapacidad()) {                        // R8
                    System.out.println("El turno está completo (R8)");
                } else {
                    // Todo correcto: crear la reserva
                    BigDecimal importe = turno.getAventura().getPrecio();   // R9: precio actual de la aventura

                    // R13: siguiente id libre con formato Rnnnn
                    String max = sesion.createQuery("SELECT MAX(r.idReserva) FROM Reserva r", String.class)
                            .getSingleResult();
                    int siguiente = (max == null) ? 1 : Integer.parseInt(max.substring(1)) + 1; // substring(1) quita la R
                    String idReserva = String.format("R%04d", siguiente);   // %04d: 4 cifras con ceros 

                    Reserva nueva = new Reserva(idReserva, LocalDateTime.now(), importe, "CONFIRMADA");
                    nueva.setCliente(cliente);
                    nueva.setTurno(turno);
                    cliente.getReservas().add(nueva);
                    turno.getReservas().add(nueva);
                    sesion.persist(nueva); // programa el INSERT de la NUEVA reserva
                    System.out.println("Reserva " + idReserva + " creada con importe " + importe + " €");

                }
            }

            tr.commit();
        } catch (Exception e) {
            if (tr != null && tr.isActive()) {
                tr.rollback();
            }
            System.out.println("Error en la recuperación "
                    + e.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }

    private static void cancelarReserva() {
        Scanner teclado = new Scanner(System.in);

        //Pedir los datos
        System.out.print("Identificador del cliente: ");
        String idCliente = teclado.nextLine();
        System.out.print("Identificador del turno: ");
        String idTurno = teclado.nextLine();
        Session sesion = null;
        Transaction tr = null;
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            sesion = sessionFactory.openSession();
            tr = sesion.beginTransaction();
            //lo que haga

            Cliente cliente = sesion.find(Cliente.class, idCliente);
            Turno turno = sesion.find(Turno.class, idTurno);

            if (cliente == null || turno == null) {
                System.out.println("El cliente o el turno no existen");
            } else {
                Reserva miReserva=null;
                for (Reserva reserva : turno.getReservas()) {
                    if(reserva.getCliente().getIdCliente().equals(idCliente)){
                        miReserva=reserva;
                    }
                    
                }
                if(miReserva==null){
                    System.out.println("El cliente no tiene ninguna reserva en ese turno");
                }else if (miReserva.getEstado().equals("CANCELADA")){
                    System.out.println("La reserva ya está cancelada");
                }else{
                    miReserva.setEstado("CANCELADA");
                    System.out.println("Reserva " +miReserva.getIdReserva()+" cancelada");
                }
            }

            tr.commit();
        } catch (Exception e) {
            if (tr != null && tr.isActive()) {
                tr.rollback();
            }
            System.out.println("Error en la recuperación "
                    + e.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }

    private static void agendaGuia() {

     Scanner teclado = new Scanner(System.in);

        //Pedir los datos
        System.out.print("Email del guia: ");
        String email = teclado.nextLine();
        Session sesion = null;
        Transaction tr = null;
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            sesion = sessionFactory.openSession();
            tr = sesion.beginTransaction();
            //lo que haga

            List<Guia> guias = sesion.createNamedQuery("Guia.findByEmail", Guia.class)
                    .setParameter("email", email).getResultList();

            if (guias.isEmpty()) {
                System.out.println("No existe ningun guia con ese email");
            } else {
                        // Proyección: la consulta trae SOLO nombre de la aventura, fecha/hora y duración, ordenados
            List<Object[]> turnos = sesion.createQuery(
                    "SELECT t.aventura.nombre, t.fechaHora, t.aventura.duracion FROM Turno t WHERE t.guia.email = :email "
                            + "ORDER BY t.fechaHora", Object[].class)   // ORDER BY: de la fecha más antigua a la más reciente
                .setParameter("email", email).getResultList();

            if (turnos.isEmpty()) {
                System.out.println("El guía no tiene turnos asignados");
            }

            
            for (Object[] fila : turnos) {
                LocalDateTime inicio = (LocalDateTime) fila[1];
                short duracion = (Short) fila[2];                  // duración en minutos
                LocalDateTime fin = inicio.plusMinutes(duracion);  // la hora de fin se calcula en la aplicación

                System.out.println("Aventura: " + fila[0]
                        +" Fecha: " + inicio.getDayOfMonth() + "/" + inicio.getMonthValue() + "/" + inicio.getYear()
                        + " Hora de inicio: " + inicio.getHour() + ":" + inicio.getMinute()
                        + " Hora de fin: " + fin.getHour() + ":" + fin.getMinute()
                );
            }
        }
            tr.commit();
        } catch (Exception e) {
            if (tr != null && tr.isActive()) {
                tr.rollback();
            }
            System.out.println("Error en la recuperación "
                    + e.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }
/*
    Aventura: getIdAventura, getTurnos
    Cliente: getIdCliente, getReservas
    Guia: getIdGuia, getTurnos
    Reserva: getIdReserva, getCliente, getTurno
    Turno: getIdTurno, getAventuras, getReservas
     */
    private static void reservasImportes() {
             Scanner teclado = new Scanner(System.in);

        //Pedir los datos
        System.out.print("Identificador del cliente: ");
        String idCliente = teclado.nextLine();
       
        Session sesion = null;
        Transaction tr = null;
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            sesion = sessionFactory.openSession();
            tr = sesion.beginTransaction();
            //lo que haga

            Cliente cliente = sesion.find(Cliente.class, idCliente);

            if (cliente == null) {
                System.out.println("El cliente no existe");
            } else {
                //tengo cliente, quiero reservas, aventura, turno
                
                for (Reserva reserva : cliente.getReservas()) {
                    if (!reserva.getEstado().equals("CANCELADA")){
                        Turno miTurno=reserva.getTurno();
                        Aventura miAventura=miTurno.getAventura();
                    System.out.println("Nombre de la aventura: "+miAventura.getNombre()+
                            " fecha: "+miTurno.getFechaHora().getDayOfMonth()+ "/"+miTurno.getFechaHora().getMonthValue()+ "/"+miTurno.getFechaHora().getYear()+
                            " hora del turno: "+ miTurno.getFechaHora().getHour()+"."+miTurno.getFechaHora().getMinute()+
                            " importe: "+reserva.getImporte());
                }
                    
                }
                
            }

            tr.commit();
        } catch (Exception e) {
            if (tr != null && tr.isActive()) {
                tr.rollback();
            }
            System.out.println("Error en la recuperación "
                    + e.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }

    private static void clientesMayores() {
        Scanner teclado = new Scanner(System.in);

        //Pedir los datos
        System.out.print("Edad: ");
        int edadMinima = Integer.parseInt(teclado.nextLine());
        Session sesion = null;
        Transaction tr = null;
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            sesion = sessionFactory.openSession();
            tr = sesion.beginTransaction();
            //lo que haga
           
            LocalDate fechaNacimiento=LocalDate.now().minusYears(edadMinima);
            List<Cliente> clientes = sesion.createNamedQuery("Cliente.findAll", Cliente.class)
                    .getResultList();
           
            int encontrados=0;
            for(Cliente cliente: clientes){
                int edad=Period.between(cliente.getFechaNacimiento(), LocalDate.now()).getYears();
                if (edad>edadMinima) {
                    imprimirCliente(cliente);
                    encontrados++;
                }
            }  
            if(encontrados==0){
                    System.out.println("No hay clientes mayores de " + edadMinima + " años");

            }
            
            tr.commit();
        } catch (Exception e) {
            if (tr != null && tr.isActive()) {
                tr.rollback();
            }
            System.out.println("Error en la recuperación "
                    + e.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }
}
