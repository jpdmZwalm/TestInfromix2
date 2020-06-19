package be.regie.wiw.model.db.dao;

import be.regie.wiw.model.db.entity.Organisation;
import org.apache.commons.lang.NotImplementedException;

import javax.persistence.EntityManager;
import java.util.List;

public abstract class AbstractDao2<T> {
    protected EntityManager entityManager;

    /**
     * Constructor
     * @param entityManager
     */
    public AbstractDao2(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public abstract String getTableName();

    /**
     * Een Object bewaren in de database.
     * @param entity
     */
    public abstract void persist(T entity);

    /**
     * Opvragen volgens de primary Key
     * @param id
     * @return
     */
    public abstract T find(int id);

    /**
     * Alle items opvragen
     * @return
     */
    public abstract List<T> findAll();


    /**
     * Een update doen WHERE id = ...
     * @param entity
     */
    public abstract void update(T entity);

    /**
     * Delete
     * @param id
     */
    public abstract void remove(int id);

    public boolean exists(T aClass) throws PartialExistingException {
        return true;
    }

    public List<T> findField(String fieldName, Integer value) throws Exception {
        throw new NotImplementedException();
    }

    public T findField1(String fieldName, Integer value) throws Exception {
        throw new NotImplementedException();
    }

    public List<T> findField(String fieldName, String value) throws Exception {
        throw new NotImplementedException();
    }

    public T findField1(String fieldName, String value) throws Exception {
        throw new NotImplementedException();
    }

}



