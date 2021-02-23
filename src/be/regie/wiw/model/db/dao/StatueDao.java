package be.regie.wiw.model.db.dao;

import be.regie.wiw.model.db.entity.Statute;

import javax.persistence.EntityManager;
import java.util.List;

public class StatueDao extends AbstractDao2<Statute> {

    public StatueDao(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public String getTableName() {
        return Statute.class.getSimpleName();
    }

    @Override
    public void persist(Statute statue) {
        //beginTransaction();
        entityManager.persist(statue);
        //commitTransaction();
    }

    @Override
    public Statute find(int id) {
        return entityManager.find(Statute.class, id);
    }

    @Override
    public List<Statute> findAll() {
        List<Statute> allStatue =
                entityManager.createQuery("SELECT s FROM Statute s").getResultList();
        return allStatue;
    }

    @Override
    public void update(Statute statue) {
        entityManager.merge(statue);
    }

    @Override
    public void remove(int id) {
        Statute statue = entityManager.find(Statute.class, id);
        entityManager.remove(statue);
    }

}

