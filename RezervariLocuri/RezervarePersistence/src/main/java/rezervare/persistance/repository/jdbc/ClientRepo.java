package rezervare.persistance.repository.jdbc;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import rezervare.model.Client;
import rezervare.model.Manager;
import rezervare.persistance.repository.ClientRepository;

public class ClientRepo implements ClientRepository<String, String> {
    private SessionFactory sessionFactory;

    public ClientRepo(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Client verificareCont(String numeUtilizator, String parola) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                Client c = session.createQuery("from Client where NumeUtilizator = :u and Parola = :p" ,Client.class)
                        .setString("u", numeUtilizator)
                        .setString("p", parola)
                        .getSingleResult();
                tx.commit();
                return c;
            }catch(RuntimeException ex){
                if(tx != null)
                    tx.rollback();
            }
        }
        return null;
    }

    @Override
    public Client findOne(String numeUtilizator) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                Client c = session.createQuery("from Client where NumeUtilizator = :u" , Client.class)
                        .setString("u", numeUtilizator)
                        .getSingleResult();
                tx.commit();
                return c;
            }catch (RuntimeException ex){
                if(tx != null)
                    tx.rollback();
            }
        }
        return null;
    }
}
