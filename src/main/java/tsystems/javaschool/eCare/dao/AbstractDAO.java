package tsystems.javaschool.eCare.dao;

import java.util.List;

public abstract class AbstractDAO<T>  {
    // Abstract methods for implementation it in inheritors.
    protected abstract T doSaveOrUpdate(T t);
    protected abstract T doGetById(Long id);
    protected abstract void doDelete(T t);
    protected abstract List<T> doGetAll();
    protected abstract void doDeleteAll();
    protected abstract Long doSize();

    public T saveOrUpdate(T t) {
        return doSaveOrUpdate(t);
    }

    public T getById(Long id) {
        return doGetById(id);
    }

    public void delete(T t) {
        doDelete(t);
    }

    public List<T> getAll() {
        return doGetAll();
    }

    public void deleteAll() {
        doDeleteAll();
    }

    public Long size() {
        return doSize();
    }
}
