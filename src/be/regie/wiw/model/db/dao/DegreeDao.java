package be.regie.wiw.model.db.dao;

import be.regie.wiw.model.db.entity.Degree;
import be.regie.wiw.model.db.entity.Function;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class DegreeDao extends AbstractDao2<Degree> {

    public DegreeDao(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public String getTableName() {
        return Degree.class.getSimpleName();
    }

    @Override
    public void persist(Degree degree) {
        //beginTransaction();
        entityManager.persist(degree);
        //commitTransaction();
    }

    @Override
    public Degree find(int id) {
        return entityManager.find(Degree.class, id);
    }

    @Override
    public List<Degree> findAll() {
        List<Degree> allDegree =
                entityManager.createQuery("SELECT d FROM Degree d").getResultList();
        return allDegree;
    }

    @Override
    public void update(Degree degree) {
        entityManager.merge(degree);
    }

    @Override
    public void remove(int id) {
        Degree degree = entityManager.find(Degree.class, id);
        entityManager.remove(degree);
    }

    @Override
    public boolean exists(Degree degree) throws PartialExistingException {
        boolean existsNL = false;
        TypedQuery queryNL = entityManager.createNamedQuery("Degree.ExistsNL", Degree.class);
        queryNL.setParameter("descrNL", degree.getDescrNL());
        List<Degree> theListNL = queryNL.getResultList();
        if (theListNL.size() > 0) {
            existsNL = true;
        }

        boolean existsFR = false;
        TypedQuery queryFR = entityManager.createNamedQuery("Degree.ExistsFR", Degree.class);
        queryFR.setParameter("descrFR", degree.getDescrFR());
        List<Degree> theListFR = queryFR.getResultList();
        if (theListFR.size() > 0) {
            existsFR = true;
        }

        if (existsNL && !existsFR) {
            throw new PartialExistingException("FOUT : NL bestaat en FR niet : " + degree.toString());
        }

        if (!existsNL && existsFR) {
            throw new PartialExistingException("FOUT : FR bestaat en NL niet : " + degree.toString());
        }

        return existsNL || existsFR;
    }

    @Override
    public List<Degree> findField(String fieldName, String value) throws Exception {
        List<Degree> results = null;
        switch (fieldName) {
            case "deg_descr_nl":
                TypedQuery<Degree> queryNL = entityManager.createNamedQuery("Degree.ExistsNL", Degree.class);
                queryNL.setParameter("descrNL", value);
                results = queryNL.getResultList();
                break;
            case "deg_descr_fr":
                TypedQuery<Degree> queryFR = entityManager.createNamedQuery("Degree.ExistsFR", Degree.class);
                queryFR.setParameter("descrFR", value);
                results = queryFR.getResultList();
                break;
            default:
                throw new Exception("Unknown field");
        }
        return results;
    }

    @Override
    public Degree findField1(String fieldName, String value) throws Exception {
        if (value == null)
            return null;
        List<Degree> results = findField(fieldName, value);
        switch (results.size()) {
            case 0 : throw new Exception("No results");
            case 1 : return results.get(0);
            default: throw new Exception("Too many results");
        }
    }

}

