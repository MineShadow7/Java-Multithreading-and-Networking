package ru.kriseev.netdevlab.server.dao.implementations;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.kriseev.netdevlab.server.dao.PlayerDAO;
import ru.kriseev.netdevlab.server.model.PlayerPersistentData;
import ru.kriseev.netdevlab.server.util.HibernateSessionFactory;

import java.util.List;

public class PlayerDAOHibernateImpl implements PlayerDAO {
    @Override
    public PlayerPersistentData findByNickname(String nickname) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            final Query<PlayerPersistentData> query = session.createQuery(
                    "from PlayerPersistentData where nickname=:nick",
                    PlayerPersistentData.class
            );
            query.setParameter("nick", nickname);
            return query.uniqueResult();
        }
    }

    @Override
    public PlayerPersistentData findById(int id) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return session.get(PlayerPersistentData.class, id);
        }
    }

    @Override
    public List<PlayerPersistentData> getTopPlayers(int count) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            final var query = session.createQuery(
                    "from PlayerPersistentData player order by player.rank desc",
                    PlayerPersistentData.class
            );
            query.setMaxResults(count);
            return query.getResultList();
        }
    }

    @Override
    public void save(PlayerPersistentData player) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(player);
            transaction.commit();
        }
    }

    @Override
    public void update(PlayerPersistentData player) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(player);
            transaction.commit();
        }
    }
}
