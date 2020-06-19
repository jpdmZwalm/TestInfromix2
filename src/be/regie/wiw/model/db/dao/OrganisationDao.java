package be.regie.wiw.model.db.dao;

import be.regie.wiw.model.db.entity.Organisation;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class OrganisationDao extends AbstractDao2<Organisation> {

    public OrganisationDao(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public String getTableName() {
        return Organisation.class.getSimpleName();
    }

    @Override
    public void persist(Organisation organisation) {
        //beginTransaction();
        entityManager.persist(organisation);
        //commitTransaction();
    }

    @Override
    public Organisation find(int id) {
        return entityManager.find(Organisation.class, id);
    }

    @Override
    public List<Organisation> findAll() {
        List<Organisation> allOrganisation =
                entityManager.createQuery("SELECT o FROM Organisation o").getResultList();
        return allOrganisation;
    }

    @Override
    public void update(Organisation organisation) {
        entityManager.merge(organisation);
    }

    @Override
    public void remove(int id) {
        Organisation organisation = entityManager.find(Organisation.class, id);
        entityManager.remove(organisation);
    }

    @Override
    public List<Organisation> findField(String fieldName, Integer value) throws Exception {
        List<Organisation> results = null;
        switch (fieldName) {
            case "org_old_id":
                TypedQuery<Organisation> query = entityManager.createNamedQuery("Organisation.org_old_id", Organisation.class);
                query.setParameter("org_old_id", value);
                results = query.getResultList();
                break;
            default:
                throw new Exception("Unknown field");
        }
        return results;
    }

    @Override
    public Organisation findField1(String fieldName, Integer value) throws Exception {
        List<Organisation> results = findField(fieldName, value);
        switch (results.size()) {
            case 0 : throw new Exception("No results");
            case 1 : return results.get(0);
            default: throw new Exception("Too many results");
        }
    }

    @Override
    public boolean exists(Organisation organisation) throws PartialExistingException {
        boolean existsOldOrgId = false;
        TypedQuery queryID = entityManager.createNamedQuery("Organisation.ExistsOldOrgId", Organisation.class);
        queryID.setParameter("oldOrgId", organisation.getOldOrgId());
        List<Organisation> theListID = queryID.getResultList();
        if (theListID.size() > 0) {
            existsOldOrgId = true;
        }


        boolean existsNL = false;
        TypedQuery queryNL = entityManager.createNamedQuery("Organisation.ExistsNL", Organisation.class);
        queryNL.setParameter("shortNL", organisation.getShortNL());
        queryNL.setParameter("longNL", organisation.getLongNL());
        List<Organisation> theListNL = queryNL.getResultList();
        if (theListNL.size() > 0) {
            existsNL = true;
        }

        boolean existsFR = false;
        TypedQuery queryFR = entityManager.createNamedQuery("Organisation.ExistsFR", Organisation.class);
        queryFR.setParameter("shortFR", organisation.getShortFR());
        queryFR.setParameter("longFR", organisation.getLongFR());
        List<Organisation> theListFR = queryFR.getResultList();
        if (theListFR.size() > 0) {
            existsFR = true;
        }

        boolean ok = existsOldOrgId && existsNL && existsFR;
        ok = ok || (!existsOldOrgId && !existsNL && !existsFR);

        if (!ok) {
            throw new PartialExistingException("FOUT : bestaat wel en niet : " + organisation.toString());
        }

        return existsOldOrgId && existsNL && existsFR;
    }
}

