package org.jvmlthread.javamultithreading.server.dao;

public class PlayerDAOFactory {
    private PlayerDAO playerDAO;
    public PlayerDAO getPlayerDAO() {
        if(playerDAO == null) {
            playerDAO = new PlayerDaoHibernateImplementation();
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
