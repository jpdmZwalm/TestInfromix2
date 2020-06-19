package be.regie.wiw.model.db.dao;

import be.regie.wiw.model.db.entity.Transport;

import javax.persistence.EntityManager;
import java.util.List;

public class TransportDao extends AbstractDao2<Transport> {

    public TransportDao(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public String getTableName() {
        return Transport.class.getSimpleName();
    }

    @Override
    public void persist(Transport transport) {
        //beginTransaction();
        entityManager.persist(transport);
        //commitTransaction();
    }

    @Override
    public Transport find(int id) {
        return entityManager.find(Transport.class, id);
    }

    @Override
    public List<Transport> findAll() {
        List<Transport> allTransport =
                entityManager.createQuery("SELECT t FROM Transport t").getResultList();
        return allTransport;
    }

    @Override
    public void update(Transport transport) {
        entityManager.merge(transport);
    }

    @Override
    public void remove(int id) {
        Transport transport = entityManager.find(Transport.class, id);
        entityManager.remove(transport);
    }

}

