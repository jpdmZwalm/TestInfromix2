package be.regie.wiw.model.db.dao;

import be.regie.wiw.model.db.entity.security.User;

import javax.persistence.EntityManager;
import java.util.List;

public class UserDao extends AbstractDao2<User> {

    public UserDao(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public String getTableName() {
        return User.class.getSimpleName();
    }

    @Override
    public void persist(User user) {
        //beginTransaction();
        entityManager.persist(user);
        //commitTransaction();
    }

    @Override
    public User find(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> findAll() {
        List<User> allUser =
                entityManager.createQuery("SELECT u FROM User u").getResultList();
        return allUser;
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public void remove(int id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Override
    public List<User> findField(String fieldName, Integer value) throws Exception {
        List<User> results = null;
        return results;
    }

}

