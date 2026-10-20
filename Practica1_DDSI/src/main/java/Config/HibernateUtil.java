/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Config;

/**
 *
 * @author Usuario
 */
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
// Se crea una única instancia de SessionFactory al cargar la clase

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml").build();
            Metadata metadata = new MetadataSources(serviceRegistry).getMetadataBuilder().build();
// Devuelve la fábrica de sesiones
            return metadata.getSessionFactoryBuilder().build();
        } catch (Exception ex) {
            System.err.println("Build SessionFactory failed :" + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void close() {
        if ((sessionFactory != null) && (sessionFactory.isClosed() == false)) {
            sessionFactory.close();
        }
    }
}
