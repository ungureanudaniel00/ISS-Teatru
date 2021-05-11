package rezervare.persistance.repository;

import rezervare.model.Client;
import rezervare.model.Manager;

public interface ClientRepository<E,T> {
    Client verificareCont(E numeUtilizator, T parola);

    Client findOne(String numeUtilizator);
}
