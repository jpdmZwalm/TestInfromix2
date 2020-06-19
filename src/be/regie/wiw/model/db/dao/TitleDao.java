package be.regie.wiw.model.db.dao;

import be.regie.wiw.model.db.entity.Degree;
import be.regie.wiw.model.db.entity.Title;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class TitleDao extends AbstractDao2<Title> {

    public TitleDao(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public String getTableName() {
        return Title.class.getSimpleName();
    }

    @Override
    public void persist(Title title) {
        //beginTransaction();
        entityManager.persist(title);
        //commitTransaction();
    }

    @Override
    public Title find(int id) {
        return entityManager.find(Title.class, id);
    }

    @Override
    public List<Title> findAll() {
        List<Title> allTitle =
                entityManager.createQuery("SELECT t FROM Title t").getResultList();
        return allTitle;
    }

    @Override
    public void update(Title title) {
        entityManager.merge(title);
    }

    @Override
    public void remove(int id) {
        Title title = entityManager.find(Title.class, id);
        entityManager.remove(title);
    }

    @Override
    public boolean exists(Title title) throws PartialExistingException {
        boolean existsNL = false;
        TypedQuery queryNL = entityManager.createNamedQuery("Title.ExistsNL", Title.class);
        queryNL.setParameter("shortNL", title.getShortNL());
        queryNL.setParameter("longNL", title.getLongNL());
        List<Title> theListNL = queryNL.getResultList();
        if (theListNL.size() > 0) {
            existsNL = true;
        }

        boolean existsFR = false;
        TypedQuery queryFR = entityManager.createNamedQuery("Title.ExistsFR", Title.class);
        queryFR.setParameter("shortFR", title.getShortFR());
        queryFR.setParameter("longFR", title.getLongFR());
        List<Title> theListFR = queryFR.getResultList();
        if (theListFR.size() > 0) {
            existsFR = true;
        }

        if (existsNL && !existsFR) {
            throw new PartialExistingException("FOUT : NL bestaat en FR niet : " + title.toString());
        }

        if (!existsNL && existsFR) {
            throw new PartialExistingException("FOUT : FR bestaat en NL niet : " + title.toString());
        }

        return existsNL || existsFR;
    }

    @Override
    public List<Title> findField(String fieldName, Integer value) throws Exception {
        List<Title> results = null;
        switch (fieldName) {
            case "ti_old_id":
                TypedQuery<Title> query = entityManager.createNamedQuery("Title.oldId", Title.class);
                query.setParameter("oldId", value);
                results = query.getResultList();
                break;
            default:
                throw new Exception("Unknown field");
        }
        return results;
    }

    @Override
    public Title findField1(String fieldName, Integer value) throws Exception {
        List<Title> results = findField(fieldName, value);
        switch (results.size()) {
            case 0 : return null;
            case 1 : return results.get(0);
            default: throw new Exception("Too many results");
        }
    }

}

