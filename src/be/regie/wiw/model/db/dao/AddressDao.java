package be.regie.wiw.model.db.dao;

import be.regie.wiw.model.db.entity.Address;
import be.regie.wiw.model.db.entity.HigherFunction;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class AddressDao extends AbstractDao2<Address> {
    public AddressDao(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public String getTableName() {
        return Address.class.getSimpleName();
    }

    @Override
    public void persist(Address address) {
        //beginTransaction();
        entityManager.persist(address);
        //commitTransaction();
    }

    @Override
    public Address find(int id) {
        return entityManager.find(Address.class, id);
    }

    @Override
    public List<Address> findAll() {
        List<Address> allAddress =
                entityManager.createQuery("SELECT a FROM Address a").getResultList();
        return allAddress;
    }

    @Override
    public void update(Address address) {
        entityManager.merge(address);
    }

    @Override
    public void remove(int id) {
        Address address = entityManager.find(Address.class, id);
        entityManager.remove(address);
    }

    public Address findAdress(String adres, String gemeente) {
        //TODO persons pe_adm_adres en pe_adm_gemeente opzoeken
        //TODO beter adressen importeren van persons
        return null;
    }

    @Override
    public List<Address> findField(String fieldName, Integer value) throws Exception {
        List<Address> results = null;
        switch (fieldName) {
            case "adr_old_id":
                TypedQuery<Address> query = entityManager.createNamedQuery("Address.old_id", Address.class);
                query.setParameter("old_id", value);
                results = query.getResultList();
                break;
            default:
                throw new Exception("Unknown field");
        }
        return results;
    }

    @Override
    public Address findField1(String fieldName, Integer value) throws Exception {
        List<Address> results = findField(fieldName, value);
        switch (results.size()) {
            case 0 : throw new Exception("No results");
            case 1 : return results.get(0);
            default: throw new Exception("Too many results");
        }
    }


}

