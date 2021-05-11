package rezervare.persistance.repository.jdbc;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import rezervare.model.Manager;
import rezervare.persistance.repository.ManagerRepository;

public class ManagerRepo implements ManagerRepository<String, String> {
    static SessionFactory sessionFactory;

    public ManagerRepo(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    //public ManagerRepo(){}

//    public void setSessionFactory(SessionFactory sessionFactory2){
//        this.sessionFactory = sessionFactory2;
//    }

    @Override
    public Manager verificareCont(String numeUtilizator, String parola) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                Manager m = session.createQuery("from Manager where NumeUtilizator = :u and Parola = :p" ,Manager.class)
                        .setString("u", numeUtilizator)
                        .setString("p", parola)
                        .getSingleResult();
                tx.commit();
                return m;
            }catch(RuntimeException ex){
                if(tx != null)
                    tx.rollback();
            }
        }
        return null;
    }
}
