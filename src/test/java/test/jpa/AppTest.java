package test.jpa;


import org.hibernate.LazyInitializationException;
import org.testng.Assert;
import org.testng.annotations.ExpectedExceptions;
import org.testng.annotations.Test;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void testManyToOne_non_lazy_property() throws NamingException {
        EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Department d1 = em.find(Department.class, 1);
        Assert.assertEquals(d1.getName(), "science");//checking a non-lazy property.
        tx.commit();
    }

    @Test
    @ExpectedExceptions(value = LazyInitializationException.class)
    public void testManyToOne_lazy_property_with_closed_session_results_exception() throws NamingException {
        EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Department d1 = em.find(Department.class, 1);
        Assert.assertEquals(d1.getName(),"science");//checking a non-lazy property.
        tx.commit();
        em.close();
        Assert.assertEquals(d1.getEmployees().get(0).getName(),"Xaviour");

    }

    @Test
    @ExpectedExceptions(value = LazyInitializationException.class)
    public void testManyToOne_lazy_property_with_open_session_passes() throws NamingException {
        EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Department d1 = em.find(Department.class, 1);
        Assert.assertEquals(d1.getName(),"science");//checking a non-lazy property.
        tx.commit();
        em.close();
        Assert.assertEquals(d1.getEmployees().get(0).getName(),"Xaviour");
    }



    //One-to-Many tests

    @Test
    public void testOne_to_Many_non_lazy_property_non_inverse_relation() throws NamingException {
        EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Professor x = em.find(Professor.class, 1);
        Assert.assertEquals(x.getName(), "Xaviour");//checking a non-lazy property.
        tx.commit();
    }


    @Test
    public void testOne_to_Many_lazy_property_with_open_session_passes_non_inverse_relation() throws NamingException {
        EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Professor x = em.find(Professor.class, 1);
        Assert.assertEquals(x.getName(), "Xaviour");//checking a non-lazy property.
        Assert.assertEquals(x.getDepartment().getName(), "science");//checking a non-lazy property.
        tx.commit();
    }

    @Test
    @ExpectedExceptions(value = LazyInitializationException.class)
    public void testOne_to_Many_lazy_property_with_closed_session_fails_non_inverse_relation() throws NamingException {
        EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Professor x = em.find(Professor.class, 1);
        Assert.assertEquals(x.getName(), "Xaviour");//checking a non-lazy property.
        tx.commit();
        em.close();
        Assert.assertEquals(x.getDepartment().getName(), "science");//checking a non-lazy property.
    }


}
