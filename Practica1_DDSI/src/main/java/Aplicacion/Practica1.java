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
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import jakarta.persistence.NoResultException; // excepción que lanza getSingleResult() si no hay ninguna fila
import java.util.List;
import java.util.Scanner;
import org.hibernate.query.Query;

/**
 *
 * @author Usuario
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
            Cliente cliente = sesion.find(Cliente.class, "CL001");
            System.out.println(cliente.getNombre());
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
}
