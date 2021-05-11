package rezervare.persistance.repository.jdbc;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import rezervare.model.DaySpectacol;
import rezervare.model.Spectacol;
import rezervare.persistance.repository.SpectacolRepository;

import java.util.List;

public class SpectacolRepo implements SpectacolRepository {
    static SessionFactory sessionFactory;

    public SpectacolRepo(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //public SpectacolRepo(){}

//    public void setSessionFactory(SessionFactory sessionFactory2){
//        this.sessionFactory = sessionFactory2;
//    }

    @Override
    public void save(Spectacol entity) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                session.save(entity);
                tx.commit();
            }catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
    }

    @Override
    public Spectacol findOne(Integer integer) {
        return null;
    }

    @Override
    public void delete(Integer integer) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                Spectacol s = session.createQuery("from Spectacol where Id = :i", Spectacol.class)
                        .setString("i", integer.toString())
                        .getSingleResult();
                session.delete(s);
                tx.commit();
            } catch(RuntimeException ex){
                if(tx != null)
                    tx.rollback();
            }
        }
    }

    @Override
    public List<Spectacol> findAll() {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Spectacol> spectacole = session.createQuery("from Spectacol", Spectacol.class)
                        .list();
                tx.commit();
                return spectacole;
            } catch (RuntimeException ex){
                if(tx != null)
                    tx.rollback();
            }
        }
        return null;
    }

    @Override
    public Spectacol findByName(String spectacolActual) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                Spectacol s = session.createQuery("from Spectacol where Nume = :u" , Spectacol.class)
                        .setString("u", spectacolActual)
                        .getSingleResult();
                tx.commit();
                return s;
            }catch (RuntimeException ex){
                if(tx != null)
                    tx.rollback();
            }
        }
        return null;
    }

    @Override
    public void update(Spectacol spectacol) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Spectacol spectacol2 = session.load(Spectacol.class, spectacol.getId());
                spectacol2.setNume(spectacol.getNume());
                tx.commit();
            } catch (RuntimeException ex){
                if(tx != null)
                    tx.rollback();
            }
        }
    }

    @Override
    public void setSpectacol(Spectacol spectacolSetat) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                DaySpectacol spectacol2 = session.load(DaySpectacol.class, spectacolSetat.getId());
                spectacol2.setNume(spectacolSetat.getNume());
                tx.commit();
            } catch (RuntimeException ex){
                if(tx != null)
                    tx.rollback();
            }
        }
    }

    @Override
    public DaySpectacol findDaySpectacol() {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                DaySpectacol ds = session.createQuery("from DaySpectacol where Id = 1" , DaySpectacol.class)
                        .getSingleResult();
                tx.commit();
                return ds;
            }catch (RuntimeException ex){
                if(tx != null)
                    tx.rollback();
            }
        }
        return null;
    }
}
