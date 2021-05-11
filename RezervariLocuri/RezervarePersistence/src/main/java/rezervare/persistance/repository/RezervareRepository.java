package rezervare.persistance.repository;

import rezervare.model.Rezervare;

import java.util.List;

public interface RezervareRepository<E> {
    List<E> findAll();

    void save(Rezervare r);
}
