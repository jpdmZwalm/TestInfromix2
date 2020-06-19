package be.regie.wiw.model.db.dao;

import be.regie.wiw.model.db.entity.Address;
import be.regie.wiw.model.db.entity.Approach;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ApproachDao extends AbstractDao2<Approach> {

    public ApproachDao(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public String getTableName() {
        return Approach.class.getSimpleName();
    }

    @Override
    public void persist(Approach approach) {
        //beginTransaction();
        entityManager.persist(approach);
        //commitTransaction();
    }

    @Override
    public Approach find(int id) {
        return entityManager.find(Approach.class, id);
    }

    @Override
    public List<Approach> findAll() {
        List<Approach> allApproach =
                entityManager.createQuery("SELECT a FROM Approach a").getResultList();
        return allApproach;
    }

    @Override
    public void update(Approach approach) {
        entityManager.merge(approach);
    }

    @Override
    public void remove(int id) {
        Approach approach = entityManager.find(Approach.class, id);
        entityManager.remove(approach);
    }

    @Override
    public List<Approach> findField(String fieldName, Integer value) throws Exception {
        List<Approach> results = null;
        switch (fieldName) {
            case "app_old_id":
                TypedQuery<Approach> query = entityManager.createNamedQuery("Approach.old_id", Approach.class);
                query.setParameter("old_id", value);
                results = query.getResultList();
                break;
            default:
                throw new Exception("Unknown field");
        }
        return results;
    }

    @Override
    public Approach findField1(String fieldName, Integer value) throws Exception {
        List<Approach> results = findField(fieldName, value);
        switch (results.size()) {
            case 0 : throw new Exception("No results");
            case 1 : return results.get(0);
            default: throw new Exception("Too many results");
        }
    }

}

