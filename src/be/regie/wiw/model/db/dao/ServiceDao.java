package be.regie.wiw.model.db.dao;

import be.regie.wiw.model.db.entity.Service;
import be.regie.wiw.model.db.entity.Title;

import javax.persistence.EntityManager;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.TypedQuery;
import java.util.List;

public class ServiceDao extends AbstractDao2<Service> {

    public ServiceDao(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public String getTableName() {
        return Service.class.getSimpleName();
    }

    @Override
    public void persist(Service service) {
        //beginTransaction();
        entityManager.persist(service);
        //commitTransaction();
    }

    @Override
    public Service find(int id) {
        return entityManager.find(Service.class, id);
    }

    @Override
    public List<Service> findAll() {
        List<Service> allService =
                entityManager.createQuery("SELECT s FROM Service s").getResultList();
        return allService;
    }

    @Override
    public void update(Service service) {
        entityManager.merge(service);
    }

    @Override
    public void remove(int id) {
        Service service = entityManager.find(Service.class, id);
        entityManager.remove(service);
    }

    @Override
    public boolean exists(Service service) throws PartialExistingException {
        boolean existsNL = false;
        TypedQuery queryNL = entityManager.createNamedQuery("Service.ExistsNL", Service.class);
        queryNL.setParameter("descrNL", service.getDescrNL());
        List<Service> theListNL = queryNL.getResultList();
        if (theListNL.size() > 0) {
            existsNL = true;
        }

        boolean existsFR = false;
        TypedQuery queryFR = entityManager.createNamedQuery("Service.ExistsFR", Service.class);
        queryFR.setParameter("descrFR", service.getDescrFR());
        List<Service> theListFR = queryFR.getResultList();
        if (theListFR.size() > 0) {
            existsFR = true;
        }

        boolean existsCode = false;
        if (service.getDnstCode() != null) {
            TypedQuery queryCode = entityManager.createNamedQuery("Service.ExistsCode", Service.class);
            queryCode.setParameter("dnstCode", service.getDnstCode());
            List<Service> theListCode = queryCode.getResultList();
            if (theListCode.size() > 0) {
                existsCode = true;
            }
        }

        boolean existsId = false;
        if (service.getDnstId() != null) {
            TypedQuery queryId = entityManager.createNamedQuery("Service.ExistsId", Service.class);
            queryId.setParameter("dnstId", service.getDnstId());
            List<Service> theListId = queryId.getResultList();
            if (theListId.size() > 0) {
                existsId = true;
            }
        }

        boolean existsTrue  =  existsNL &&  existsFR &&  existsCode &&  existsId;
        boolean existsFalse = !existsNL && !existsFR && !existsCode && !existsId;

        if (!existsTrue && !existsFalse) {
            throw new PartialExistingException("FOUT : Service : " + service.toString());
        }

        return existsTrue;
    }

    @Override
    public List<Service> findField(String fieldName, Integer value) throws Exception {
        List<Service> results = null;
        switch (fieldName) {
            case "srv_old_id":
                TypedQuery<Service> query = entityManager.createNamedQuery("Service.oldId", Service.class);
                query.setParameter("oldId", value);
                results = query.getResultList();
                break;
            default:
                throw new Exception("Unknown field");
        }
        return results;
    }

    @Override
    public Service findField1(String fieldName, Integer value) throws Exception {
        if (value == null || value == 0)
            return null;
        List<Service> results = findField(fieldName, value);
        switch (results.size()) {
            case 0 : throw new Exception("No results");
            case 1 : return results.get(0);
            default: throw new Exception("Too many results");
        }
    }

}



