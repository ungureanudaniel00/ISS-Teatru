package rezervare.persistance.repository;

import java.util.List;

public interface Repository<ID,E> {
    void save(E entity);
    E findOne(ID id);
    void delete(ID id);
    List<E> findAll();
}
