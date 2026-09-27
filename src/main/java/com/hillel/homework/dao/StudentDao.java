package com.hillel.homework.dao;

import com.hillel.homework.entity.Student;
import com.hillel.homework.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;


public class StudentDao implements GenericDao<Student, Long> {

    @Override
    public void save(final Student entity) {
        final EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        final EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(entity);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Student findById(final Long id) {
        final EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.find(Student.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public Student findByEmail(final String email) {
        final EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        try {
            final TypedQuery<Student> query = em.createQuery(
                    "SELECT s FROM Student s WHERE s.email = :email", Student.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Student> findAll() {
        final EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT s FROM Student s", Student.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Student update(final Student entity) {
        final EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        final EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            final Student merged = em.merge(entity);
            transaction.commit();
            return merged;
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean deleteById(final Long id) {
        final EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        final EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            final Student student = em.find(Student.class, id);
            if (student == null) {
                transaction.rollback();
                return false;
            }
            em.remove(student);
            transaction.commit();
            return true;
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}
