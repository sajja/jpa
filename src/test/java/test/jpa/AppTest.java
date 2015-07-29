package test.jpa;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.Transaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Unit test for simple App.
 */
public class AppTest {

    public void setup() {
        EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        ArrayList<Professor> profs = new ArrayList<Professor>();
        Department alianDept = new Department();
        alianDept.setName("Alien studies");
        Professor agentK = new Professor();
        agentK.setName("K");

        Professor agentJ = new Professor();
        agentJ.setName("J");

        profs.add(agentK);
        profs.add(agentJ);

        alianDept.setEmployees(profs);

        em.persist(alianDept);
        tx.commit();
    }

    @Test
    public void lazyLoadingTest() {
        EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Department x1 = em.find(Department.class, 1);
        List<Professor>  p = x1.getEmployees();
        Professor newP = new Professor();
        newP.setName("VVVVVVVVv");
        p.add(newP);

//        Professor newP1 = new Professor();
//        p.add(newP1);
//        System.out.println("XXXXXX");
//        em.flush();
//        em.createNativeQuery("insert  into professor values(11,'x',0,1)").executeUpdate();
        Object o = em.createNativeQuery("SELECT nextval('hibernate_sequence')").getFirstResult();


        tx.commit();
     }


    @Test
    public void auditNon_InverseTest() throws NamingException {
        EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Department science = new Department();
        science.setName("Science");

        ArrayList<Professor> employees = new ArrayList<Professor>();

        Professor xavior = new Professor();
        xavior.setName("Xavior");
        employees.add(xavior);

        Professor magnito = new Professor();
        xavior.setName("Magnito");
        employees.add(magnito);

        science.setEmployees(employees);
        em.persist(science);

        Department investigation = new Department();
        investigation.setName("Investigation");

        Address sherlock = new Address();
        sherlock.setName("52, Baker st.");

        Address jamesbond = new Address();
        jamesbond.setName("Classified");

        jamesbond.setDepartment(investigation);
        sherlock.setDepartment(investigation);

        em.persist(sherlock);
        em.persist(jamesbond);


        tx.commit();
        tx = em.getTransaction();
        tx.begin();

        science = em.find(Department.class, science.getId());
        science.getEmployees().remove(0);
        em.persist(science);
        tx.commit();
    }


    @Test
    public void auditInverseTest() {
        EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Department cia = new Department();
        cia.setName("Cia");

        Address oo7 = new Address();
        oo7.setName("Classified");
        oo7.setDepartment(cia);


        Address oo8 = new Address();
        oo8.setName("Compromized");
        oo8.setDepartment(cia);

        em.persist(oo7);
        em.persist(oo8);

        oo7.setName("007 - modified");
        em.persist(oo7);

        tx.commit();

        tx = em.getTransaction();
        tx.begin();
        oo8 = em.find(Address.class, oo8.getId());
        oo8.setDepartment(null);
        em.persist(oo8);

        tx.commit();
    }

}
