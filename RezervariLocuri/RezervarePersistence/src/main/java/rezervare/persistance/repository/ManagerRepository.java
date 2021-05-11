package rezervare.persistance.repository;

import org.hibernate.SessionFactory;
import rezervare.model.Manager;

public interface ManagerRepository<E,T> {
    Manager verificareCont(E numeUtilizator, T parola);
    //void setSessionFactory(SessionFactory sessionFactory);
}
