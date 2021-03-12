package be.regie.wiw.model.db.dao;

import be.regie.wiw.model.db.entity.CClass;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ClassDao extends AbstractDao2<CClass> {

    public ClassDao(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public String getTableName() {
        return CClass.class.getSimpleName();
    }

    @Override
    public void persist(CClass aClass) {
        //beginTransaction();
        entityManager.persist(aClass);
        //commitTransaction();
    }

    @Override
    public CClass find(int id) {
        return entityManager.find(CClass.class, id);
    }

    @Override
    public List<CClass> findAll() {
        List<CClass> allClass =
                entityManager.createQuery("SELECT c FROM CClass c").getResultList();
        return allClass;
    }

    @Override
    public void update(CClass aClass) {
        entityManager.merge(aClass);
    }

    @Override
    public void remove(int id) {
        CClass aClass = entityManager.find(CClass.class, id);
        entityManager.remove(aClass);
    }

    @Override
    public boolean exists(CClass aClass) throws PartialExistingException {
        boolean existsNL = false;
        TypedQuery queryNL = entityManager.createNamedQuery("CClass.ExistsNL", CClass.class);
        //queryNL.setParameter("shortNL", aClass.getShortNL());
        queryNL.setParameter("longNL", aClass.getLongNL());
        List<CClass> theListNL = queryNL.getResultList();
        if (theListNL.size() > 0) {
            existsNL = true;
        }

        boolean existsFR = false;
        TypedQuery queryFR = entityManager.createNamedQuery("CClass.ExistsFR", CClass.class);
        //queryFR.setParameter("shortFR", aClass.getShortFR());
        queryFR.setParameter("longFR", aClass.getLongFR());
        List<CClass> theListFR = queryFR.getResultList();
        if (theListFR.size() > 0) {
            existsFR = true;
        }

        if (existsNL && !existsFR) {
            throw new PartialExistingException("FOUT : NL bestaat en FR niet : " + aClass.toString());
        }

        if (!existsNL && existsFR) {
            throw new PartialExistingException("FOUT : FR bestaat en NL niet : " + aClass.toString());
        }

        return existsNL || existsFR;
    }

    @Override
    public List<CClass> findField(String fieldName, Integer value) throws Exception {
        List<CClass> results = null;
        switch (fieldName) {
            case "cla_old_id":
                TypedQuery<CClass> query = entityManager.createNamedQuery("CClass.old_id", CClass.class);
                query.setParameter("old_id", value);
                results = query.getResultList();
                break;
            default:
                throw new Exception("Unknown field");
        }
        return results;
    }

    @Override
    public CClass findField1(String fieldName, String value) throws Exception {
        List<CClass> results = findField(fieldName, value);
        switch (results.size()) {
            case 0 : throw new Exception("No results");
            case 1 : return results.get(0);
            default: throw new Exception("Too many results");
        }
    }

    public CClass find(String komschr_nl, String omschr_nl, String komschr_fr, String omschr_fr) throws Exception {
        if (komschr_nl == null && omschr_nl == null && komschr_fr == null && omschr_fr == null)
            return null;
        TypedQuery<CClass> query = entityManager.createNamedQuery("CClass.omschr", CClass.class);
        query.setParameter("shortNL", komschr_nl);
        query.setParameter("longNL",  omschr_nl);
        query.setParameter("shortFR", komschr_fr);
        query.setParameter("longFR",  omschr_fr);
        List<CClass> results = query.getResultList();
        switch (results.size()) {
            case 0 : throw new Exception("No results");
            case 1 : return results.get(0);
            default: throw new Exception("Too many results");
        }
    }
}

