package be.regie.wiw.model.db.dao;

import be.regie.wiw.model.db.dao.AddressDao;
import be.regie.wiw.model.db.entity.Address;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class AddressDaoTest {

    @org.junit.jupiter.api.Test
    void test() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WIWPersistenceUnit");
        EntityManager em = emf.createEntityManager();
        AddressDao dao = new AddressDao(em);
        Address address = new Address("buildingNameNL3", "buildingNameFR3",
                                      "addressNL", "addressFR", "56A", "BE-1000",
                                      "Brussel", "Bruxelles", "Belgie", "BE", "BEL", null, null);
        dao.persist(address);
        Integer id = address.getId();
        assertNotNull(id, "id is null");

    }


}
