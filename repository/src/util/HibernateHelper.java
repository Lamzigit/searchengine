package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by 林志杰 on 2017/3/27.
 */
public class HibernateHelper {
    private static SessionFactory sessionFactory;

    static{
        Configuration configuration = new Configuration();
        configuration.configure("/hibernate.cfg.xml");
        sessionFactory = configuration.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory(){return sessionFactory;}
}
