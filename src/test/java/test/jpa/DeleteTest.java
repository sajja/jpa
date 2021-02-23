package test.jpa;

import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Created by sajith on 10/13/16.
 */
public class DeleteTest {


    @Test
    public void deleteTest() {
        EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Professor prof = em.find(Professor.class, 2);

        em.remove(prof);

        tx.commit();
    }
}
