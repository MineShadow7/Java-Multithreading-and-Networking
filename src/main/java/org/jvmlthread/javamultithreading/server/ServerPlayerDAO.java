package org.jvmlthread.javamultithreading.server;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import javax.naming.Referenceable;

import java.util.List;

public class ServerPlayerDAO {
    public ServerPlayerPersistentData findByNickname(String nickname) {
        try (Session session = ServerHibernateSessionFactory.getSessionFactory().openSession()) {
            final Query<ServerPlayerPersistentData> query = session.createQuery(
                    "from ServerPlayerPersistentData where nickname=:nick",
                    ServerPlayerPersistentData.class
            );
            query.setParameter("nick", nickname);
            return query.uniqueResult();
        }
    }
    public ServerPlayerPersistentData findById(int id) {
        try (Session session = ServerHibernateSessionFactory.getSessionFactory().openSession()) {
            return session.get(ServerPlayerPersistentData.class, id);
        }
    }
    public List<ServerPlayerPersistentData> getTopPlayers(int count) {
        try (Session session = ServerHibernateSessionFactory.getSessionFactory().openSession()) {
            final var query = session.createQuery(
                    "from ServerPlayerPersistentData player order by player.rank desc",
                    ServerPlayerPersistentData.class
            );
            query.setMaxResults(count);
            return query.getResultList();
        }
    }
    public void save(ServerPlayerPersistentData player) {
        try (Session session = ServerHibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(player);
            transaction.commit();
        }
    }
    public void update(ServerPlayerPersistentData player) {
        try (Session session = ServerHibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(player);
            transaction.commit();
        }
    }
}
