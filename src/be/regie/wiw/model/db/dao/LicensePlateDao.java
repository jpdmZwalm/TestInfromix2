package be.regie.wiw.model.db.dao;

import be.regie.wiw.model.db.entity.LicensePlate;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class LicensePlateDao extends AbstractDao2<LicensePlate> {

    public LicensePlateDao(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public String getTableName() {
        return LicensePlate.class.getSimpleName();
    }

    @Override
    public void persist(LicensePlate licensePlate) {
        //beginTransaction();
        entityManager.persist(licensePlate);
        //commitTransaction();
    }

    @Override
    public LicensePlate find(int id) {
        return entityManager.find(LicensePlate.class, id);
    }

    @Override
    public List<LicensePlate> findAll() {
        List<LicensePlate> allLicensePlate =
                entityManager.createQuery("SELECT l FROM LicensePlate l").getResultList();
        return allLicensePlate;
    }

    @Override
    public void update(LicensePlate licensePlate) {
        entityManager.merge(licensePlate);
    }

    @Override
    public void remove(int id) {
        LicensePlate licensePlate = entityManager.find(LicensePlate.class, id);
        entityManager.remove(licensePlate);
    }

    @Override
    public boolean exists(LicensePlate licensePlate)  {
        boolean exists = false;
        TypedQuery query = entityManager.createNamedQuery("LicensePlate.Exists", LicensePlate.class);
        query.setParameter("licenseplate", licensePlate.getLicenseplate());
        List<LicensePlate> theList = query.getResultList();
        if (theList.size() > 0) {
            exists = true;
        }
        return exists;
    }

/*
    @Override
    public List<LicensePlate> findField(String fieldName, int value) throws Exception {
        List<LicensePlate> results = null;
        switch (fieldName) {
            case "srv_old_id":
                TypedQuery<LicensePlate> query = entityManager.createNamedQuery("LicensePlate.oldId", LicensePlate.class);
                query.setParameter("oldId", value);
                results = query.getResultList();
                break;
            default:
                throw new Exception("Unknown field");
        }
        return results;
    }

    @Override
    public LicensePlate findField1(String fieldName, int value) throws Exception {
        List<LicensePlate> results = findField(fieldName, value);
        switch (results.size()) {
            case 0 : throw new Exception("No results");
            case 1 : return results.get(0);
            default: throw new Exception("Too many results");
        }
    }
*/
}



