package app.daos;

import java.util.List;
import java.util.Set;

public interface IDAO<T> {

    T getById(Long id);

    List<T> getAll();

    T create(T t);

    T update(T t);

    void delete(Long id);
}
