package web.dao;

import web.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDaoImpl implements UserDao {
   ///private final Map<Long, User> m = new HashMap<Long,User>();
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List getAllUsers() {
        return sessionFactory.getCurrentSession().createQuery("from User ").list();
    }

    @Override
    public void saveUser(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    /*public User findUserById(Long id) {
        return sessionFactory.getCurrentSession().load(User.class, id);
    }*/

    public User findUserById(Long id) {
        return (User) sessionFactory
                .getCurrentSession()
                .createQuery("From User user where user.id =: id")
                .setParameter("id", id)
                .getSingleResult();
    }

    public User findUserByName(String name) {
        return (User) sessionFactory
                .getCurrentSession()
                .createQuery("From User user where user.name =: name")
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public void deleteUser(Long id) {
        sessionFactory.getCurrentSession().delete(sessionFactory.getCurrentSession().get(User.class, id));
    }

    @Override
    public void updateUser(User user) {
        Query query = sessionFactory.getCurrentSession().createQuery(
                "update User u set u.name = :name, u.age = :age, u.password = :pass where u.id = :id");
        query.setParameter("name", user.getName());
        query.setParameter("id", user.getId());
        query.setParameter("age", user.getAge());
        query.setParameter("pass", user.getPassword());
        query.executeUpdate();
    }

}
