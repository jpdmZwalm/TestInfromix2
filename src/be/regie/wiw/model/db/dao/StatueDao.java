package be.regie.wiw.model.db.dao;

import be.regie.wiw.model.db.entity.Statue;

import javax.persistence.EntityManager;
import java.util.List;

public class StatueDao extends AbstractDao2<Statue> {

    public StatueDao(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public String getTableName() {
        return Statue.class.getSimpleName();
    }

    @Override
    public void persist(Statue statue) {
        //beginTransaction();
        entityManager.persist(statue);
        //commitTransaction();
    }

    @Override
    public Statue find(int id) {
        return entityManager.find(Statue.class, id);
    }

    @Override
    public List<Statue> findAll() {
        List<Statue> allStatue =
                entityManager.createQuery("SELECT s FROM Statue s").getResultList();
        return allStatue;
    }

    @Override
    public void update(Statue statue) {
        entityManager.merge(statue);
    }

    @Override
    public void remove(int id) {
        Statue statue = entityManager.find(Statue.class, id);
        entityManager.remove(statue);
    }

}

