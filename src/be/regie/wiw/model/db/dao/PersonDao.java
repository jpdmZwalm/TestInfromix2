package be.regie.wiw.model.db.dao;

import be.regie.wiw.model.db.entity.Person;
import be.regie.wiw.model.db.entity.Title;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class PersonDao extends AbstractDao2<Person> {

    public PersonDao(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public String getTableName() {
        return Person.class.getSimpleName();
    }

    @Override
    public void persist(Person person) {
        //beginTransaction();
        entityManager.persist(person);
        //commitTransaction();
    }

    @Override
    public Person find(int id) {
        return entityManager.find(Person.class, id);
    }

    @Override
    public List<Person> findAll() {
        List<Person> allPerson =
                entityManager.createQuery("SELECT p FROM Person p").getResultList();
        return allPerson;
    }

    @Override
    public void update(Person person) {
        entityManager.merge(person);
    }

    @Override
    public void remove(int id) {
        Person person = entityManager.find(Person.class, id);
        entityManager.remove(person);
    }

    @Override
    public boolean exists(Person person) throws PartialExistingException {
        boolean existsCode = false;
        TypedQuery queryCode = entityManager.createNamedQuery("Person.ExistsCode", Person.class);
        queryCode.setParameter("code", person.getCode());
        List<Person> theListCode = queryCode.getResultList();
        if (theListCode.size() > 0) {
            existsCode = true;
        }

        boolean existsNaam = false;
        TypedQuery queryNaam = entityManager.createNamedQuery("Person.ExistsName", Person.class);
        queryNaam.setParameter("name", person.getName());
        queryNaam.setParameter("fname", person.getFname());
        List<Person> theListNaam = queryNaam.getResultList();
        if (theListNaam.size() > 0) {
            existsNaam = true;
        }

        if (existsCode && !existsNaam) {
            throw new PartialExistingException("FOUT Code bestaat en Naam niet : Person : " + person.toString());
        }
        if (!existsCode && existsNaam) {
            throw new PartialExistingException("FOUT Naam bestaat en Code niet : Person : " + person.toString());
        }

        return existsCode && existsNaam;
    }

    @Override
    public List<Person> findField(String fieldName, Integer value) throws Exception {
        List<Person> results = null;
            switch (fieldName) {
            case "pe_old_id":
                TypedQuery<Person> query = entityManager.createNamedQuery("Person.oldId", Person.class);
                query.setParameter("oldId", value);
                results = query.getResultList();
                break;
            case "pe_code":
                TypedQuery<Person> querycd = entityManager.createNamedQuery("Person.ExistsCode", Person.class);
                querycd.setParameter("code", value);
                results = querycd.getResultList();
                break;
            default:
                throw new Exception("Unknown field");
        }
        return results;
    }

    @Override
    public Person findField1(String fieldName, Integer value) throws Exception {
        if (value == null || value == 0)
            return null;
        List<Person> results = findField(fieldName, value);
        switch (results.size()) {
            case 0 : throw new Exception("Not found");
            case 1 : return results.get(0);
            default: throw new Exception("Too many results");
        }
    }

}





