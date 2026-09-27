package com.hillel.homework.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


public final class HibernateUtil {

    private static final String PERSISTENCE_UNIT_NAME = "hillel-persistence-unit";
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY =
            Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    private HibernateUtil() {
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return ENTITY_MANAGER_FACTORY;
    }

    public static void close() {
        if (ENTITY_MANAGER_FACTORY.isOpen()) {
            ENTITY_MANAGER_FACTORY.close();
        }
    }
}
