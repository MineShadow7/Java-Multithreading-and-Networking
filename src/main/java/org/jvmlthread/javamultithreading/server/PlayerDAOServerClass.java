package org.jvmlthread.javamultithreading.server;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class PlayerDAOServerClass {
    public PlayerPersistentDataServer findByNickname(String nickname) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            final Query<PlayerPersistentDataServer> query = session.createQuery(
                    "from PlayerPersistentDataServer where nickname=:nick",
                    PlayerPersistentDataServer.class
            );
            query.setParameter("nick", nickname);
            return query.uniqueResult();
        }
    }
    public PlayerPersistentDataServer findById(int id) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return session.get(PlayerPersistentDataServer.class, id);
        }
    }
    public List<PlayerPersistentDataServer> getTopPlayers(int count) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            final var query = session.createQuery(
                    "from PlayerPersistentDataServer player order by player.rank desc",
                    PlayerPersistentDataServer.class
            );
            query.setMaxResults(count);
            return query.getResultList();
        }
    }
    public void save(PlayerPersistentDataServer player) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(player);
            transaction.commit();
        }
    }
    public void update(PlayerPersistentDataServer player) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(player);
            transaction.commit();
        }
    }
}
