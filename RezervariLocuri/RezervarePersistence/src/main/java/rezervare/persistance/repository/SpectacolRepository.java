package rezervare.persistance.repository;

import rezervare.model.DaySpectacol;
import rezervare.model.Spectacol;

public interface SpectacolRepository extends Repository<Integer, Spectacol> {
    Spectacol findByName(String spectacolActual);

    void update(Spectacol spectacol);

    void setSpectacol(Spectacol spectacolSetat);

    DaySpectacol findDaySpectacol();
    //void setSessionFactory(SessionFactory sessionFactory);

}
