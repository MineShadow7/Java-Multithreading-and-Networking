package ru.kriseev.netdevlab.server.dao;

import ru.kriseev.netdevlab.server.dao.implementations.PlayerDAOHibernateImpl;

public class PlayerDAOFactory {
    private PlayerDAO playerDAO;
    public PlayerDAO getPlayerDAO() {
        if(playerDAO == null) {
            playerDAO = new PlayerDAOHibernateImpl();
        }
        return playerDAO;
    }

    protected PlayerDAOFactory() {}

    private static PlayerDAOFactory instance;
    public static PlayerDAOFactory getInstance() {
        if(instance == null) {
            instance = new PlayerDAOFactory();
        }
        return instance;
    }
}
