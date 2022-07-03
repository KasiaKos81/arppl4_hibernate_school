package pl.sda.arppl4.hibernateschool.dao;

import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pl.sda.arppl4.hibernateschool.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class GenericDao <T> {

    public void addStudent(T addedObject) {
        SessionFactory fabrykaPolaczen = HibernateUtil.getSessionFactory();

        Transaction transaction = null;
        try (Session session = fabrykaPolaczen.openSession()) {
            transaction = session.beginTransaction();

            session.merge(addedObject);

            transaction.commit();
        } catch (SessionException sessionException) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
    public void removeStudent(T removedObject) {
        SessionFactory fabrykaPolaczen = HibernateUtil.getSessionFactory();
        try (Session session = fabrykaPolaczen.openSession()) {
            Transaction transaction = session.beginTransaction();

            session.remove(removedObject);

            transaction.commit();
        }
    }
    public void updateStudent(T updatedObject) {
        SessionFactory fabrykaPolaczen = HibernateUtil.getSessionFactory();

        Transaction transaction = null;
        try (Session session = fabrykaPolaczen.openSession()) {
            transaction = session.beginTransaction();

            session.merge(updatedObject);

            transaction.commit();
        } catch (SessionException sessionException) {
            if (transaction != null){
                transaction.rollback();
            }
        }
    }
    public List<T> getAllStudents(Class<T> classType) {
        List<T> list = new ArrayList<>();

        SessionFactory fabrykaPolaczen = HibernateUtil.getSessionFactory();
        try (Session session = fabrykaPolaczen.openSession()) {
            TypedQuery<T> request = session.createQuery("from " + classType.getName(), classType);
            List<T> requestResult = request.getResultList();

            list.addAll(requestResult);
        } catch (SessionException sessionException) {
            System.err.println("Error.");
        }

        return list;
    }


}
