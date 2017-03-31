package dao;

import entity.Employ;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateHelper;

import java.util.List;

/**
 * Created by 林志杰 on 2017/3/27.
 */
public class EmployDaoImp {
    SessionFactory sessionFactory = HibernateHelper.getSessionFactory();

    public void delete(Employ employ){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(employ);
    }

    public int save(Employ employ) {
        int id;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(employ);
        id = employ.getId();
        transaction.commit();
        session.close();
        return id;
    }

    public Employ getEmployById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "from Employ e where e.id = ?";
        Query query = session.createQuery(hql);
        query.setParameter(0,id);
        Employ employ = (Employ)query.uniqueResult();
        transaction.commit();
        session.close();
        return employ;
    }

    /**
     * 分页查询
     */
    public List<Employ> getAPage(int pagenum){
        return null;
    }
}
