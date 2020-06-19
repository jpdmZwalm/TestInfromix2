package be.regie.wiw.model.db.dao;

import be.regie.wiw.model.db.entity.HigherFunction;
import be.regie.wiw.model.db.entity.Organisation;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class HigherFunctionDao extends AbstractDao2<HigherFunction> {

    public HigherFunctionDao(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public String getTableName() {
        return HigherFunction.class.getSimpleName();
    }

    @Override
    public void persist(HigherFunction higherFunction) {
        //beginTransaction();
        entityManager.persist(higherFunction);
        //commitTransaction();
    }

    @Override
    public HigherFunction find(int id) {
        return entityManager.find(HigherFunction.class, id);
    }

    @Override
    public List<HigherFunction> findAll() {
        List<HigherFunction> allHigherFunction =
                entityManager.createQuery("SELECT h FROM HigherFunction h").getResultList();
        return allHigherFunction;
    }

    @Override
    public void update(HigherFunction higherFunction) {
        entityManager.merge(higherFunction);
    }

    @Override
    public void remove(int id) {
        HigherFunction higherFunction = entityManager.find(HigherFunction.class, id);
        entityManager.remove(higherFunction);
    }

    @Override
    public List<HigherFunction> findField(String fieldName, String value) throws Exception {
        List<HigherFunction> results = null;
        switch (fieldName) {
            case "hf_omschr_nl":
                TypedQuery<HigherFunction> queryNL = entityManager.createNamedQuery("HigherFunction.hf_omschr_nl", HigherFunction.class);
                queryNL.setParameter("hf_omschr_nl", value);
                results = queryNL.getResultList();
                break;
            case "hf_omschr_fr":
                TypedQuery<HigherFunction> queryFR = entityManager.createNamedQuery("HigherFunction.hf_omschr_fr", HigherFunction.class);
                queryFR.setParameter("hf_omschr_fr", value);
                results = queryFR.getResultList();
                break;
            default:
                throw new Exception("Unknown field");
        }
        return results;
    }

    @Override
    public HigherFunction findField1(String fieldName, String value) throws Exception {
        if (value == null)
            return null;
        List<HigherFunction> results = findField(fieldName, value);
        switch (results.size()) {
            case 0 : throw new Exception("No results");
            case 1 : return results.get(0);
            default: throw new Exception("Too many results");
        }
    }

}

