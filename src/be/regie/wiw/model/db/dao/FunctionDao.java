package be.regie.wiw.model.db.dao;

import be.regie.wiw.model.db.entity.CClass;
import be.regie.wiw.model.db.entity.Function;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class FunctionDao extends AbstractDao2<Function> {

    public FunctionDao(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public String getTableName() {
        return Function.class.getSimpleName();
    }

    @Override
    public void persist(Function Function) {
        //beginTransaction();
        entityManager.persist(Function);
        //commitTransaction();
    }

    @Override
    public Function find(int id) {
        return entityManager.find(Function.class, id);
    }

    @Override
    public List<Function> findAll() {
        List<Function> allFunction =
                entityManager.createQuery("SELECT d FROM Function d").getResultList();
        return allFunction;
    }

    @Override
    public void update(Function function) {
        entityManager.merge(function);
    }

    @Override
    public void remove(int id) {
        Function function = entityManager.find(Function.class, id);
        entityManager.remove(function);
    }

    @Override
    public boolean exists(Function function) throws PartialExistingException {
        boolean existsNL = false;
        TypedQuery queryNL = entityManager.createNamedQuery("Function.ExistsNL", Function.class);
        queryNL.setParameter("descrNL", function.getDescrNL());
        List<Function> theListNL = queryNL.getResultList();
        if (theListNL.size() > 0) {
            existsNL = true;
        }

        boolean existsFR = false;
        TypedQuery queryFR = entityManager.createNamedQuery("Function.ExistsFR", Function.class);
        queryFR.setParameter("descrFR", function.getDescrFR());
        List<Function> theListFR = queryFR.getResultList();
        if (theListFR.size() > 0) {
            existsFR = true;
        }

        if (existsNL && !existsFR) {
            throw new PartialExistingException("FOUT : NL bestaat en FR niet : " + function.toString());
        }

        if (!existsNL && existsFR) {
            throw new PartialExistingException("FOUT : FR bestaat en NL niet : " + function.toString());
        }

        return existsNL || existsFR;
    }

    @Override
    public List<Function> findField(String fieldName, String value) throws Exception {
        List<Function> results = null;
        switch (fieldName) {
            case "fct_descr_nl":
                TypedQuery<Function> queryNL = entityManager.createNamedQuery("Function.ExistsNL", Function.class);
                queryNL.setParameter("descrNL", value);
                results = queryNL.getResultList();
                break;
            case "fct_descr_fr":
                TypedQuery<Function> queryFR = entityManager.createNamedQuery("Function.ExistsFR", Function.class);
                queryFR.setParameter("descrFR", value);
                results = queryFR.getResultList();
                break;
            default:
                throw new Exception("Unknown field");
        }
        return results;
    }

    @Override
    public Function findField1(String fieldName, String value) throws Exception {
        if (value == null)
            return null;
        List<Function> results = findField(fieldName, value);
        switch (results.size()) {
            case 0 : throw new Exception("No results");
            case 1 : return results.get(0);
            default: throw new Exception("Too many results");
        }
    }


}

