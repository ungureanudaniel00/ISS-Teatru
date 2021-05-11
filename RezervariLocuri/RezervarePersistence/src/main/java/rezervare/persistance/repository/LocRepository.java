package rezervare.persistance.repository;

import java.util.List;

public interface LocRepository<E> {
    List<E> findAll();

    void updateStare(Integer id);

    void updateLocuriLibere();
}
