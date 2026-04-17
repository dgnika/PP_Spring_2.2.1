package hiber.dao;

import hiber.model.User;

import jakarta.persistence.TypedQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   private final SessionFactory sessionFactory;

   @Autowired
   public UserDaoImp(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().persist(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("select user from User user join fetch user.car", User.class);
      return query.getResultList();
   }

   @Override
   public List<User> getUserByCar(String model, int series) {
      return sessionFactory.getCurrentSession()
              .createQuery("select user from User user join fetch user.car car where user.car.model = :model and user.car.series = :series", User.class)
              .setParameter("model", model)
              .setParameter("series", series)
              .getResultList();
   }


}
