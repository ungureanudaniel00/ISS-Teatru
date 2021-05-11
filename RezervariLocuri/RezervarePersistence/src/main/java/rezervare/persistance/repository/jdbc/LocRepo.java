package rezervare.persistance.repository.jdbc;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import rezervare.model.Loc;
import rezervare.persistance.repository.LocRepository;

import java.util.List;

public class LocRepo implements LocRepository<Loc> {

    static SessionFactory sessionFactory;

    public LocRepo(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<Loc> findAll() {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Loc> locuri = session.createQuery("from Loc", Loc.class)
                        .list();
                tx.commit();
                return locuri;
            } catch (RuntimeException ex){
                if(tx != null)
                    tx.rollback();
            }
        }
        return null;
    }

    @Override
    public void updateStare(Integer id) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Loc loc = session.load(Loc.class, id);
                loc.setStare("Ocupat");
                tx.commit();
            } catch (RuntimeException ex){
                if(tx != null)
                    tx.rollback();
            }
        }
    }

    @Override
    public void updateLocuriLibere() {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.createQuery("update Loc set stare = 'Liber'").executeUpdate();
                //loc.setStare("Ocupat");
                tx.commit();
            } catch (RuntimeException ex){
                if(tx != null)
                    tx.rollback();
            }
        }
    }
}
