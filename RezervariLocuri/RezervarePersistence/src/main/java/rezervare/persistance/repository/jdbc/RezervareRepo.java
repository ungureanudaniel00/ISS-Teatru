package rezervare.persistance.repository.jdbc;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import rezervare.model.Rezervare;
import rezervare.persistance.repository.RezervareRepository;

import java.util.List;

public class RezervareRepo implements RezervareRepository<Rezervare> {

    static SessionFactory sessionFactory;

    public RezervareRepo(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Rezervare> findAll() {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Rezervare> rezervari = session.createQuery("from Rezervare", Rezervare.class)
                        .list();
                tx.commit();
                return rezervari;
            } catch (RuntimeException ex){
                if(tx != null)
                    tx.rollback();
            }
        }
        return null;
    }

    @Override
    public void save(Rezervare r) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                session.save(r);
                tx.commit();
            }catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
    }
}
